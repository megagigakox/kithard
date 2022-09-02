package pl.kithard.core.kit.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.configuration.CustomConfiguration;

import java.io.File;
import java.io.InputStream;

public class KitConfiguration extends CustomConfiguration {
    private final CorePlugin plugin;

    public KitConfiguration(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createConfig() {
        if (this.customConfigFile == null) {
            this.customConfigFile = new File(this.plugin.getDataFolder(), "kits.yml");
        }

        File file = new File(this.plugin.getDataFolder(), "kits.yml");
        if (!file.exists()) {
            this.plugin.saveResource("kits.yml", true);
        }

        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
        InputStream defConfigStream = this.plugin.getResource("kits.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
            this.customConfig.setDefaults(defConfig);
        }
    }
}
