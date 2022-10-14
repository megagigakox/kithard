package pl.kithard.core.trade;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemStackBuilder;

import java.util.*;

public class Trade {

    private final Player sender;
    private final Player receiver;

    private boolean senderAccept = false;
    private boolean receiverAccept = false;
    private boolean finished = false;

    private final Inventory inventory;

    public Trade(Player sender, Player receiver, Inventory inventory) {
        this.sender = sender;
        this.receiver = receiver;
        this.inventory = inventory;
    }

    public void refreshStatus() {
        this.inventory.setItem(3, ItemStackBuilder.of(this.senderAccept ? new ItemStack(351, 1, (short) 10) : new ItemStack(351, 1, (short) 8))
                .name("&b" + this.sender.getName() + (this.senderAccept ? " &7jest gotowy do wymiany." : " &7nie jest gotowy do wymiany"))
                .asItemStack());

        this.inventory.setItem(5, ItemStackBuilder.of(this.receiverAccept ? new ItemStack(351, 1, (short) 10) : new ItemStack(351, 1, (short) 8))
                .name("&b" + this.receiver.getName() + (this.receiverAccept ? " &7jest gotowy do wymiany." : " &7nie jest gotowy do wymiany"))
                .asItemStack());
    }

    public Player getOppositePlayer(Player player) {
        if (this.sender.getUniqueId().equals(player.getUniqueId())) {
            return this.receiver;
        }
        if (this.receiver.getUniqueId().equals(player.getUniqueId())) {
            return this.sender;
        }

        return null;
    }

    private List<ItemStack> getItemsFrom(List<Integer> slots) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int slot : slots) {
            ItemStack item = this.inventory.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                itemStacks.add(item);
            }
        }

        return itemStacks;
    }

    public List<Integer> getAllowedSlotsByPlayer(Player player) {
        if (player.getUniqueId().equals(this.sender.getUniqueId())) {
            return TradeConfiguration.LEFT_SLOTS;
        }
        else if (player.getUniqueId().equals(this.receiver.getUniqueId())) {
            return TradeConfiguration.RIGHT_SLOTS;
        }

        return null;
    }

    public void giveBackItems() {
        InventoryUtil.addItem(this.sender, getItemsFrom(TradeConfiguration.LEFT_SLOTS));
        InventoryUtil.addItem(this.receiver, getItemsFrom(TradeConfiguration.RIGHT_SLOTS));
    }

    public void tryAccept() {
        if (this.receiverAccept && this.senderAccept) {
            this.finished = true;
            InventoryUtil.addItem(this.sender, getItemsFrom(TradeConfiguration.RIGHT_SLOTS));
            InventoryUtil.addItem(this.receiver, getItemsFrom(TradeConfiguration.LEFT_SLOTS));

            this.receiver.closeInventory();
            this.sender.closeInventory();
        }

    }

    public void openInventory() {
        refreshStatus();

        this.sender.openInventory(inventory);
        this.receiver.openInventory(inventory);
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isSenderAccept() {
        return senderAccept;
    }

    public void setSenderAccept(boolean senderAccept) {
        this.senderAccept = senderAccept;
    }

    public boolean isReceiverAccept() {
        return receiverAccept;
    }

    public void setReceiverAccept(boolean receiverAccept) {
        this.receiverAccept = receiverAccept;
    }

    public boolean isFinished() {
        return finished;
    }
}
