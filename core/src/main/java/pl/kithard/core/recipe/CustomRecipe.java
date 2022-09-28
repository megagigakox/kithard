package pl.kithard.core.recipe;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public enum CustomRecipe {

    BOY_FARMER(ItemBuilder.from(new ItemStack(159,1, (short) 11))
            .name(TextUtil.component("&5&lWykladacz obsydianu"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw ten blok na ziemi, aby rozpoczac",
                    " &7generowanie sie &5obsydianu&7!",
                    ""
            )))
            .glow()
            .build()),
    SAND_FARMER(ItemBuilder.from(new ItemStack(159,1, (short) 4))
            .name(TextUtil.component("&e&lWykladacz piasku"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw ten blok na ziemi, aby rozpoczac",
                    " &7generowanie sie &epiasku&7!",
                    ""
            )))
            .glow()
            .build()),
    AIR_FARMER(ItemBuilder.from(new ItemStack(159,1, (short) 7))
            .name(TextUtil.component("&f&lKopacz Fosy"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw ten blok na ziemi, aby rozpoczac",
                    " &7generowanie &fpustej przestrzeni&7!",
                    ""
            )))
            .glow()
            .build()),
    STONE_GENERATOR(ItemBuilder.from(new ItemStack(Material.ENDER_STONE,1))
            .name(TextUtil.component("&3&lGenerator kamienia"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw ten blok na ziemi, aby stworzyc",
                    " &7nieskonczony &bgenerator &7kamienia!",
                    ""
            )))
            .glow()
            .build()),
    COBBLEX(ItemBuilder.from(new ItemStack(Material.MOSSY_COBBLESTONE,1))
            .name(TextUtil.component("&2&lCobbleX"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw na ziemi aby wylosowac itemki!",
                    " &7Sprawdz drop pod komenda&8: /&fdrop",
                    ""
            )))
            .glow()
            .build()),
    ANTI_LEGS(ItemBuilder.from(new ItemStack(Material.GOLD_BOOTS,1))
            .name(TextUtil.component("&6&lAnty Nogi"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Item ktory pomoze Tobie uciec z &fnozek&7.",
                    " &bZabieraj go ze soba na kazda klepe!",
                    ""
            )))
            .glow()
            .build()),
    CROWBAR(ItemBuilder.from(new ItemStack(Material.TRIPWIRE_HOOK,1))
            .name(TextUtil.component("&b&lŁOM"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Item potrzebny do przejęcia czyjegos &3sejfu&7!",
                    " &7Mozliwy do wydropienia z &bmagicznych skrzynek",
                    " &7lub do kupna w naszym &fitemshopie&7!"
            )))
            .glow()
            .build()),
    MAGIC_CHEST(ItemBuilder.from(new ItemStack(Material.CHEST,1))
            .name(TextUtil.component(" "))
            .lore(TextUtil.component(Arrays.asList(
                    "     &b&l&nMagiczna Skrzynka",
                    "  &3Wyjatkowo cenny przedmiot.",
                    "",
                    "  &7Jest to skrzynia z ktorej",
                    "       &7możesz wydropic...",
                    "  &fniesamowicie cenne itemy!",
                    "",
                    " &aPoloż na ziemi aby ja otworzyc!"
            )))
            .build()),
    VIP_VOUCHER(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&7Voucher na range &e&lVIP"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Czas trwania rangi&8: &bcała edycja",
                    "",
                    "&7Kliknij &fprawym &7aby aktywowac!"
            )))
            .glow()
            .build()),
    SVIP_VOUCHER(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&7Voucher na range &6&lSVIP"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Czas trwania rangi&8: &bcała edycja",
                    "",
                    "&7Kliknij &fprawym &7aby aktywowac!"
            )))
            .glow()
            .build()),
    SPONSOR_VOUCHER(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&7Voucher na range &b&lSponsor"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Czas trwania rangi&8: &bcała edycja",
                    "",
                    "&7Kliknij &fprawym &7aby aktywowac!"
            )))
            .glow()
            .build()),
    TURBO_DROP_VOUCHER_30MIN(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&7Voucher na &b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &f&l30min"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    "&7Kliknij &fprawym &7aby aktywowac!"
            )))
            .glow()
            .build()),
    TURBO_DROP_VOUCHER_60MIN(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&7Voucher na &b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &f&l60min"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    "&7Kliknij &fprawym &7aby aktywowac!"
            )))
            .glow()
            .build()),
    ENDER_CHEST(ItemBuilder.from(new ItemStack(Material.ENDER_CHEST,1))
            .build()),
    PICKAXE633(ItemBuilder.from(new ItemStack(Material.DIAMOND_PICKAXE,1))
            .name(TextUtil.component("&3&lLegendarny kilof 6/3/3"))
            .enchant(Enchantment.DIG_SPEED, 6)
            .enchant(Enchantment.LOOT_BONUS_BLOCKS, 3)
            .enchant(Enchantment.DURABILITY, 3)
            .build());

    private final ItemStack item;

    CustomRecipe(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}