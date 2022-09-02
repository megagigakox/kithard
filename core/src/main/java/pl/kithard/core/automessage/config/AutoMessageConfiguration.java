package pl.kithard.core.automessage.config;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.configuration.CustomConfiguration;

import java.io.File;
import java.io.InputStream;

public class AutoMessageConfiguration extends CustomConfiguration {

    private final CorePlugin plugin;

    public AutoMessageConfiguration(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createConfig() {
        if (this.customConfigFile == null) {
            this.customConfigFile = new File(this.plugin.getDataFolder(), "automessages.yml");
        }

        File file = new File(this.plugin.getDataFolder(), "automessages.yml");
        if (!file.exists()) {
            this.plugin.saveResource("automessages.yml", true);
        }

        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
        InputStream defConfigStream = this.plugin.getResource("automessages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
            this.customConfig.setDefaults(defConfig);
        }
    }
}
