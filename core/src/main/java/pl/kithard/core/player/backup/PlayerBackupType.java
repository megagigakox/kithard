package pl.kithard.core.player.backup;

import org.bukkit.Material;

public enum PlayerBackupType {

    DEATH(Material.SKULL_ITEM),
    AUTO(Material.WATCH),
    QUIT(Material.WOOD_DOOR);

    private final Material icon;

    PlayerBackupType(Material icon) {
        this.icon = icon;
    }

    public Material getIcon() {
        return icon;
    }
}
