package pl.kithard.core.player.ranking;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.*;

public class PlayerRankingGui {

    private final CorePlugin plugin;
    private final PlayerRankingService rankingService;

    public PlayerRankingGui(CorePlugin plugin) {
        this.plugin = plugin;
        this.rankingService = plugin.getPlayerRankingService();
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&9&lTopki"))
                .rows(5)
                .create();

        CorePlayer core = this.plugin.getCorePlayerCache().findByPlayer(player);

        GuiHelper.fillColorGui5(gui);

        ItemStackBuilder minedStone = ItemStackBuilder.of(Material.STONE)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 WYKOPANEGO STONE &b&l&m---&8&l&m]--");
        ItemStackBuilder openedCase = ItemStackBuilder.of(Material.CHEST)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 OTWORZONYCH SKRZYNEK &b&l&m---&8&l&m]--");
        ItemStackBuilder conqueredPoints = ItemStackBuilder.of(Material.FISHING_ROD)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 ZDOBYTYCH PUNKTOW &b&l&m---&8&l&m]--");
        ItemStackBuilder killedPlayers = ItemStackBuilder.of(Material.DIAMOND_SWORD)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 ZABOJSTW &b&l&m---&8&l&m]--");
        ItemStackBuilder deaths = ItemStackBuilder.of(Material.SKULL_ITEM)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 SMIERCI &b&l&m---&8&l&m]--");
        ItemStackBuilder thrownEnderPearls = ItemStackBuilder.of(Material.ENDER_PEARL)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 WYRZUCONYCH PEREL &b&l&m---&8&l&m]--");
        ItemStackBuilder eatenGoldenApples = ItemStackBuilder.of(Material.GOLDEN_APPLE)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 ZJEDZONYCH REFOW &b&l&m---&8&l&m]--");
        ItemStackBuilder eatenEnchantedGoldenApple = ItemStackBuilder.of(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1))
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 ZJEDZONYCH KOXOW &b&l&m---&8&l&m]--");
        ItemStackBuilder spentTime = ItemStackBuilder.of(Material.WATCH)
                .name("  &8&l&m--[&b&l&m---&b&l TOP 16 SPEDZONEGO CZASU &b&l&m---&8&l&m]--");

        int i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerMinedStoneRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            minedStone.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.MINED_STONE) + " &7wykopanego stone.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerOpenedCaseRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            openedCase.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.OPENED_CASE) + " &7otworzonych magicznych skrzynek.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerConqueredPointsRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            conqueredPoints.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.CONQUERED_POINTS) + " &7zdobytych punktow.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerKillsRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            killedPlayers.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.KILLS) + " &7zabitych graczy.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerDeathsRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            deaths.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.DEATHS) + " &7smierci.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerThrownEnderPearlsRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            thrownEnderPearls.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.THROWN_PEARLS) + " &7wyrzuconych perel.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerEatenGoldenApplesRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            eatenGoldenApples.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.EATEN_GOLDEN_APPLES) + " &7zjedzonych refow.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerEatenEnchantedGoldenApplesRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            eatenEnchantedGoldenApple.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + corePlayer.getAchievementProgress(AchievementType.EATEN_ENCHANTED_GOLDEN_APPLES) + " &7zjedzonych koxow.");
        }

        i = 0;
        for (CorePlayer corePlayer : this.rankingService.getPlayerSpendTimeRanking()) {
            i++;
            if (i > 16) {
                break;
            }

            spentTime.appendLore("&8&l#&e&l" + i + "&8. &b" + corePlayer.getName() + " &8- &f" + TimeUtil.formatTimeMillis(corePlayer.getAchievementProgress(AchievementType.SPEND_TIME)) + " &7spedzonego czasu.");
        }

        gui.setItem(2, 3, minedStone.asGuiItem());
        gui.setItem(3, 3, openedCase.asGuiItem());
        gui.setItem(4, 3, conqueredPoints.asGuiItem());

        gui.setItem(2, 5, killedPlayers.asGuiItem());
        gui.setItem(3, 5, deaths.asGuiItem());
        gui.setItem(4, 5, thrownEnderPearls.asGuiItem());

        gui.setItem(2, 7, eatenGoldenApples.asGuiItem());
        gui.setItem(3, 7, eatenEnchantedGoldenApple.asGuiItem());
        gui.setItem(4, 7, spentTime.asGuiItem());

        gui.setItem(1, 5, ItemStackBuilder.of(SkullCreator.itemFromUuid(player.getUniqueId()))
                .name("  &8&l&m--[&b&l&m---&b&l TWOJE STATYSTYKI &b&l&m---&8&l&m]--")
                .appendLore("")
                .appendLore("&7Wykopanego stone&8: &f" + core.getAchievementProgress(AchievementType.MINED_STONE))
                .appendLore("&7Otworzonych magicznych skrzynek&8: &f" + core.getAchievementProgress(AchievementType.OPENED_CASE))
                .appendLore("&7Zdobytego rankingu&8: &f" + core.getAchievementProgress(AchievementType.CONQUERED_POINTS))
                .appendLore("&7Zabojstw&8: &f" + core.getAchievementProgress(AchievementType.KILLS))
                .appendLore("&7Smierci&8: &f" + core.getAchievementProgress(AchievementType.DEATHS))
                .appendLore("&7Wyrzuconych perel&8: &f" + core.getAchievementProgress(AchievementType.THROWN_PEARLS))
                .appendLore("&7Zjedzonych refow&8: &f" + core.getAchievementProgress(AchievementType.EATEN_GOLDEN_APPLES))
                .appendLore("&7Zjedzonych koxow&8: &f" + core.getAchievementProgress(AchievementType.EATEN_ENCHANTED_GOLDEN_APPLES))
                .appendLore("&7Spedzonego czasu&8: &f" + TimeUtil.formatTimeMillis(core.getAchievementProgress(AchievementType.SPEND_TIME)))
                .asGuiItem());

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

}
