package pl.kithard.core.guild.regen;

import org.bukkit.Material;

import java.util.*;

public class GuildRegenCache {

    private final Set<String> currentlyRegeneratingGuilds = new HashSet<>();
    private final List<GuildRegenBlock> toSave = new ArrayList<>();
    private final List<Material> disabledMaterial = Arrays.asList(
            Material.AIR,
            Material.TNT,
            Material.CHEST
    );

    public Set<String> getCurrentlyRegeneratingGuilds() {
        return currentlyRegeneratingGuilds;
    }

    public List<Material> getDisabledMaterial() {
        return disabledMaterial;
    }

    public List<GuildRegenBlock> getToSave() {
        return toSave;
    }
}
