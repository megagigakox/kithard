package pl.kithard.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class CustomConfiguration {

    protected File customConfigFile;
    protected FileConfiguration customConfig;

    public abstract void createConfig();

    public FileConfiguration getCustomConfig() {
        if (this.customConfig == null) {
            createConfig();
        }
        return this.customConfig;
    }

    public void save() {
        try {
            this.getCustomConfig().save(customConfigFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
