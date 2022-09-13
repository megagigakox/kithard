package pl.kithard.core.player.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

public class PlayerEnderChest {

    public static final Map<Integer, String> REQUIRED_RANK = new HashMap<>();

    static {
        REQUIRED_RANK.put(1, "default");
        REQUIRED_RANK.put(2, "vip");
        REQUIRED_RANK.put(3, "svip");
        REQUIRED_RANK.put(4, "sponsor");
        REQUIRED_RANK.put(5, "legenda");
    }

    private final int id;
    private final ItemStack[] contents;
    private final Inventory inventory;
    private String lore;

    public PlayerEnderChest(int id, ItemStack[] contents, String lore) {
        this.id = id;
        this.lore = lore;
        this.contents = contents;
        this.inventory = Bukkit.createInventory(null, 45, TextUtil.color("&7Enderchest: &8&l#&e&l" + this.id));
    }

    public int getId() {
        return id;
    }

    public String getPermission() {
        return "kithard.enderchest.access." + this.id;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public void closeInventory() {
        this.inventory.setContents(contents);
    }

    public void openInventory(Player player) {
        if (contents != null) {
            this.inventory.setContents(contents);
        }

        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
