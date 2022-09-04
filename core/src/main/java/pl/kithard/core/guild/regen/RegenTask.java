package pl.kithard.core.guild.regen;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RegenTask extends BukkitRunnable {

    private final CorePlugin plugin;
    private final Set<RegenBlock> regenBlocks;
    private final UUID player;
    private final Guild guild;
    private final int maxSize;
    private int y;

    public RegenTask(CorePlugin plugin, Set<RegenBlock> regenBlocks, UUID player, Guild guild) {
        this.plugin = plugin;
        this.regenBlocks = regenBlocks;
        this.player = player;
        this.guild = guild;
        this.maxSize = this.regenBlocks.size();
        this.runTaskTimer(plugin, 0L, 10L);
    }

    @Override
    public void run() {
        if (regenBlocks.isEmpty()) {
            this.plugin.getRegenCache()
                    .getCurrentlyRegeneratingGuilds()
                    .remove(guild.getTag());
            this.plugin.getActionBarNoticeCache().remove(player, ActionBarNoticeType.GUILD_TERRAIN_REGEN);
            super.cancel();
            return;
        }

        long seconds = this.regenBlocks.size() / 2;
        this.plugin.getActionBarNoticeCache().add(
                player,
                ActionBarNotice.builder()
                        .type(ActionBarNoticeType.GUILD_TERRAIN_REGEN)
                        .text(
                                "&aRegeneracja terenu gildii: &8(" +
                                TextUtil.progressBar(maxSize - this.regenBlocks.size(), maxSize, 15, '▌', ChatColor.GREEN, ChatColor.RED)
                                + "&8) &f" + TimeUtil.formatTimeMillis(TimeUnit.SECONDS.toMillis(seconds))
                        )
                        .build()
        );


        if (getRegenBlocks(y).isEmpty()) {
            y++;
            return;
        }

        RegenBlock regenBlock = get(y);
        Location location = regenBlock.getLocation();
        location.getBlock().setType(regenBlock.getMaterial());
        location.getBlock().setData(regenBlock.getData());
        this.regenBlocks.remove(regenBlock);

    }

    public RegenBlock get(int y) {
        for (RegenBlock regenBlock : getRegenBlocks(y)) {
            return regenBlock;
        }
        return null;
    }

    public List<RegenBlock> getRegenBlocks(int y) {
        List<RegenBlock> regenBlocks = new ArrayList<>();
        for (RegenBlock regenBlock : this.regenBlocks) {
            if (regenBlock.getLocation().getY() == y) {
                regenBlocks.add(regenBlock);
            }
        }
        return regenBlocks;
    }

}
