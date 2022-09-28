package pl.kithard.core.safe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.util.UUID;

public class Safe extends DatabaseEntry {

    private final long id;
    private UUID ownerUUID;
    private String ownerName;
    private final String firstOwnerName;
    private final long createTime;
    private ItemStack[] contents;

    private ItemStack item;

    public Safe(long id, UUID ownerUUID, String ownerName, String firstOwnerName) {
        this.id = id;
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.firstOwnerName = firstOwnerName;
        this.createTime = System.currentTimeMillis();
        this.item();
    }

    public Safe(long id, UUID ownerUUID, String ownerName, String firstOwnerName, long createTime, ItemStack[] contents) {
        this.id = id;
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.firstOwnerName = firstOwnerName;
        this.createTime = createTime;
        this.contents = contents;
        this.item();
    }

    public ItemStack item() {
        return this.item = ItemStackBuilder.of(Material.CHEST)
                .name("&b&lSEJF &8&l#&e&l" + this.id)
                .lore(
                        "",
                        "&7Wlasciciel sejfu&8: &f" + this.ownerName + "&8(&b" + this.ownerUUID + "&8)",
                        "&7Data stworzenia&8: &f" + TimeUtil.formatTimeMillisToDate(this.createTime),
                        "&7Pierwotny wlascicel&8: &f" + this.firstOwnerName
                )
                .asItemStack();
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, TextUtil.color(this.item.getItemMeta().getDisplayName()));

        if (this.contents != null) {
            inventory.setContents(this.contents);
        }

        player.openInventory(inventory);
    }

    public long getId() {
        return id;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getFirstOwnerName() {
        return firstOwnerName;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
