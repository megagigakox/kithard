package pl.kithard.core.boss;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class BossCommand {

    private final CorePlugin plugin;

    public BossCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "spawnboss",
            permission = "kithard.commands.spawnboss",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/spawnboss (hp)");
            return;
        }
        if (this.plugin.getBossService().getBoss() == null) {
            this.plugin.getBossService().createBoss(player.getLocation(), Integer.parseInt(args[0]));
        }
        else {
            TextUtil.message(player, "boss is not null");
        }
    }

    @FunnyCommand(
            name = "despawnboss",
            permission = "kithard.commands.spawnboss",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handleDespawn(Player player) {
        this.plugin.getBossService().setBoss(null);
        for (Entity entity : this.plugin.getServer().getWorld("world").getEntities()) {
            if (entity instanceof IronGolem) {
                ((IronGolem) entity).setHealth(0);
                entity.remove();
            }
        }

        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie zdespawniono bossa!");
    }

}
