package pl.kithard.core.itemshop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemShopService {

    private final String name;
    private final boolean amountable;
    private final List<String> commands;
    private final List<String> messages;
    private final List<ItemStack> items;

    public ItemShopService(String name, boolean amountable, List<String> commands, List<String> messages, List<ItemStack> items) {
        this.name = name;
        this.amountable = amountable;
        this.commands = commands;
        this.messages = messages;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<String> getCommands() {
        return commands;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public boolean isAmountable() {
        return amountable;
    }
}
