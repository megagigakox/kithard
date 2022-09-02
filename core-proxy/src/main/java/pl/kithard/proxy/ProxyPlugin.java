package pl.kithard.proxy;

import com.google.gson.Gson;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.json.gson.JsonGsonConfigurer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.kithard.core.api.MongoService;
import pl.kithard.proxy.auth.AuthListener;
import pl.kithard.proxy.auth.AuthPlayerCache;
import pl.kithard.proxy.motd.MotdConfig;
import pl.kithard.proxy.motd.MotdListener;

public class ProxyPlugin extends Plugin {

    private Gson gson;
    private MongoService mongoService;

    private AuthPlayerCache authPlayerCache;

    private MotdConfig motdConfig;

    @Override
    public void onEnable() {
        getProxy().registerChannel("BungeeCord");
        this.motdConfig =  ConfigManager.create(MotdConfig.class, (it) -> {
            it.withConfigurer(new JsonGsonConfigurer());
            it.withBindFile(getDataFolder() + "/motd.json");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.authPlayerCache = new AuthPlayerCache();
        new MotdListener(this);
        new AuthListener(this);
    }

    public MotdConfig getMotdConfig() {
        return motdConfig;
    }

    public Gson getGson() {
        return gson;
    }

    public MongoService getMongoService() {
        return mongoService;
    }

    public AuthPlayerCache getAuthPlayerCache() {
        return authPlayerCache;
    }
}
