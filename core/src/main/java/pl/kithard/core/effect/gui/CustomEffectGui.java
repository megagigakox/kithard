package pl.kithard.core.effect.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.effect.CustomEffect;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class CustomEffectGui {

    private final CorePlugin plugin;

    public CustomEffectGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Zakup efekt:"))
                .rows(6)
                .create();

        GuiHelper.fillEffectsGui(gui);

        for (CustomEffect customEffect : this.plugin.getCustomEffectCache().getEffects()) {

            gui.setItem(customEffect.getSlot(), ItemBuilder.from(customEffect.getIcon())
                    .asGuiItem(inventoryClickEvent -> {

                        if (player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), customEffect.getCost())) {
                            InventoryUtil.removeItem(player, Material.EMERALD_BLOCK, customEffect.getCost());

                            for (PotionEffect potionEffect : customEffect.getPotionEffects()) {
                                player.removePotionEffect(potionEffect.getType());
                                player.addPotionEffect(potionEffect);
                            }

                            gui.close(player);

                            TextUtil.message(player, "&aZakupiono efekt!");

                        } else {

                            TextUtil.message(player, "&cNie stac cie na kupno!");

                        }

                    }));

        }

        gui.setItem(5,5, ItemBuilder.from(Material.MILK_BUCKET)
                .name(TextUtil.component("&f&lWyczysc efekty"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &8» &7Koszt: &f5 &7blokow emeraldow.",
                        " &8» &7Po zakupie &fwczyszczone &7zostana: ",
                        "  &8- &bSila&8, &bSzybkosc&8, &bWysokie skakanie",
                        "",
                        " &aKliknij aby wyczyscic efekty!"
                )))
                .asGuiItem(inventoryClickEvent -> {


                    if (player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), 5)) {
                        player.getInventory().remove(new ItemStack(Material.EMERALD_BLOCK, 5));

                        player.removePotionEffect(PotionEffectType.SPEED);
                        player.removePotionEffect(PotionEffectType.JUMP);
                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

                        TextUtil.message(player, "&aWyczyszczono efekty!");

                    } else {

                        TextUtil.message(player, "&cNie stac cie na kupno!");

                    }

                }));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

}
