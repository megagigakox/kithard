package pl.kithard.core.guild.regen.gui;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.panel.gui.GuildPanelGui;
import pl.kithard.core.guild.regen.RegenBlock;
import pl.kithard.core.guild.regen.RegenTask;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegenGui {

    private final CorePlugin plugin;

    public RegenGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, Guild guild) {
        Gui gui = Gui.gui()
                .rows(5)
                .title(TextUtil.component("&3&lZregeneruj cuboid"))
                .create();

        GuiHelper.fillColorGui5(gui);
        gui.setItem(5, 5, ItemStackBuilder.of(GuiHelper.BACK_ITEM)
                .asGuiItem(event -> new GuildPanelGui(plugin).openPanel(player, guild)));

        Set<RegenBlock> regenBlocks = guild.getRegenBlocks();
        int cost = (int) (regenBlocks.size() / 1.5);

        ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(Material.LEVER)
                .name("&3&lRegeneracja");

        itemStackBuilder.appendLore(
                "",
                " &7Koszt regeneracji: &f" + cost + " blokow emeraldow.",
                " &7Blokow do zregenerowania: &f" + regenBlocks.size(),
                "",
                "&7Kliknij &fprawym &7aby rozpoczac regeneracje!"
        );

        gui.setItem(3, 5, itemStackBuilder.asGuiItem(event -> {

            if (regenBlocks.isEmpty()) {
                TextUtil.message(player, "&8[&4&l!&8] &cNie ma zadnych blokow do regeneracji!");
                return;
            }

            if (!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), cost)) {
                TextUtil.message(
                        player,
                        "&8[&4&l!&8] &cNie posiadasz wystarczajacej ilosci blokow emeraldow! &4(" + cost + " blokow)"
                );
                return;
            }

            if (this.plugin.getRegenCache().getCurrentlyRegeneratingGuilds().contains(guild.getTag())) {
                TextUtil.message(player, "&8[&4&l!&8] &cTwoj teren gildii juz jest podczas regeneracji!");
                return;
            }

            player.closeInventory();
            InventoryUtil.removeItems(player.getInventory(), new ItemStack(Material.EMERALD_BLOCK), cost);
            TextUtil.message(player, "&8[&2&l!&8] &aPomyslnie rozpoczeto regeneracje terenu gildii!");
            this.plugin.getRegenCache().getCurrentlyRegeneratingGuilds().add(guild.getTag());
            new RegenTask(this.plugin, new HashSet<>(guild.getRegenBlocks()), player.getUniqueId(), guild);
            guild.getRegenBlocks().clear();
            guild.setNeedSave(true);

        }));

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }
}
