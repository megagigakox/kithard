package pl.kithard.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CoreConstants {

    public final static int MIN_DISTANCE_FROM_SPAWN = 250;
    public final static int MIN_DISTANCE_FROM_OTHER_GUILD = 45 * 2 + 50;
    public final static int SPAWN_AREA_DISTANCE = 75;

    public final static List<ItemStack> PLAYER_GUILD_ITEMS = Arrays.asList(
            new ItemStack(Material.SLIME_BLOCK,24),
            new ItemStack(Material.NOTE_BLOCK,48),
            new ItemStack(Material.RAILS,64),
            new ItemStack(Material.GOLD_BLOCK,64),
            new ItemStack(Material.ENCHANTMENT_TABLE,48),
            new ItemStack(Material.ANVIL,48),
            new ItemStack(Material.TNT,48),
            new ItemStack(Material.BOOKSHELF,32),
            new ItemStack(Material.REDSTONE_BLOCK,64)

    );

    public final static List<ItemStack> PREMIUM_GUILD_ITEMS = Arrays.asList(
            new ItemStack(Material.SLIME_BLOCK,16),
            new ItemStack(Material.NOTE_BLOCK,32),
            new ItemStack(Material.RAILS,32),
            new ItemStack(Material.GOLD_BLOCK,32),
            new ItemStack(Material.ENCHANTMENT_TABLE,24),
            new ItemStack(Material.ANVIL,24),
            new ItemStack(Material.TNT,24),
            new ItemStack(Material.BOOKSHELF,24),
            new ItemStack(Material.REDSTONE_BLOCK,32)
    );

    public static final File CENTER_SCHEMATIC =
            new File(
                    System.getProperty("user.dir")
                            + File.separator
                            + "plugins"
                            + File.separator
                            + "/core/center.schematic"
            );
}
