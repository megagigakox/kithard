package pl.kithard.core.kit.command;

import dev.triumphteam.gui.guis.Gui;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.kit.Kit;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;

@FunnyComponent
public class KitManageCommand {

    private final CorePlugin plugin;

    public KitManageCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "kitmanage",
            permission = "kithard.commands.kitmanage",
            acceptsExceeded = true
    )
    public void handle(Player player) {
        TextUtil.message(player, "&8» &f/kitmanage create (name) (icon) (slot) (time)");
        TextUtil.message(player, "&8» &f/kitmanage edit (name)");
        TextUtil.message(player, "&8» &f/kitmanage delete (name)");
        TextUtil.message(player, "&8» &f/kitmanage status (name)");
        TextUtil.message(player, "&8» &f/kitmanage cooldown (name) (cooldown)");
    }

    @FunnyCommand(
            name = "kitmanage cooldown",
            permission = "kithard.commands.kitmanage",
            acceptsExceeded = true
    )
    public void handleCooldown(Player player, String[] args) {
        if (args.length < 2) {
            return;
        }

        String name = args[0];
        Kit kit = this.plugin.getKitConfiguration().findByName(name);
        if (kit == null) {
            return;
        }
        long time = TimeUtil.timeFromString(args[1]);
        kit.setCooldown(time);
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getKitConfiguration().save());

    }

    @FunnyCommand(
            name = "kitmanage create",
            permission = "kithard.commands.kitmanage",
            acceptsExceeded = true
    )
    public void handleCreate(Player player, String[] args) {
        if (args.length < 4) {
            return;
        }

        String name = args[0];
        Material material = Material.valueOf(args[1]);
        int slot = Integer.parseInt(args[2]);
        long time = TimeUtil.timeFromString(args[3]);

        this.plugin.getKitConfiguration()
                .getKits()
                .put(name, new Kit(
                        name,
                        slot,
                        "kithard.kits." + name,
                        time,
                        true,
                        new ItemStack(material),
                        new ArrayList<>()));
    }

    @FunnyCommand(
            name = "kitmanage edit",
            permission = "kithard.commands.kitmanage",
            acceptsExceeded = true
    )
    public void handleEdit(Player player, String[] args) {
        if (args.length < 1) {
            return;
        }

        String name = args[0];
        Kit kit = this.plugin.getKitConfiguration().findByName(name);
        if (kit == null) {
            return;
        }

        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Edytor zestawu " + kit.getName()))
                .rows(6)
                .create();

        for (ItemStack itemStack : kit.getItems()) {
            gui.addItem(ItemStackBuilder.of(itemStack).asGuiItem());
        }

        gui.setCloseGuiAction(inventoryCloseEvent -> {

            kit.getItems().clear();
            for (ItemStack content : inventoryCloseEvent.getInventory().getContents()) {
                if (content == null) {
                    continue;
                }
                kit.getItems().add(content);
            }

            this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getKitConfiguration().save());

        });

        gui.open(player);
    }


    @FunnyCommand(
            name = "kitmanage delete",
            permission = "kithard.commands.kitmanage",
            acceptsExceeded = true
    )
    public void handleDelete(Player player, String[] args) {
        if (args.length < 1) {
            return;
        }

        String name = args[0];
        Kit kit = this.plugin.getKitConfiguration().findByName(name);
        if (kit == null) {
            return;
        }

        for (CorePlayer corePlayer : this.plugin.getCorePlayerCache().getValues()) {
            if (corePlayer.getCooldown().getKitCooldown(kit.getName()) != 0) {
                corePlayer.getCooldown().getKitCooldowns().remove(kit.getName());
                corePlayer.setNeedSave(true);
            }
        }

        this.plugin.getKitConfiguration().getKits().remove(kit.getName());
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getKitConfiguration().save());
        TextUtil.message(player, "Usunieto kit " + kit.getName());
    }

    @FunnyCommand(
            name = "kitmanage status",
            permission = "kithard.commands.kitmanage",
            acceptsExceeded = true
    )
    public void handleStatus(Player player, String[] args) {
        if (args.length < 1) {
            return;
        }

        String name = args[0];
        Kit kit = this.plugin.getKitConfiguration().findByName(name);
        if (kit == null) {
            return;
        }

        kit.setEnable(!kit.isEnable());
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getKitConfiguration().save());

    }
}
