package pl.kithard.core.drop.config;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.configuration.CustomConfiguration;

import java.io.File;
import java.io.InputStream;

public class DropItemConfiguration extends CustomConfiguration {

    private final CorePlugin plugin;

    public DropItemConfiguration(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createConfig() {
        if (this.customConfigFile == null) {
            this.customConfigFile = new File(this.plugin.getDataFolder(), "drops.yml");
        }

        File file = new File(this.plugin.getDataFolder(), "drops.yml");
        if (!file.exists()) {
            this.plugin.saveResource("drops.yml", true);
        }

        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
        InputStream defConfigStream = this.plugin.getResource("drops.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
            this.customConfig.setDefaults(defConfig);
        }
    }
}
