package pl.kithard.core.guild.regen;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuildRegenCache {

    private final Set<String> currentlyRegeneratingGuilds = new HashSet<>();
    private final List<Material> disabledMaterial = Arrays.asList(
            Material.TNT,
            Material.CHEST
    );

    public Set<String> getCurrentlyRegeneratingGuilds() {
        return currentlyRegeneratingGuilds;
    }

    public List<Material> getDisabledMaterial() {
        return disabledMaterial;
    }
}
