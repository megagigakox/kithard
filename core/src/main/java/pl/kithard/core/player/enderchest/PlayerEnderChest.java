package pl.kithard.core.player.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.kithard.core.util.TextUtil;

public class PlayerEnderChest {

    private final int id;
    private final Inventory inventory;
    private final String requiredRank;
    private String lore;

    public PlayerEnderChest(int id, String requiredRank, String lore) {
        this.id = id;
        this.requiredRank = requiredRank;
        this.lore = lore;
        this.inventory = Bukkit.createInventory(null, 54, TextUtil.color("&7Enderchest: &8&l#&e&l" + this.id));
    }

    public int getId() {
        return id;
    }

    public String getPermission() {
        return "kithard.enderchest.access." + this.getId();
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getRequiredRank() {
        return requiredRank;
    }
}
