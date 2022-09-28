package pl.kithard.core.player.actionbar.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.util.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ActionBarNoticeShowTask extends BukkitRunnable {

    private final CorePlugin plugin;
    private StringBuilder builder;

    public ActionBarNoticeShowTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 10L, 10L);
    }

    @Override
    public void run() {

        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer == null) {
                continue;
            }

            constant(onlinePlayer);
            show(onlinePlayer);
        }
    }

    void show(Player player) {
        long currentTimeMillis = System.currentTimeMillis();
        Set<ActionBarNotice> notices = new HashSet<>(this.plugin.getActionBarNoticeCache()
                .values(player.getUniqueId())
                .values());

        if (notices.isEmpty()) {
            return;
        }

        this.builder = new StringBuilder();
        for (ActionBarNotice notice : notices.stream()
                .sorted(Comparator.comparingInt(o -> o.getType().getPriority()))
                .collect(Collectors.toCollection(LinkedHashSet::new))) {

            if (notice.getExpireTime() > currentTimeMillis || notice.getExpireTime() == 0) {
                updateMessage(notice.getText());
            }
            else {
                this.plugin.getActionBarNoticeCache().remove(player.getUniqueId(), notice.getType());
            }

        }

        ActionBarUtil.actionBar(player, builder.toString());
    }

    void constant(Player player) {
        long currentTimeMillis = System.currentTimeMillis();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        add(
                corePlayer.isVanish(),
                player,
                ActionBarNotice.builder()
                        .type(ActionBarNoticeType.VANISH)
                        .text("&7Jestes aktualnie &fniewidzialny&7!")
                        .build()
        );

        PlayerCombat playerCombat = corePlayer.getCombat();
        if (playerCombat != null) {
            add(
                    playerCombat.hasFight(),
                    player,
                    ActionBarNotice.builder()
                            .type(ActionBarNoticeType.ANTI_LOGOUT)
                            .text("&c&lAntiLogout &8(&4" + TimeUtil.formatTimeMillis(playerCombat.getLastAttackTime() - currentTimeMillis) + "&8)")
                            .build()
            );
        }

        add(
                this.plugin.getServerSettings().getTurboDrop() > currentTimeMillis,
                player,
                ActionBarNotice.builder()
                        .type(ActionBarNoticeType.TURBO_DROP)
                        .text("&b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &8(&f" + TimeUtil.formatTimeMillis(this.plugin.getServerSettings().getTurboDrop() - currentTimeMillis) + "&8)")
                        .build()
        );

        add(
                corePlayer.getProtection() > System.currentTimeMillis(),
                player,
                ActionBarNotice.builder()
                        .type(ActionBarNoticeType.PROTECTION)
                        .text("&2&lOchrona &8(&a" + TimeUtil.formatTimeMillis(corePlayer.getProtection() - System.currentTimeMillis()) + "&8)")
                        .build()
        );

        Guild guildByLocation = this.plugin.getGuildCache().findByLocation(player.getLocation());
        if (guildByLocation != null) {
            String message;
            Guild playerGuild = this.plugin.getGuildCache().findByPlayer(player);
            double distance = MathUtil.round(LocationUtil.distance(player.getLocation(), guildByLocation.getRegion().getCenter().clone()), 2);

            if (guildByLocation != playerGuild) {
                if (playerGuild != null && playerGuild.getAllies().contains(guildByLocation.getTag())) {
                    message = "&9Teren gildii &8[&9&l" + guildByLocation.getTag() + "&8] &f" + distance + "m";
                }
                else {
                    message = "&cTeren gildii &8[&4&l" + guildByLocation.getTag() + "&8] &f" + distance + "m";
                }
            }
            else {
                message = "&aTeren gildii &8[&2&l" + guildByLocation.getTag() + "&8] &f" + distance + "m";
            }

            this.plugin.getActionBarNoticeCache().add(
                    player.getUniqueId(),
                    ActionBarNotice.builder()
                            .type(ActionBarNoticeType.GUILD_TERRAIN)
                            .text(message)
                            .build()
            );
        }
        else {
            this.plugin.getActionBarNoticeCache().remove(player.getUniqueId(), ActionBarNoticeType.GUILD_TERRAIN);
        }
    }


    void add(boolean b, Player player, ActionBarNotice notice) {
        if (b) {
            this.plugin.getActionBarNoticeCache().add(player.getUniqueId(), notice);
        }
        else {
            this.plugin.getActionBarNoticeCache().remove(player.getUniqueId(), notice.getType());
        }
    }

    void updateMessage(String text) {
        if (builder.toString().isEmpty()) {
            builder.append(TextUtil.color(text));
            return;
        }

        builder.append(TextUtil.color(" &8&l| &r" + text));
    }
}
