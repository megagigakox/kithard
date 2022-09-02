package pl.kithard.core.itemshop.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.configuration.CustomConfiguration;

import java.io.File;
import java.io.InputStream;

public class ItemShopServiceConfiguration extends CustomConfiguration {

    private final CorePlugin plugin;

    public ItemShopServiceConfiguration(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createConfig() {
        if (this.customConfigFile == null) {
            this.customConfigFile = new File(this.plugin.getDataFolder(), "itemshopservices.yml");
        }

        File file = new File(this.plugin.getDataFolder(), "itemshopservices.yml");
        if (!file.exists()) {
            this.plugin.saveResource("itemshopservices.yml", true);
        }

        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
        InputStream defConfigStream = this.plugin.getResource("itemshopservices.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
            this.customConfig.setDefaults(defConfig);
        }
    }
}
