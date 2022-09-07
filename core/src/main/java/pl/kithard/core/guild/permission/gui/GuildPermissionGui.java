package pl.kithard.core.guild.permission.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.guild.panel.gui.GuildPanelGui;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.guild.permission.GuildPermissionScheme;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.SkullCreator;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class GuildPermissionGui {
    private final CorePlugin plugin;

    public GuildPermissionGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void openMembersList(Player player, Guild guild) {
        PaginatedGui gui = Gui.paginated()
                .title(TextUtil.component("&3&lCzlonkowie"))
                .rows(6)
                .pageSize(28)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6,7, ItemBuilder.from(GuiHelper.NEXT_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.next()));

        gui.setItem(6,3, ItemBuilder.from(GuiHelper.PREVIOUS_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.previous()));

        gui.setItem(6,5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> new GuildPanelGui(plugin).openPanel(player, guild)));

        for (GuildMember guildMember : guild.getMembers()) {

            ItemStack skull = ItemBuilder.skull().owner(Bukkit.getOfflinePlayer(guildMember.getUuid()))
                    .name(TextUtil.component("&b&l" + guildMember.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Kliknij &flewym &7aby &3zarzadzac &7permisjami!",
                            " &7Kliknij &fprawym &7aby &3nadac &7schemat permisji!")))
                    .build();

            gui.addItem(ItemBuilder.from(skull)
                    .asGuiItem(inventoryClickEvent -> {

                        GuildMember memberFromSkull = guild.findMemberByName(ChatColor.stripColor(skull.getItemMeta().getDisplayName()));

                        if (memberFromSkull == null) {
                            TextUtil.message(player,"&cGuildMember jest aktualnie nullem, odswieżam liste czlonkow.");
                            player.closeInventory();
                            this.openMembersList(player, guild);
                            return;
                        }

                        if (guild.isDeputyOrOwner(memberFromSkull.getUuid())) {
                            TextUtil.message(player,"&8[&4&l!&8] &cNie możesz zmienic uprawnien &4lidera/zastepcy &cgildii!");
                            return;
                        }

                        if (memberFromSkull.getUuid().equals(player.getUniqueId())) {
                            TextUtil.message(player,"&8[&4&l!&8] &cNie możesz zmienic swoich uprawnien!");
                            return;
                        }

                        if (inventoryClickEvent.getClick() == ClickType.LEFT) {
                            this.openPermissionManage(
                                    player,
                                    guildMember,
                                    1,
                                    guild
                            );
                        }
                        else if (inventoryClickEvent.getClick() == ClickType.RIGHT) {
                            this.openPermissionSchemeChoose(
                                    player,
                                    guildMember,
                                    guild
                            );
                        }
                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openPermissionManage(Player player, GuildMember member, int page, Guild guild) {
        PaginatedGui gui = Gui.paginated()
                .title(TextUtil.component("&7Uprawnienia&8: &3" + member.getName()))
                .rows(6)
                .pageSize(28)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6,6, ItemBuilder.from(GuiHelper.NEXT_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.next()));

        gui.setItem(6,4, ItemBuilder.from(GuiHelper.PREVIOUS_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.previous()));

        gui.setItem(6,5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> this.openMembersList(player, guild)));

        gui.setItem(6,2, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 2))
                .name(TextUtil.component("&aDaj dostep do wszystkiego"))
                .asGuiItem(inventoryClickEvent -> {
                    member.togglePermissions(true);
                    guild.setNeedSave(true);
                    openPermissionManage(player, member, gui.getCurrentPageNum(), guild);

                }));

        gui.setItem(6,8, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 1))
                .name(TextUtil.component("&cZabierz dostep do wszystkiego"))
                .asGuiItem(inventoryClickEvent -> {
                    member.togglePermissions(false);
                    guild.setNeedSave(true);
                    openPermissionManage(player, member, gui.getCurrentPageNum(), guild);
                }));

        for (GuildPermission entry : GuildPermission.values()) {
            gui.addItem(ItemBuilder
                    .from(new ItemStack(entry.getIcon()))
                    .name(TextUtil.component("&7Akcja&8: &3" + entry.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Status: " + (!member.isNotAllowed(entry) ? "&a✔ posiada dostep." : "&c✖ &cnie posiada dostepu."),
                            "",
                            "&7Kliknij tutaj &faby zmienic &7status!")))
                    .glow(!member.isNotAllowed(entry))
                    .asGuiItem(inventoryClickEvent -> {

                        member.togglePermission(entry);
                        guild.setNeedSave(true);
                        this.openPermissionManage(
                                player,
                                member,
                                gui.getCurrentPageNum(),
                                guild);
                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player, page);
    }

    public void openPermissionSchemeList(Player player, Guild guild) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lSchematy uprawnien"))
                .rows(3)
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(3, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> new GuildPanelGui(plugin).openPanel(player, guild)));

        for (GuildPermissionScheme scheme : guild.getPermissionSchemes()) {
            gui.addItem(ItemBuilder.from(Material.ITEM_FRAME)
                    .name(TextUtil.component("&7Schemat&8: &b" + scheme.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Kliknij tutaj aby &fedytowac &7ten schemat!")))
                    .asGuiItem(inventoryClickEvent ->
                            openPermissionSchemeManage(player, scheme,1, guild)));
        }

        gui.setItem(1,5, ItemBuilder.from(Material.HOPPER)
                .name(TextUtil.component("&aStworz nowy schemat"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij aby &fstworzyc &7nowy schemat!")))
                .asGuiItem(inventoryClickEvent -> {
                    player.closeInventory();
                    new AnvilGUI.Builder()
                            .onComplete((p, text) -> {

                                GuildPermissionScheme guildPermissionScheme = guild.findPermissionSchemeByName(text.toUpperCase());
                                if (guild.getPermissionSchemes().size() >= 7) {
                                    TextUtil.message(p,"&8[&4&l!&8] &cOsiagnales limit schematow!");
                                    return AnvilGUI.Response.close();
                                }

                                if (guildPermissionScheme != null) {
                                    TextUtil.message(p,"&8[&4&l!&8] &cSchemat o tej nazwie juz istnieje!");
                                    return AnvilGUI.Response.close();
                                }

                                guild.getPermissionSchemes().add(new GuildPermissionScheme(text.toUpperCase()));
                                guild.setNeedSave(true);
                                this.openPermissionSchemeList(p, guild);
                                return AnvilGUI.Response.close();
                            })
                            .text("Wprowadz nazwe schematu:")
                            .title("Wprowadz nazwe schematu:")
                            .plugin(plugin)
                            .open(player);
                }));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openPermissionSchemeManage(Player player, GuildPermissionScheme scheme, int page, Guild guild) {
        PaginatedGui gui = Gui.paginated()
                .title(TextUtil.component("&7Schemat&8: &3" + scheme.getName()))
                .rows(6)
                .pageSize(28)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6, 4, ItemBuilder.from(GuiHelper.PREVIOUS_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.previous()));
        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> this.openPermissionSchemeList(player, guild)));
        gui.setItem(6, 6, ItemBuilder.from(GuiHelper.NEXT_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.next()));

        gui.setItem(1, 5, ItemBuilder.from(Material.BARRIER)
                .name(TextUtil.component("&cUsun schemat"))
                .asGuiItem(inventoryClickEvent -> {
                    guild.getPermissionSchemes().remove(scheme);
                    guild.setNeedSave(true);

                    this.openPermissionSchemeList(player, guild);
                }));

        gui.setItem(6,2, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 2))
                .name(TextUtil.component("&aDaj dostep do wszystkiego"))
                .asGuiItem(inventoryClickEvent -> {
                    scheme.togglePermissions(true);
                    guild.setNeedSave(true);
                    this.openPermissionSchemeManage(player, scheme, gui.getCurrentPageNum(), guild);

                }));

        gui.setItem(6,8, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 1))
                .name(TextUtil.component("&cZabierz dostep do wszystkiego"))
                .asGuiItem(inventoryClickEvent -> {
                    scheme.togglePermissions(false);
                    guild.setNeedSave(true);
                    openPermissionSchemeManage(player, scheme, gui.getCurrentPageNum(), guild);
                }));

        for (GuildPermission entry : GuildPermission.values()) {
            gui.addItem(ItemBuilder.from(new ItemStack(entry.getIcon()))
                    .name(TextUtil.component("&7Akcja&8: &b" + entry.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Status: " + (scheme.getAllowedPermissions().contains(entry) ? "&a✔ posiada dostep." : "&c✖ &cnie posiada dostepu."),
                            "",
                            "&7Kliknij tutaj &faby zmienic &7status!")))
                    .glow(scheme.getAllowedPermissions().contains(entry))
                    .asGuiItem(e -> {

                        scheme.togglePermission(entry);
                        guild.setNeedSave(true);
                        openPermissionSchemeManage(player, scheme, gui.getCurrentPageNum(), guild);

                    }));
        }
        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player, page);
    }

    public void openPermissionSchemeChoose(Player player, GuildMember member, Guild guild) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Wybierz schemat dla&8: &3" + member.getName()))
                .rows(3)
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(3, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> openMembersList(player, guild)));

        for (GuildPermissionScheme scheme : guild.getPermissionSchemes()) {
            gui.addItem(ItemBuilder.from(Material.ITEM_FRAME)
                    .name(TextUtil.component("&7Schemat&8: &b" + scheme.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Kliknij &faby nadac &7ten schemat!")))
                    .asGuiItem(event -> {

                        member.getAllowedPermissions().clear();
                        for (GuildPermission permission : scheme.getAllowedPermissions()) {
                            member.getAllowedPermissions().add(permission);
                        }

                        guild.setNeedSave(true);
                        this.openPermissionManage(player, member, 1, guild);
                    }));
        }
        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

}
