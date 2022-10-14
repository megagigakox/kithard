package pl.kithard.core.settings;

import eu.okaeri.configs.OkaeriConfig;

public class ServerSettingsConfiguration extends OkaeriConfig {

    private ServerSettings serverSettings = new ServerSettings();

    public ServerSettings getServerSettings() {
        return serverSettings;
    }

}
