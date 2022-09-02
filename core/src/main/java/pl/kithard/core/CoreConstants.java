package pl.kithard.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CoreConstants {

    public final static int MIN_DISTANCE_FROM_SPAWN = 250;
    public final static int MIN_DISTANCE_FROM_OTHER_GUILD = 40 * 2 + 50;
    public final static int SPAWN_AREA_DISTANCE = 60;

    public final static List<ItemStack> PLAYER_GUILD_ITEMS = Arrays.asList(
            new ItemStack(Material.EMERALD_BLOCK,64),
            new ItemStack(Material.COAL_BLOCK,64),
            new ItemStack(Material.GOLD_BLOCK,64),
            new ItemStack(Material.TNT,64),
            new ItemStack(Material.SLIME_BLOCK,64),
            new ItemStack(Material.ANVIL,64),
            new ItemStack(Material.PISTON_BASE,32),
            new ItemStack(Material.CHEST,64),
            new ItemStack(Material.ENCHANTMENT_TABLE,32)

    );

    public final static List<ItemStack> PREMIUM_GUILD_ITEMS = Arrays.asList(
            new ItemStack(Material.EMERALD_BLOCK,32),
            new ItemStack(Material.COAL_BLOCK,32),
            new ItemStack(Material.GOLD_BLOCK,32),
            new ItemStack(Material.TNT,32),
            new ItemStack(Material.SLIME_BLOCK,32),
            new ItemStack(Material.ANVIL,32),
            new ItemStack(Material.PISTON_BASE,16),
            new ItemStack(Material.CHEST,32),
            new ItemStack(Material.ENCHANTMENT_TABLE,16)
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
