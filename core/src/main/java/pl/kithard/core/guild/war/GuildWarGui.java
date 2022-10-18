package pl.kithard.core.guild.war;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class GuildWarGui {

    private final CorePlugin plugin;

    public GuildWarGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, Guild guild) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lWojny"))
                .rows(3)
                .create();

        gui.setItem(Arrays.asList(0, 1, 7, 8), ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(Arrays.asList(2, 6, 19, 21, 22, 23, 25), ItemStackBuilder.of(GuiHelper.CYAN_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(Arrays.asList(3, 5, 18, 20, 24, 26), ItemStackBuilder.of(GuiHelper.LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(1, 5, ItemStackBuilder.of(Material.TNT)
                .name("&c&lWYPOWIEDZ WOJNE")
                .asGuiItem(event -> {

                    player.closeInventory();
                    new AnvilGUI.Builder()
                            .onComplete((p, text) -> {

                                if (!guild.canDeclareWar()) {
                                    TextUtil.message(player, "&8(&4&l!&8) &cProwadzisz juz wojne z inna gildia!");
                                    return AnvilGUI.Response.close();
                                }

                                Guild target = this.plugin.getGuildCache().findByTag(text);
                                if (target == null) {
                                    TextUtil.message(player, "&8(&4&l!&8) &cGildia o podanym tagu nie istnieje!");
                                    return AnvilGUI.Response.close();
                                }

                                if (!target.canDeclareWar()) {
                                    TextUtil.message(player, "&8(&4&l!&8) &cGildia o podanym tagu prowadzi juz z kims wojne!");
                                    return AnvilGUI.Response.close();
                                }

                                GuildWar guildWar = new GuildWar(target.getTag());
                                GuildWar targetWar = new GuildWar(guild.getTag());
                                guild.addWar(guildWar);
                                target.addWar(targetWar);
//                                this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> this.plugin.getGuildRepository().insertScheme(scheme));

                                Bukkit.broadcastMessage(TextUtil.color("&7Gildia &b[" + guild.getTag() + "] &3" + guild.getName() + " &7wypowiedziala wojne gildii &b[" + target.getTag() + "] &3" + target.getName() + "&7!"));

                                open(player, guild);
                                return AnvilGUI.Response.close();
                            })
                            .text("Tag gildii")
                            .title("Tag gildii")
                            .plugin(plugin)
                            .open(player);

                }));

        for (GuildWar guildWar : guild.getGuildWars()) {
            ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(Material.SIGN)
                    .name("&3&lWOJNA &7- &3&l" + guildWar.getEnemyGuild())
                    .lore(
                            "",
                            " &a&lTWOJA GILDIA:",
                            "  &7Tag&8: &f" + guild.getTag(),
                            "  &7Zdobyte zabojstwa&8: &f" + guildWar.getKills(),
                            "  &7Poniesione smierci&8: &f" + guildWar.getDeaths(),
                            "  &7Zdobyte punkty&8: &f" + guildWar.getPoints(),
                            "",
                            " &c&lPRZECIWNA GILDIA:",
                            "  &7Tag&8: &f" + guildWar.getEnemyGuild()
                    );
            Guild enemy = this.plugin.getGuildCache().findByTag(guildWar.getEnemyGuild());

            if (enemy == null) {

                itemStackBuilder.appendLore("  &cNie mozna wczytac wiekszej ilosci danych o tej wojnie...");
                itemStackBuilder.appendLore("  &cPrawdopodobnia gildia &4" + guildWar.getEnemyGuild() + " &czostala usunieta.");
            }
            else {
                GuildWar enemyWar = enemy.findWar(guild.getTag());
                itemStackBuilder.appendLore(
                        "  &7Zdobyte zabojstwa&8: &f" + enemyWar.getKills(),
                        "  &7Poniesione smierci&8: &f" + enemyWar.getDeaths(),
                        "  &7Zdobyte punkty&8: &f" + enemyWar.getPoints()
                );
            }

            gui.addItem(itemStackBuilder.asGuiItem());


        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);

    }

}
