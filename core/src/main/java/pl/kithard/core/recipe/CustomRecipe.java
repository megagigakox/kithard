package pl.kithard.core.recipe;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public enum CustomRecipe {

    BOY_FARMER(ItemBuilder.from(new ItemStack(159,1, (short) 11))
            .name(TextUtil.component("&8» &9&lBoyFarmer &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw na ziemi aby stworzyc",
                    " &7Sciane &9obsydianu &7do samego &8bedrocka&7!"
            )))
            .glow()
            .build()),
    SAND_FARMER(ItemBuilder.from(new ItemStack(159,1, (short) 4))
            .name(TextUtil.component("&8» &e&lSandFarmer &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw na ziemi aby stworzyc",
                    " &7Sciane &epiasku &7do samego &8bedrocka&7!"
            )))
            .glow()
            .build()),
    AIR_FARMER(ItemBuilder.from(new ItemStack(159,1, (short) 7))
            .name(TextUtil.component("&8» &f&lKopacz Fosy &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw na ziemi aby wykopac",
                    " &7Sciane &7do samego &8bedrocka&7!"
            )))
            .glow()
            .build()),
    STONE_GENERATOR(ItemBuilder.from(new ItemStack(Material.ENDER_STONE,1))
            .name(TextUtil.component("&8» &3&lStoniarka &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw na ziemi aby stworzyc",
                    " &7Nieskonczony &bgenerator &7kamienia!"
            )))
            .glow()
            .build()),
    COBBLEX(ItemBuilder.from(new ItemStack(Material.MOSSY_COBBLESTONE,1))
            .name(TextUtil.component("&8» &2&lCobbleX &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Postaw na ziemi aby wydropic",
                    " &eCenne itemki!"
            )))
            .glow()
            .build()),
    THROWN_TNT(ItemBuilder.from(new ItemStack(Material.TNT,1))
            .name(TextUtil.component("&8» &c&lRzucane TNT &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Kliknij aby wyrzucic",
                    " &7Odpalone &cTNT &7w powietrze!"
            )))
            .glow()
            .build()),
    ANTI_LEGS(ItemBuilder.from(new ItemStack(Material.GOLD_BOOTS,1))
            .name(TextUtil.component("&8» &6&lAnty Nogi &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Kliknij aby &fprzeteleportowac &7sie",
                    " &7Do gracza ktory robi ci nogi!"
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
    SVIP_VOUCHER(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&8» &7Voucher na: &6SVIP &7na edycje &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Kliknij aby aktywowac!"
            )))
            .glow()
            .build()),
    SPONSOR_VOUCHER(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&8» &7Voucher na: &9SPONSOR &7na edycje &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Kliknij aby aktywowac!"
            )))
            .glow()
            .build()),
    TURBO_DROP_VOUCHER_30MIN(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&8» &7Voucher na: &bTURBODROP &7na 30min &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Kliknij aby aktywowac!"
            )))
            .glow()
            .build()),
    TURBO_DROP_VOUCHER_60MIN(ItemBuilder.from(new ItemStack(Material.PAPER,1))
            .name(TextUtil.component("&8» &7Voucher na: &bTURBODROP &7na 1h &8«"))
            .lore(TextUtil.component(Arrays.asList(
                    "",
                    " &7Kliknij aby aktywowac!"
            )))
            .glow()
            .build()),
    ENDER_CHEST(ItemBuilder.from(new ItemStack(Material.ENDER_CHEST,1))
            .build());

    private final ItemStack item;

    CustomRecipe(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}