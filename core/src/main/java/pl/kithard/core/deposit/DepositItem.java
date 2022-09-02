package pl.kithard.core.deposit;

import org.bukkit.inventory.ItemStack;

public class DepositItem {

    private final String id;
    private final String name;
    private final int limit;
    private final boolean withdrawAll;
    private final int slot;
    private final String message;
    private final ItemStack item;

    public DepositItem(String id, String name, int limit, boolean withdrawAll, int slot, String message, ItemStack item) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.withdrawAll = withdrawAll;
        this.slot = slot;
        this.message = message;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getLimit() {
        return limit;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isWithdrawAll() {
        return withdrawAll;
    }

    public String getMessage() {
        return message;
    }

    public ItemStack getItem() {
        return item;
    }
}
