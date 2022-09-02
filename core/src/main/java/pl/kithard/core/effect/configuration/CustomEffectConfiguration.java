package pl.kithard.core.effect.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.configuration.CustomConfiguration;

import java.io.File;
import java.io.InputStream;

public class CustomEffectConfiguration extends CustomConfiguration {

    private final CorePlugin plugin;

    public CustomEffectConfiguration(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createConfig() {
        if (this.customConfigFile == null) {
            this.customConfigFile = new File(this.plugin.getDataFolder(), "effects.yml");
        }

        File file = new File(this.plugin.getDataFolder(), "effects.yml");
        if (!file.exists()) {
            this.plugin.saveResource("effects.yml", true);
        }

        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
        InputStream defConfigStream = this.plugin.getResource("effects.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
            this.customConfig.setDefaults(defConfig);
        }
    }
}
