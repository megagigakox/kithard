package pl.kithard.core.settings;

import pl.kithard.core.CorePlugin;

public class ServerSettingsService {

    private final CorePlugin plugin;

    public ServerSettingsService(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void save(ServerSettings serverSettings) {
//        this.plugin.getMongoService().save(serverSettings);
    }

    public ServerSettings load() {
//        ServerSettings serverSettings = this.plugin.getMongoService()
//                .load("server_settings", ServerSettings.class);
//
//        if (serverSettings == null) {
//            serverSettings = new ServerSettings();
//        }

        return new ServerSettings();
    }

}
