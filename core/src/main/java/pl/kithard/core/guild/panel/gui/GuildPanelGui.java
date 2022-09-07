package pl.kithard.core.guild.panel.gui;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.log.gui.GuildLogGui;
import pl.kithard.core.guild.permission.gui.GuildPermissionGui;
import pl.kithard.core.guild.regen.gui.RegenGui;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.command.GuildInfoCommand;
import pl.kithard.core.guild.periscope.GuildPeriscope;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.SkullCreator;
import pl.kithard.core.util.TextUtil;

public class GuildPanelGui {

    private final CorePlugin plugin;

    public GuildPanelGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void openPanel(Player player, Guild guild) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lPanel gildyjny"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(1,5, ItemStackBuilder.of(SkullCreator.itemFromUuid(guild.getOwner()))
                .name("     &8&l&m--[&b&l&m---&b&l " + guild.getTag() + " &b&l&m---&8&l&m]--")
                .lore(GuildInfoCommand.guildInfo(guild, plugin))
                .asGuiItem());

        gui.setItem(2,5, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTdjNjZmNWE0YjQwODAwNWIzMzZkYTY2NzZlOGY2YTJhNjdlZWEzMTVmYjdlOTEzNjBhY2MwNDc4MDJmYTMyMCJ9fX0="))
                .name("&3&lUprawnienia czlonkow")
                .lore(
                        " &7Chcesz zabezpieczyć &bswoja gildie",
                        " &7przed ewentualnym &foszustem&7?",
                        " &7W tej zakladce mozesz w &bzaawansowany &7sposób",
                        " &7edytować &3uprawnienia &7swoim czlonkom gildii.",
                        "",
                        "&7Kliknij aby &fprzejsc &7do tej zakladki!"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.PERMISSION_MANAGE)) {
                        return;
                    }

                    new GuildPermissionGui(plugin).openMembersList(player, guild);

                }));

        gui.setItem(3,3, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1NjhhMTUyZGI1NzA1OTNjYzg5ZmFiYzAzMWJlY2RiOWJkYmRkZDFlYmI5MDM3ZWJhZTQ4ZGE5OGYwZTdjNyJ9fX0="))
                .name("&3&lRegeneracja cuboida")
                .lore(
                        "&7Twoja gildia zostala &3przebita",
                        " &7i nie chce Ci sie jej odbudowywac?",
                        " &7W tej zakladce mozesz &bzregenerowac &7swoja gildie",
                        " &7do stanu przed wybuchem &ctnt&7!",
                        "",
                        "&7Kliknij aby &fprzejsc &7do tej zakladki!"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.GUILD_REGEN_ACCESS)) {
                        return;
                    }

                    new RegenGui(plugin).open(player, guild);
                }));

        gui.setItem(3,7, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODEyMmE1MDNkN2E2ZjU3ODAyYjAzYWY3NjI0MTk0YTRjNGY1MDc3YTk5YWUyMWRkMjc2Y2U3ZGI4OGJjMzhhZSJ9fX0="))
                .name("&3&lSchematy uprawnien")
                .lore(
                        " &7Nie chce Ci sie nadawac tych &fsamych uprawnien",
                        " &7czlonkom z osobna?",
                        " &7W tej zakladce mozesz &fstworzyc &7swoj schemat",
                        " &7lub wybrac juz istniejacy",
                        " &7i edytować jego &3uprawnienia&7, a następnie nadawać go swoim czlonkom.",
                        "",
                        "&7Kliknij aby &fprzejsc &7do tej zakladki!"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.PERMISSION_MANAGE)) {
                        return;
                    }

                    new GuildPermissionGui(plugin).openPermissionSchemeList(player, guild);

                }));

        gui.setItem(4,5, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJjMmE5ZTNiOTkxNDkyYWNkNjg0ODA0ZjQ2NmJkZTY4MTNmODcyYWU5MDZkNmE2NWQ4YWQ2YTZmYjQwMDA1YyJ9fX0="))
                .name("&3&lMagazyn")
                .lore(
                        " &7Brakuje twojej gildii miejsc w skrzynkach?",
                        " &7Lub po prostu &fchcecie &7mieć &3wspolne itemy &7w jednym miejscu?",
                        " &7Po kliknieciu otworzy Ci się &bprzenosna skrzynka",
                        " &7w ktorej mozecie chowac &fswoje itemy",
                        " &7i miec do nich dostep z &3kazdego miejsca &7na mapie.",
                        "",
                        "&7Kliknij aby &fprzejsc &7do tej zakladki!"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.WAREHOUSE_ACCESS)) {
                        return;
                    }

                    guild.openWarehouse(player);
                }));

        gui.setItem(5,3, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRiODlhZDA2ZDMxOGYwYWUxZWVhZjY2MGZlYTc4YzM0ZWI1NWQwNWYwMWUxY2Y5OTlmMzMxZmIzMmQzODk0MiJ9fX0="))
                .name("&3&lLogi gildyjne")
                .lore(
                        " &7Chcesz zobaczyc kto &bzarzadza &7twoja gildia?",
                        " &7W tej zakladce sprawdzisz &3logi &7wszystkie akcji",
                        " &7ktore wykonuja zastepcy/czlonkowie majace wyzsze uprawnienia",
                        " &7np. &bwyrzucanie czlonkow z gldii&7, &bzawieranie sojuszy&7.",
                        "",
                        "&7Kliknij aby &fprzejsc &7do tej zakladki!"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.VIEWING_LOGS)) {
                        return;
                    }

                    new GuildLogGui(plugin).open(player, guild);

                }));

        gui.setItem(5,7, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJkYzgzNjQ5M2ZmZjNlNDk2ZTRjNTU3NmI0M2FmMzRjOTFkMzNjZmFiYTEyODE0MGM3YWRiZGNmMzA0NSJ9fX0="))
                .name("&3&lPeryskop")
                .lore(
                        " &7Chcialbys zobaczyc co sie dzieje na zewnatrz twojego cuboida",
                        " &7nie wychodzac przy tym z srodka?",
                        " &7Po kliknieciu &bprzeteleportujesz sie &7jako &fobserwator",
                        " &7na gore swojego &3cuboida &7aby moc obserwowac klepy",
                        " &7lub &cintruzow &7ktorzy wkraczaja na teren twojej gildii.",
                        "",
                        "&7Kliknij aby &fprzejsc &7do tej zakladki!"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.PERISCOPE_ACCESS)) {
                        return;
                    }

                    if (!guild.getRegion().isInHeart(player.getLocation())) {
                        TextUtil.message(player, "&8[&4&l!&8] &cAby wejsc w tryb obserwatora musisz w sercu gildii!");
                        return;
                    }

                    new GuildPeriscope(this.plugin).start(guild.findMemberByUuid(player.getUniqueId()), guild);
                }));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }


}
