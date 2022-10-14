package pl.kithard.core.kit;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class KitConfiguration extends OkaeriConfig {

    private Map<String, Kit> kits = new HashMap<>();

    public KitConfiguration() {
        this.kits.put("GRACZ", new Kit("GRACZ", 20, "kithard.kits.gracz", 30000L, true, new ItemStack(Material.DIRT), Arrays.asList(new ItemStack(Material.DIRT))));
    }

    public Kit findByName(String name) {
        return this.kits.get(name);
    }

    public Map<String, Kit> getKits() {
        return kits;
    }
}