package pl.kithard.proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.json.gson.JsonGsonConfigurer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.kithard.core.api.database.DatabaseConfig;
import pl.kithard.core.api.database.MongoService;
import pl.kithard.proxy.auth.AuthPlayer;
import pl.kithard.proxy.auth.command.AuthCommand;
import pl.kithard.proxy.auth.command.ChangePasswordCommand;
import pl.kithard.proxy.auth.command.LoginCommand;
import pl.kithard.proxy.auth.command.RegisterCommand;
import pl.kithard.proxy.auth.listener.AuthListener;
import pl.kithard.proxy.auth.AuthPlayerCache;
import pl.kithard.proxy.mojang.MojangRequestTask;
import pl.kithard.proxy.motd.MotdConfig;
import pl.kithard.proxy.motd.MotdListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ProxyPlugin extends Plugin {

    public final static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

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

        this.gson = new GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();
        this.mongoService = new MongoService(DatabaseConfig.MONGO_URI, this.gson);

        this.getProxy().getPluginManager().registerCommand(this, new LoginCommand(this));
        this.getProxy().getPluginManager().registerCommand(this, new RegisterCommand(this));
        this.getProxy().getPluginManager().registerCommand(this, new ChangePasswordCommand(this));
        this.getProxy().getPluginManager().registerCommand(this, new AuthCommand(this));
        this.authPlayerCache = new AuthPlayerCache(this);
        this.authPlayerCache.load();
        new MojangRequestTask(this);
        new MotdListener(this);
        new AuthListener(this);
    }

    @Override
    public void onDisable() {
        System.out.println("zapisano wszstkich authplayerow");
        for (AuthPlayer authPlayer : this.authPlayerCache.values()) {
            this.mongoService.save(authPlayer);
        }
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
