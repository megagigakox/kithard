package pl.kithard.core.player.combat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.backup.PlayerBackup;
import pl.kithard.core.player.backup.PlayerBackupType;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerDeathListener implements Listener {

    private final static String TITLE_KILL_MESSAGE = "&cZabiles gracza {LOSER_GUILD}&c{LOSER} &8(&7+{VICTIM_POINTS}&8, &c{VICTIM_HEARTS}❤&8)";
    private final static String TITLE_ASSIST_MESSAGE = "&cAsystowales przy zabiciu {LOSER_GUILD}&c{LOSER} &8(&7+{ASSISTANT_POINTS}&8, &c{ASSISTANT_HEARTS}❤&8)";
    private final static String CHAT_KILL_MESSAGE = "{LOSER_GUILD}&c{LOSER} &8(&7-{LOSER_POINTS}&8) &czabity przez {VICTIM_GUILD}&c{VICTIM} &8(&7+{VICTIM_POINTS}&8, &c{VICTIM_HEARTS}❤&8)";
    private final static String CHAT_ASSIST_MESSAGE = "&cAsysta&8: {ASSISTANT_GUILD}&c{ASSISTANT} &8(&7+{ASSISTANT_POINTS}&8, &c{ASSISTANT_HEARTS}❤&8)";
    private final static String KILLSELF_MESSAGE = "{GUILD}&c{LOSER} &8(&7-{POINTS}&8) &cpopelnil samobojstwo!";

    private final CorePlugin plugin;

    public PlayerDeathListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player loser = event.getEntity();
        Player killer = loser.getKiller();
        CorePlayer loserPlayer = this.plugin.getCorePlayerCache().findByPlayer(loser);
        PlayerCombat loserCombat = loserPlayer.getCombat();
        loserPlayer.addDeaths(1);
        loserPlayer.addAchievementProgress(AchievementType.DEATHS, 1);

        if ((killer == null || killer.equals(loser)) && loserCombat.getLastAttackPlayer() != null && loserCombat.hasFight()) {
            killer = loserCombat.getLastAttackPlayer();
        }

        Guild loserGuild = this.plugin.getGuildCache().findByPlayer(loser);
        if (killer == null) {
            PlayerBackup playerBackup = this.plugin.getPlayerBackupFactory().create(loser, PlayerBackupType.DEATH, "unknown killer", 3);
            this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPlayerBackupRepository().insert(playerBackup));
            loserPlayer.removePoints(3);
            loserPlayer.setNeedSave(true);
            loserCombat.reset();
            Bukkit.broadcastMessage(TextUtil.color(KILLSELF_MESSAGE
                    .replace("{GUILD}", loserGuild == null ? "" : "&8[&c" + loserGuild.getTag() + "&8] ")
                    .replace("{LOSER}", loser.getName())
                    .replace("{POINTS}", String.valueOf(3))));
            return;
        }

        killer.getWorld().strikeLightningEffect(loser.getLocation());
        CorePlayer killerPlayer = this.plugin.getCorePlayerCache().findByPlayer(killer);

        Guild killerGuild = this.plugin.getGuildCache().findByPlayer(killer);
        if (loserPlayer.getCooldown().getLastKillersCooldown().containsKey(killerPlayer.getUuid()) && loserPlayer.getCooldown().getLastKillersCooldown().get(killerPlayer.getUuid()) + TimeUnit.MINUTES.toMillis(30) > System.currentTimeMillis()) {
            TextUtil.message(killer, "&8(&4&l!&8) &cZabiles ostatnio tego gracza, punkty nie zostaja przyznane!");
            TextUtil.message(loser, "&8(&4&l!&8) &cZostales zabity ostatnio przez tego gracza, punkty nie zostaja odebrane!");
            broadcastKill(loserGuild, killerGuild, killer, loser, 0, 0);
            PlayerBackup playerBackup = this.plugin.getPlayerBackupFactory().create(loser, PlayerBackupType.DEATH, killer.getName(), 0);
            this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPlayerBackupRepository().insert(playerBackup));
            loserCombat.reset();
            return;
        }

        int toAdd = (int) (43.0 + (killerPlayer.getPoints() - loserPlayer.getPoints()) * -0.10);
        if (toAdd < 10) {
            toAdd = 8;
        }
        int toRemove = (int) (toAdd / 1.7);
        if (toRemove < 5) {
            toRemove = 5;
        }

        broadcastKill(loserGuild, killerGuild, killer, loser, toAdd, toRemove);

        if (loserCombat.getLastAssistPlayer() != null
                && loserCombat.getLastAssistTime() > System.currentTimeMillis()
                && !loserCombat.getLastAssistPlayer().getUniqueId().equals(killer.getUniqueId())) {

            Player assistant = loserCombat.getLastAssistPlayer();
            CorePlayer assistantCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(assistant);

            int assistPoints = (int) ((31.0 + (assistantCorePlayer.getPoints() - loserPlayer.getPoints()) * -0.10) / 3.0);
            if (assistPoints <= 0) {
                assistPoints = 3;
            }

            Guild assistantGuild = this.plugin.getGuildCache().findByPlayer(assistant);
            TitleUtil.title(
                    assistant,
                    "&bAsysta!",
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

        loserPlayer.removePoints(toRemove);
        loserPlayer.setKillStreak(0);
        loserPlayer.getCooldown()
                .getLastKillersCooldown()
                .put(killerPlayer.getUuid(), System.currentTimeMillis());
        loserPlayer.setNeedSave(true);
        loserCombat.reset();

        this.plugin.getPlayerNameTagService().updateDummy(loserPlayer);
        PlayerBackup playerBackup = this.plugin.getPlayerBackupFactory().create(loser, PlayerBackupType.DEATH, killer.getName(), toRemove);
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPlayerBackupRepository().insert(playerBackup));

        if (loserGuild != null) {
            loserGuild.addDeaths(1);
            loserGuild.removePoints(toRemove / 2);
            loserGuild.setNeedSave(true);
        }

        killerPlayer.addPoints(toAdd);
        killerPlayer.addAchievementProgress(AchievementType.CONQUERED_POINTS, toAdd);
        killerPlayer.addKillStreak(1);
        killerPlayer.addKills(1);
        killerPlayer.addAchievementProgress(AchievementType.KILLS, 1);
        killerPlayer.setNeedSave(true);

        this.plugin.getPlayerNameTagService().updateDummy(killerPlayer);

        ItemStack itemStack = SkullCreator.itemFromUuid(loserPlayer.getUuid());
        InventoryUtil.addItem(killer, itemStack);
        killer.setFireTicks(0);

        if (killerGuild != null) {
            killerGuild.addKills(1);
            killerGuild.addPoints(toAdd / 2);
            killerGuild.setNeedSave(true);
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

    void broadcastKill(Guild loserGuild, Guild killerGuild, Player killer, Player loser, int toAdd, int toRemove) {
        TitleUtil.title(
                killer,
                "&bZabojstwo!",
                TITLE_KILL_MESSAGE
                        .replace("{LOSER_GUILD}", loserGuild == null ? "" : " &8[&c" + loserGuild.getTag() + "&8] " )
                        .replace("{LOSER}", loser.getName())
                        .replace("{VICTIM_POINTS}", String.valueOf(toAdd))
                        .replace("{VICTIM_HEARTS}", String.valueOf(MathUtil.round(killer.getHealth() / 2, 2))),
                20,
                80,
                20
        );

        for (Player it : Bukkit.getServer().getOnlinePlayers()) {
            CorePlayer itCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(it);
            if (!itCorePlayer.isDisabledSetting(PlayerSettings.DEATHS_MESSAGES)) {
                TextUtil.message(it, CHAT_KILL_MESSAGE
                        .replace("{LOSER_GUILD}", loserGuild == null ? "" : "&8[&c" + loserGuild.getTag() + "&8] ")
                        .replace("{VICTIM_GUILD}", killerGuild == null ? "" : "&8[&c" + killerGuild.getTag() + "&8] ")
                        .replace("{VICTIM}", killer.getName())
                        .replace("{LOSER}", loser.getName())
                        .replace("{LOSER_POINTS}", String.valueOf(toRemove))
                        .replace("{VICTIM_POINTS}", String.valueOf(toAdd))
                        .replace("{VICTIM_HEARTS}", String.valueOf(MathUtil.round(killer.getHealth() / 2, 2))));
            }
        }
    }
}
