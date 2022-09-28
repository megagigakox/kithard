package pl.kithard.proxy;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.json.gson.JsonGsonConfigurer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.proxy.auth.AuthPlayerCache;
import pl.kithard.proxy.auth.AuthPlayerRepository;
import pl.kithard.proxy.auth.AuthPlayerSaveTask;
import pl.kithard.proxy.auth.command.AuthCommand;
import pl.kithard.proxy.auth.command.ChangePasswordCommand;
import pl.kithard.proxy.auth.command.LoginCommand;
import pl.kithard.proxy.auth.command.RegisterCommand;
import pl.kithard.proxy.auth.listener.AuthListener;
import pl.kithard.proxy.mojang.MojangRequestTask;
import pl.kithard.proxy.motd.MotdConfig;
import pl.kithard.proxy.motd.MotdListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ProxyPlugin extends Plugin {

    public final static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private DatabaseService databaseService;

    private AuthPlayerCache authPlayerCache;
    private AuthPlayerRepository authPlayerRepository;

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

        this.databaseService = new DatabaseService("mysql.titanaxe.com",3306, "srv235179", "srv235179", "CbccNpZ8");
        this.getProxy().getPluginManager().registerCommand(this, new LoginCommand(this));
        this.getProxy().getPluginManager().registerCommand(this, new RegisterCommand(this));
        this.getProxy().getPluginManager().registerCommand(this, new ChangePasswordCommand(this));
        this.getProxy().getPluginManager().registerCommand(this, new AuthCommand(this));
        this.authPlayerCache = new AuthPlayerCache();
        this.authPlayerRepository = new AuthPlayerRepository(this.databaseService);
        this.authPlayerRepository.prepareTable();
        this.authPlayerRepository.loadAll().forEach(authPlayer -> this.authPlayerCache.add(authPlayer));
        new MojangRequestTask(this);
        new AuthPlayerSaveTask(this);
        new MotdListener(this);
        new AuthListener(this);
    }

    @Override
    public void onDisable() {
        this.authPlayerRepository.updateAll(this.authPlayerCache.values());
        this.databaseService.shutdown();
    }

    public MotdConfig getMotdConfig() {
        return motdConfig;
    }

    public AuthPlayerCache getAuthPlayerCache() {
        return authPlayerCache;
    }

    public AuthPlayerRepository getAuthPlayerRepository() {
        return authPlayerRepository;
    }
}
