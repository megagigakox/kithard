package pl.kithard.core.drop;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.drop.special.SpecialDropItem;
import pl.kithard.core.drop.special.SpecialDropItemType;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.ItemStackBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DropItemConfiguration extends OkaeriConfig {

    private List<DropItem> dropItems = new ArrayList<>();
    private List<SpecialDropItem> specialDropItems = new ArrayList<>();

    public DropItemConfiguration() {
//        this.dropItems.add(new DropItem("Diament", new ItemStack(Material.DIAMOND), 6, 10, true, false));
//        this.specialDropItems.add(new SpecialDropItem("Diament", new ItemStack(Material.DIAMOND), 6, 1, 5, SpecialDropItemType.COBBLEX));
//        this.specialDropItems.add(new SpecialDropItem("Helm 4/3", ItemStackBuilder.of(Material.IRON_HELMET).amount(1)
//                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                .enchantment(Enchantment.DURABILITY, 3)
//                .asItemStack(), 6.03, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Klata 4/3", ItemStackBuilder.of(Material.IRON_CHESTPLATE).amount(1)
//                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                .enchantment(Enchantment.DURABILITY, 3)
//                .asItemStack(), 6.03, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Spodnie 4/3", ItemStackBuilder.of(Material.IRON_LEGGINGS).amount(1)
//                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                .enchantment(Enchantment.DURABILITY, 3)
//                .asItemStack(), 6.03, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Buty 4/3", ItemStackBuilder.of(Material.IRON_BOOTS).amount(1)
//                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                .enchantment(Enchantment.DURABILITY, 3)
//                .asItemStack(), 6.03, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Miecz 4/1", ItemStackBuilder.of(Material.IRON_SWORD).amount(1)
//                .enchantment(Enchantment.DAMAGE_ALL, 4)
//                .enchantment(Enchantment.FIRE_ASPECT, 1)
//                .asItemStack(), 6.03, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Luk 3/1", ItemStackBuilder.of(Material.BOW).amount(1)
//                .enchantment(Enchantment.ARROW_DAMAGE, 3)
//                .enchantment(Enchantment.ARROW_FIRE, 1)
//                .asItemStack(), 6.03, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Kox jablko", new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1), 6, 1, 3, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Refile", new ItemStack(Material.GOLDEN_APPLE, 1), 6, 1, 8, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Perla kresu", new ItemStack(Material.ENDER_PEARL, 1), 6, 1, 3, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Bloki zlota", new ItemStack(Material.GOLD_BLOCK, 1), 6, 1, 16, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Bloki emeraldow", new ItemStack(Material.GOLDEN_APPLE, 1), 6, 1, 32, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Kowadlo", new ItemStack(Material.ANVIL, 1), 6, 1, 8, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Voucher svip edycja", CustomRecipe.SVIP_VOUCHER.getItem().clone(), 6, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Voucher sponsor edycja", CustomRecipe.SPONSOR_VOUCHER.getItem().clone(), 6, 1, 1, SpecialDropItemType.MAGIC_CHEST));
//        this.specialDropItems.add(new SpecialDropItem("Voucher TURBODROP 30min", CustomRecipe.TURBO_DROP_VOUCHER_30MIN.getItem().clone(), 6, 1, 1, SpecialDropItemType.MAGIC_CHEST));
    }

    public Collection<DropItem> getDropItems() {
        return dropItems;
    }

    public Collection<SpecialDropItem> getSpecialDropItems() {
        return specialDropItems;
    }

}
