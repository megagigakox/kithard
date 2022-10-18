package pl.kithard.core.enchant;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

public class CustomEnchantGui {

    private final CorePlugin plugin;

    public CustomEnchantGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, CustomEnchantType type) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lEnchant"))
                .rows(6)
                .create();

        gui.getFiller().fill(ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());
        GuiHelper.fillColorGui6(gui);

        gui.setItem(3, 8, ItemStackBuilder.of(player.getItemInHand().clone()).asGuiItem());

        CustomEnchantWrapper customEnchantWrapper = this.plugin.getCustomEnchantConfiguration().findByType(type);
        for (CustomEnchant customEnchant : customEnchantWrapper.getEnchantments()) {
            int slot = customEnchant.getSlot();
            Enchantment enchantment = customEnchant.getEnchantment();
            gui.setItem(slot, ItemStackBuilder.of(Material.ENCHANTED_BOOK)
                    .name("&3&l" + customEnchant.getName())
                    .lore(
                            "",
                            " &7Koszt zaklecia&8: &f" + customEnchant.getCost(),
                            "",
                            "&7Kliknij &flewym &7aby zenchantowac."
                    )
                    .asGuiItem(event -> {

                        if (player.getGameMode() == GameMode.CREATIVE) {
                            player.getItemInHand().addEnchantment(enchantment, customEnchant.getLevel());
                            player.closeInventory();
                            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie zenchantowano!");
                            return;
                        }

                        if (player.getLevel() >= customEnchant.getCost()) {
                            player.getItemInHand().addEnchantment(enchantment, customEnchant.getLevel());
                            player.setLevel(player.getLevel() - customEnchant.getCost());
                            player.closeInventory();
                            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie zenchantowano!");
                        }
                        else {
                            TextUtil.message(player, "&8(&4&l!&8) &cNie posiadasz wystarczajacej ilosci expa!");
                        }

                    }));

        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }
}
