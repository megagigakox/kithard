package pl.kithard.core.deposit.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.configuration.CustomConfiguration;

import java.io.File;
import java.io.InputStream;

public class DepositConfiguration extends CustomConfiguration {

    private final CorePlugin plugin;

    public DepositConfiguration(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createConfig() {
        if (this.customConfigFile == null) {
            this.customConfigFile = new File(this.plugin.getDataFolder(), "deposit.yml");
        }

        File file = new File(this.plugin.getDataFolder(), "deposit.yml");
        if (!file.exists()) {
            this.plugin.saveResource("deposit.yml", true);
        }

        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
        InputStream defConfigStream = this.plugin.getResource("deposit.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
            this.customConfig.setDefaults(defConfig);
        }
    }
}
