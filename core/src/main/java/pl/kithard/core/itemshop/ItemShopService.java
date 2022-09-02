package pl.kithard.core.itemshop;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemShopService {

    private final String name;
    private final List<String> commands;
    private final List<String> messages;
    private final List<ItemStack> items = new ArrayList<>();

    public ItemShopService(String name, List<String> commands, List<String> messages) {
        this.name = name;
        this.commands = commands;
        this.messages = messages;
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
}
