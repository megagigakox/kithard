package pl.kithard.core.settings;

import org.bukkit.Material;
import pl.kithard.core.api.database.entity.DatabaseEntity;

@DatabaseEntity(database = "core", collection = "settings")
public enum ServerSettingsType {

    CHAT("Chat", Material.PAPER),
    TNT("TNT", Material.TNT),
    DIAMOND_ITEMS("Diamentowe itemy", Material.DIAMOND_CHESTPLATE),
    MAGIC_CHEST("Magiczne skrzynki", Material.CHEST),
    ENCHANT("Enchant", Material.ENCHANTMENT_TABLE),
    BEACON("Beacon", Material.BEACON),
    FISHING_RODS("Wedki", Material.FISHING_ROD);

    private final String name;
    private final Material icon;

    ServerSettingsType(String name, Material icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }
}
