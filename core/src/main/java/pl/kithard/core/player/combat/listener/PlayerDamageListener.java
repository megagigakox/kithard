package pl.kithard.core.player.combat.listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

import java.util.concurrent.TimeUnit;

public class PlayerDamageListener implements Listener {

    private final static String HP_INFO = "&7HP zaatakowanego gracza&8: &c{HP}!";

    private final CorePlugin plugin;

    public PlayerDamageListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player attacker = getAttackerFrom(event);
        if (attacker == null) {
            return;
        }

        if (LocationUtil.isInSpawn(attacker.getLocation())) {
            return;
        }

        Player attacked = (Player) event.getEntity();
        if (attacked.equals(attacker)) {
            return;
        }

        if (this.plugin.getServerSettings().getFreeze() > System.currentTimeMillis()) {
            event.setCancelled(true);
            TextUtil.message(attacker, "&8(&4&l!&8) &cNie mozesz sie bic podczas zamrozenia!");
            return;
        }

        Guild attackedPlayerGuild = this.plugin.getGuildCache().findByPlayer(attacked);
        Guild attackerPlayerGuild = this.plugin.getGuildCache().findByPlayer(attacker);
        if (attackedPlayerGuild != null && attackerPlayerGuild != null) {
            if (attackedPlayerGuild.getTag().equalsIgnoreCase(attackerPlayerGuild.getTag())) {
                if (!attackedPlayerGuild.isFriendlyFire()) {
                    event.setCancelled(true);
                    return;
                }
            } else if (attackedPlayerGuild.getAllies().contains(attackerPlayerGuild.getTag())) {
                if (!attackedPlayerGuild.isAllyFire() && !attackerPlayerGuild.isAllyFire()) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        CorePlayer attackedPlayer = this.plugin.getCorePlayerCache().findByPlayer(attacked);
        CorePlayer attackerPlayer = this.plugin.getCorePlayerCache().findByPlayer(attacker);
        if (attackerPlayer.getCooldown().getGroupTeleportCombatCooldown() > System.currentTimeMillis()) {
            event.setCancelled(true);
            return;
        }

        if (attackerPlayer.getProtection() > System.currentTimeMillis()) {
            TextUtil.message(attacker, "&8(&4&l!&8) &cPosiadasz ochrone startowa jeszcze przez: &4" + TimeUtil.formatTimeMillis(attackerPlayer.getProtection() - System.currentTimeMillis()) + ". &cJezeli chcesz ja wylaczyc uzyj komendy: &4/wylaczochrone");
            event.setCancelled(true);
            return;
        }

        if (attackedPlayer.getProtection() > System.currentTimeMillis()) {
            TextUtil.message(attacker, "&8(&4&l!&8) &cZaatakowany gracz posiada ochrone startowa jeszcze przez: &4" + TimeUtil.formatTimeMillis(attackedPlayer.getProtection() - System.currentTimeMillis()));
            event.setCancelled(true);
            return;
        }

        PlayerCombat attackedCombat = attackedPlayer.getCombat();
        PlayerCombat attackerCombat = attackerPlayer.getCombat();
        attackedCombat.setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
        attackerCombat.setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));

        if (attackedCombat.getLastAttackPlayer() != attacker) {
            attackedCombat.setLastAssistPlayer(attackedCombat.getLastAttackPlayer());
            attackedCombat.setLastAssistTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60));
        }

        attackedCombat.setLastAttackPlayer(attacker);
        attackerCombat.setLastAttackPlayer(attacked);
        tryUnblock(attacked);
        tryUnblock(attacker);

        if (event.getDamager() instanceof Snowball || event.getDamager() instanceof Arrow) {
            double hp = attacked.getHealth();
            String replacedHpInfo = HP_INFO.replace("{HP}", String.valueOf(MathUtil.round(hp, 2)));
            TextUtil.message(attacker, replacedHpInfo);

            this.plugin.getActionBarNoticeCache().add(
                    attacker.getUniqueId(),
                    ActionBarNotice.builder()
                            .type(ActionBarNoticeType.HP_INFO)
                            .text(replacedHpInfo)
                            .expireTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3))
                            .build()
            );
        }
    }

    private void tryUnblock(Player player) {
        if (player.isBlocking()) {
            ItemStack itemInHand = player.getItemInHand();
            player.setItemInHand(null);
            player.setItemInHand(itemInHand);
            player.updateInventory();
        }
    }

    private Player getAttackerFrom(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        boolean isPlayer = attacker instanceof Player, isProjectile = attacker instanceof Projectile;
        if (!isPlayer && !isProjectile) {
            return null;
        }

        if (isProjectile) {
            ProjectileSource source = ((Projectile) attacker).getShooter();
            if (source instanceof Player) {
                return (Player) source;
            }
        }

        return isPlayer ? (Player) attacker : null;
    }

    @EventHandler
    public void onPearlDamage(EntityDamageByEntityEvent event) {

        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        if (!event.getDamager().getType().equals(EntityType.ENDER_PEARL)) {
            return;
        }

        event.setCancelled(true);
    }

}
