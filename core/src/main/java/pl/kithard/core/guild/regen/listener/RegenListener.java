package pl.kithard.core.guild.regen.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.regen.RegenBlock;

public class RegenListener implements Listener {

    private final CorePlugin plugin;

    public RegenListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getLocation());
        if (guild == null) {
            return;
        }

        for (Block block : event.blockList()) {
            if (this.plugin.getRegenCache().getDisabledMaterial().contains(block.getType())) {
                continue;
            }

            guild.getRegenBlocks().add(
                    new RegenBlock(block.getLocation(), block.getType(), block.getData())
            );
        }

        guild.setNeedSave(true);
    }

}
