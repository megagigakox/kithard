package pl.kithard.core.player.combat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.backup.PlayerBackupType;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerDeathListener implements Listener {

    private final static String TITLE_KILL_MESSAGE = "&7Zabiles gracza {LOSER_GUILD}&c{LOSER} &8(&7+{VICTIM_POINTS}&8, &c{VICTIM_HEARTS}❤&8)";
    private final static String TITLE_ASSIST_MESSAGE = "&7Asystowales przy zabiciu {LOSER_GUILD}&c{LOSER} &8(&7+{ASSISTANT_POINTS}&8, &c{ASSISTANT_HEARTS}❤&8)";
    private final static String CHAT_KILL_MESSAGE = "&c&l⚔ {LOSER_GUILD}&c{LOSER} &8(&7-{LOSER_POINTS}&8) &7zostal zabity przez {VICTIM_GUILD}&c{VICTIM} &8(&7+{VICTIM_POINTS}&8, &c{VICTIM_HEARTS}❤&8)";
    private final static String CHAT_ASSIST_MESSAGE = "&c&l⚔ &7Asystowal {ASSISTANT_GUILD}&c{ASSISTANT} &8(&7+{ASSISTANT_POINTS}&8, &c{ASSISTANT_HEARTS}❤&8)";

    private final CorePlugin plugin;

    public PlayerDeathListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player loser = event.getEntity();
        Player victim = loser.getKiller();

        CorePlayer loserCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(loser);
        PlayerCombat loserCombat = loserCorePlayer.getCombat();

        if (victim == null && loserCombat.getLastAttackPlayer() != null && loserCombat.hasFight()) {
            victim = loserCombat.getLastAttackPlayer();
        }

        if (victim == null) {
            this.plugin.getPlayerBackupFactory().create(
                    loser,
                    PlayerBackupType.DEATH,
                    "nieznany zabojca",
                    5
            );
            loserCorePlayer.removePoints(5);
            loserCorePlayer.addDeaths(1);
            loserCorePlayer.setNeedSave(true);
            return;
        }

        CorePlayer victimCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(victim);
        if (loser.getName().equals(victim.getName())) {
            return;
        }

        if (loserCorePlayer.getLastDeaths().containsKey(victimCorePlayer.getUuid()) && loserCorePlayer.getLastDeaths().get(victimCorePlayer.getUuid()) + TimeUnit.MINUTES.toMillis(30) > System.currentTimeMillis()) {
            TextUtil.message(victim, "&8[&4&l!&8] &cZabiles ostatnio tego gracza, punkty nie zostaja przyznane!");
            TextUtil.message(loser, "&8[&4&l!&8] &cZostales zabity ostatnio przez tego gracza, punkty nie zostaja odebrane!");
            this.plugin.getPlayerBackupFactory().create(
                    loser,
                    PlayerBackupType.DEATH,
                    victim.getName(),
                    0
            );
            return;
        }

        int toAdd = (int) (67.0 + (victimCorePlayer.getPoints() - loserCorePlayer.getPoints()) * -0.25);
        if (toAdd <= 20) {
            toAdd = 20;
        }

        int toRemove = toAdd / 2;
        if (toAdd / 2 <= 10) {
            toRemove = 10;
        }

        Guild loserGuild = this.plugin.getGuildCache().findByPlayer(loser);
        Guild victimGuild = this.plugin.getGuildCache().findByPlayer(victim);

        TitleUtil.title(
                victim,
                "&c&lZabojstwo!",
                TITLE_KILL_MESSAGE
                        .replace("{LOSER_GUILD}", loserGuild == null ? "" : " &8[&c" + loserGuild.getTag() + "&8] " )
                        .replace("{LOSER}", loser.getName())
                        .replace("{VICTIM_POINTS}", String.valueOf(toAdd))
                        .replace("{VICTIM_HEARTS}", String.valueOf(MathUtil.round(victim.getHealth() / 2, 2))),
                20,
                80,
                20
        );

        for (Player it : Bukkit.getServer().getOnlinePlayers()) {
            CorePlayer itCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(it);

            if (!itCorePlayer.isDisabledSetting(PlayerSettings.DEATHS_MESSAGES)) {
                TextUtil.message(it, CHAT_KILL_MESSAGE
                        .replace("{LOSER_GUILD}", loserGuild == null ? "" : "&8[&c" + loserGuild.getTag() + "&8] ")
                        .replace("{VICTIM_GUILD}", victimGuild == null ? "" : "&8[&c" + victimGuild.getTag() + "&8] ")
                        .replace("{VICTIM}", victim.getName())
                        .replace("{LOSER}", loser.getName())
                        .replace("{LOSER_POINTS}", String.valueOf(toRemove))
                        .replace("{VICTIM_POINTS}", String.valueOf(toAdd))
                        .replace("{VICTIM_HEARTS}", String.valueOf(MathUtil.round(victim.getHealth() / 2, 2))));
            }
        }

        if (loserCombat.getLastAssistPlayer() != null
                && loserCombat.getLastAssistTime() > System.currentTimeMillis()
                && !loserCombat.getLastAssistPlayer().getUniqueId().equals(victim.getUniqueId())) {

            Player assistant = loserCombat.getLastAssistPlayer();
            CorePlayer assistantCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(assistant);

            int assistPoints = (int) ((42.0 + (assistantCorePlayer.getPoints() - loserCorePlayer.getPoints()) * -0.25) / 3.0);
            if (assistPoints <= 5) {
                assistPoints = 5;
            }

            Guild assistantGuild = this.plugin.getGuildCache().findByPlayer(assistant);
            TitleUtil.title(
                    assistant,
                    "&c&lAsysta!",
                    TITLE_ASSIST_MESSAGE
                            .replace("{LOSER}", loser.getName())
                            .replace("{LOSER_GUILD}", loserGuild == null ? "" : "&8[&c" + loserGuild.getTag() + "&8] ")
                            .replace("{ASSISTANT_POINTS}", String.valueOf(assistPoints))
                            .replace("{ASSISTANT_HEARTS}", String.valueOf(MathUtil.round(assistant.getHealth() / 2, 2))),
                    20,
                    80,
                    20
            );

            for (Player it : Bukkit.getServer().getOnlinePlayers()) {
                CorePlayer itCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(it);

                if (!itCorePlayer.isDisabledSetting(PlayerSettings.DEATHS_MESSAGES)) {
                    TextUtil.message(it, CHAT_ASSIST_MESSAGE
                            .replace("{ASSISTANT_GUILD}", assistantGuild == null ? "" : "&8[&c" + assistantGuild.getTag() + "&8] ")
                            .replace("{ASSISTANT}", assistant.getName())
                            .replace("{ASSISTANT_POINTS}", String.valueOf(assistPoints))
                            .replace("{ASSISTANT_HEARTS}", String.valueOf(MathUtil.round(assistant.getHealth() / 2, 2))));
                }
            }

            assistantCorePlayer.addAssists(1);
            assistantCorePlayer.addPoints(assistPoints);
            assistantCorePlayer.setNeedSave(true);
        }

        loserCorePlayer.removePoints(toRemove);
        loserCorePlayer.addDeaths(1);
        loserCorePlayer.addAchievementProgress(AchievementType.DEATHS, 1);
        loserCorePlayer.setKillStreak(0);
        loserCorePlayer.getLastDeaths().put(victimCorePlayer.getUuid(), System.currentTimeMillis());
        loserCorePlayer.setNeedSave(true);

        this.plugin.getPlayerNameTagService().updateDummy(loserCorePlayer);

        loserCombat.setLastAttackTime(0L);
        loserCombat.setLastAssistTime(0L);
        loserCombat.setLastAssistPlayer(null);
        loserCombat.setLastAttackPlayer(null);

        this.plugin.getPlayerBackupFactory().create(loser, PlayerBackupType.DEATH, victim.getName(), toRemove);

        if (loserGuild != null) {
            loserGuild.addDeaths(1);
            loserGuild.removePoints(toRemove / 2);
            loserGuild.setNeedSave(true);
        }

        victimCorePlayer.addPoints(toAdd);
        victimCorePlayer.addAchievementProgress(AchievementType.CONQUERED_POINTS, toAdd);
        victimCorePlayer.addKillStreak(1);
        victimCorePlayer.addKills(1);
        victimCorePlayer.addAchievementProgress(AchievementType.KILLS, 1);
        victimCorePlayer.setNeedSave(true);

        this.plugin.getPlayerNameTagService().updateDummy(victimCorePlayer);

        ItemStack itemStack = SkullCreator.itemFromUuid(loserCorePlayer.getUuid());
        InventoryUtil.addItem(victim, itemStack);
        victim.setFireTicks(0);

        if (victimGuild != null) {
            victimGuild.addKills(1);
            victimGuild.addPoints(toAdd / 2);
            victimGuild.setNeedSave(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        if (event.getEntity().getKiller() == null) {
            return;
        }

        Player killer = event.getEntity().getKiller();
        if (killer.equals(event.getEntity())) {
            return;
        }

        InventoryUtil.addItems(killer, drops, event.getEntity().getLocation().getBlock());
        killer.giveExp(event.getDroppedExp());
        event.getDrops().clear();
    }
}
