package pl.kithard.core.player.achievement;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum AchievementType {

    MINED_STONE("Wykopany kamien", new ItemStack(Material.STONE)),
    OPENED_CASE("Otworzone magiczne skrzynki", new ItemStack(Material.CHEST)),
    CONQUERED_POINTS("Zdobyte punkty rankingowe", new ItemStack(Material.FISHING_ROD)),
    KILLS("Zabici gracze", new ItemStack(Material.DIAMOND_SWORD)),
    DEATHS("Smierci", new ItemStack(Material.SKULL_ITEM)),
    THROWN_PEARLS("Wyrzucone ender perly", new ItemStack(Material.ENDER_PEARL)),
    EATEN_GOLDEN_APPLES("Zjedzone zlote jabłka", new ItemStack(Material.GOLDEN_APPLE)),
    EATEN_ENCHANTED_GOLDEN_APPLES("Zjedzone koxy", new ItemStack(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1))),
    SPEND_TIME("Spedzony czas", new ItemStack(Material.WATCH));

    private final String name;
    private final ItemStack icon;

    AchievementType(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }
}
