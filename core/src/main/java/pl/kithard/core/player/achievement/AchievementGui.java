package pl.kithard.core.player.achievement;

import dev.triumphteam.gui.guis.Gui;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.util.*;

import java.util.Locale;

public class AchievementGui {

    private final CorePlugin plugin;

    public AchievementGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .rows(3)
                .title(TextUtil.component("&9&lOsiagniecia"))
                .create();

        GuiHelper.fillColorGui3(gui);

        int i = 8;
        for (AchievementType value : AchievementType.values()) {
            i++;
            gui.setItem(i, ItemStackBuilder.of(value.getIcon())
                    .name("&3&l" + value.getName().toUpperCase(Locale.ROOT))
                    .lore(
                            "",
                            " &7Kliknij aby &fotworzyć &7liste osiagniec."
                    )
                    .asGuiItem(event -> openAchievements(player, value)));
        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

    private void openAchievements(Player player, AchievementType type) {
        Gui gui = Gui.gui()
                .rows(3)
                .title(TextUtil.component("&7Osiagniecia&8: &3" + type.getName()))
                .create();


        GuiHelper.fillColorGui3(gui);
        gui.setItem(3, 5, ItemStackBuilder.of(GuiHelper.BACK_ITEM).asGuiItem(event -> open(player)));

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        long progress = corePlayer.getAchievementProgress(type);

        int i = 8;
        for (Achievement achievement : this.plugin.getAchievementCache().findByType(type)) {
            double percentProgress = ((double) progress / (double) achievement.getRequired()) * 100.0;
            long required = achievement.getRequired();

            i++;
            String status;
            if (progress < required) {
                status = "&cNie możesz tego jeszcze odebrać!";
            }
            else if (corePlayer.isAchievementClaimed(achievement)) {
                status = "&aOdebrałeś już to osiągnięcie!";
            }
            else {
                status = "&aKliknij tutaj aby odebrać!";
            }

            gui.setItem(i, ItemStackBuilder.of(type.getIcon())
                    .name("&7Osiagniecie:" + " &8&l#&e&l" + achievement.getId())
                    .lore(
                            "",
                            " &7Nagroda",
                            "  &7- &f" + achievement.getReward().getFriendlyName(),
                            "",
                            " &7Posiadasz: &f" + (type == AchievementType.SPEND_TIME ? TimeUtil.formatTimeMillis(progress) : progress) + "&8/&f" + (type == AchievementType.SPEND_TIME ? TimeUtil.formatTimeMillis(required) : required),
                            " &8(" + TextUtil.progressBar(progress, required, 15, '▌', ChatColor.GREEN, ChatColor.RED) + "&8) &f" + MathUtil.round(percentProgress, 2) + "%",
                            "",
                            status
                    )
                    .asGuiItem(event -> {

                        if (progress < required) {
                            TextUtil.message(player, "&8[&4&l!&8] &cNie mozesz tego jeszcze odebrac!");
                            return;
                        }

                        if (corePlayer.isAchievementClaimed(achievement)) {
                            TextUtil.message(player, "&8[&4&l!&8] &cOdebrales juz to osiagniecie!");
                            return;
                        }

                        corePlayer.addClaimedAchievement(achievement);
                        corePlayer.setNeedSave(true);
                        InventoryUtil.addItem(player, achievement.getReward().getItemStack());
                        for (Player it : this.plugin.getServer().getOnlinePlayers()) {
                            CorePlayer itCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(it);
                            if (itCorePlayer.isDisabledSetting(PlayerSettings.ACHIEVEMENTS_CLAIMING_MESSAGES)) {
                                continue;
                            }

                            TextUtil.message(it, "&8[&3&l!&8] &7Gracz &f" + player.getName() + " &7odebral osiagniecie &7(&8&l#&e&l" + achievement.getId() + " &f" + type.getName() + "&7)");
                        }
                        openAchievements(player, type);
                    }));
        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);

    }
}
