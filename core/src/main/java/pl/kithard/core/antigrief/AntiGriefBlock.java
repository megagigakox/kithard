package pl.kithard.core.antigrief;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.concurrent.TimeUnit;

public class AntiGriefBlock {

    private final Block block;
    private final long timeStamp = System.currentTimeMillis();

    public AntiGriefBlock(Block block) {
        this.block = block;
    }

    public void clear() {
        this.block.setType(Material.AIR);
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - this.timeStamp > (block.getWorld().getName().equals("world") ? TimeUnit.MINUTES.toMillis(30) : TimeUnit.SECONDS.toMillis(10));
    }
}
