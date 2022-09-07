package pl.kithard.core;

import codecrafter47.bungeetablistplus.api.bukkit.BungeeTabListPlusBukkitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import net.dzikoysk.funnycommands.FunnyCommands;
import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.completers.OnlinePlayersCompleter;
import net.dzikoysk.funnycommands.resources.types.PlayerType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kithard.core.antimacro.AntiMacroCache;
import pl.kithard.core.antimacro.AntiMacroListener;
import pl.kithard.core.antimacro.AntiMacroTask;
import pl.kithard.core.api.database.DatabaseConfig;
import pl.kithard.core.api.database.MongoService;
import pl.kithard.core.automessage.config.AutoMessageConfiguration;
import pl.kithard.core.automessage.task.AutoMessageTask;
import pl.kithard.core.border.command.BorderCommand;
import pl.kithard.core.border.listener.BorderListener;
import pl.kithard.core.configuration.command.ReloadConfigurationCommand;
import pl.kithard.core.database.task.DataSaveTask;
import pl.kithard.core.deposit.DepositItemCache;
import pl.kithard.core.deposit.command.DepositCommand;
import pl.kithard.core.deposit.configuration.DepositConfiguration;
import pl.kithard.core.deposit.task.DepositTask;
import pl.kithard.core.drop.DropItemConfiguration;
import pl.kithard.core.drop.DropItemSerdes;
import pl.kithard.core.drop.command.DropCommand;
import pl.kithard.core.drop.command.TurboDropCommand;
import pl.kithard.core.drop.listener.ItemDropListener;
import pl.kithard.core.drop.special.SpecialDropItemSerdes;
import pl.kithard.core.drop.special.listener.SpecialDropsListener;
import pl.kithard.core.effect.CustomEffectCache;
import pl.kithard.core.effect.command.CustomEffectCommand;
import pl.kithard.core.effect.configuration.CustomEffectConfiguration;
import pl.kithard.core.enchant.*;
import pl.kithard.core.freeze.FreezeCommand;
import pl.kithard.core.freeze.FreezeTask;
import pl.kithard.core.generator.GeneratorCache;
import pl.kithard.core.generator.GeneratorFactory;
import pl.kithard.core.generator.listener.GeneratorListener;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.guild.GuildFactory;
import pl.kithard.core.guild.command.*;
import pl.kithard.core.guild.command.admin.GuildAdminCommand;
import pl.kithard.core.guild.command.admin.GuildAdminGiveItemsCommand;
import pl.kithard.core.guild.command.admin.GuildAdminTeleportCommand;
import pl.kithard.core.guild.command.bind.GuildBind;
import pl.kithard.core.guild.freespace.FreeSpaceCache;
import pl.kithard.core.guild.freespace.command.FreeSpaceCommand;
import pl.kithard.core.guild.freespace.task.FreeSpaceTask;
import pl.kithard.core.guild.listener.GuildChatListener;
import pl.kithard.core.guild.listener.GuildHeartListener;
import pl.kithard.core.guild.listener.GuildTerrainActionsListener;
import pl.kithard.core.guild.listener.GuildTntExplosionListener;
import pl.kithard.core.guild.panel.command.GuildPanelCommand;
import pl.kithard.core.guild.periscope.listener.GuildPeriscopeListener;
import pl.kithard.core.guild.permission.listener.GuildPermissionListener;
import pl.kithard.core.guild.ranking.GuildRankingService;
import pl.kithard.core.guild.regen.RegenCache;
import pl.kithard.core.guild.regen.command.RegenCommand;
import pl.kithard.core.guild.regen.listener.RegenListener;
import pl.kithard.core.guild.task.GuildExpireTask;
import pl.kithard.core.guild.task.GuildHologramTask;
import pl.kithard.core.guild.variables.*;
import pl.kithard.core.itemshop.ItemShopService;
import pl.kithard.core.itemshop.ItemShopServiceCache;
import pl.kithard.core.itemshop.command.ItemShopCommand;
import pl.kithard.core.itemshop.configuration.ItemShopServiceConfiguration;
import pl.kithard.core.kit.KitConfiguration;
import pl.kithard.core.kit.KitSerdes;
import pl.kithard.core.kit.command.KitCommand;
import pl.kithard.core.kit.command.KitManageCommand;
import pl.kithard.core.player.CorePlayerCache;
import pl.kithard.core.player.CorePlayerFactory;
import pl.kithard.core.player.achievement.AchievementCache;
import pl.kithard.core.player.achievement.AchievementCommand;
import pl.kithard.core.player.achievement.AchievementListener;
import pl.kithard.core.player.actionbar.ActionBarNoticeCache;
import pl.kithard.core.player.actionbar.task.ActionBarNoticeShowTask;
import pl.kithard.core.player.backup.PlayerBackupFactory;
import pl.kithard.core.player.backup.PlayerBackupService;
import pl.kithard.core.player.backup.command.PlayerBackupCommand;
import pl.kithard.core.player.backup.task.PlayerBackupTask;
import pl.kithard.core.player.chat.command.ChatManageCommand;
import pl.kithard.core.player.chat.listener.AsyncPlayerChatListener;
import pl.kithard.core.player.combat.listener.BlockCommandsInCombatListener;
import pl.kithard.core.player.combat.listener.PlayerDamageListener;
import pl.kithard.core.player.combat.listener.PlayerDeathListener;
import pl.kithard.core.player.command.*;
import pl.kithard.core.player.command.bind.CorePlayerBind;
import pl.kithard.core.player.enderchest.command.EnderChestCommand;
import pl.kithard.core.player.enderchest.listener.EnderChestListener;
import pl.kithard.core.player.home.command.HomeCommand;
import pl.kithard.core.player.listener.*;
import pl.kithard.core.player.nametag.PlayerNameTagService;
import pl.kithard.core.player.nametag.task.PlayerNameTagRefreshTask;
import pl.kithard.core.player.punishment.PunishmentCache;
import pl.kithard.core.player.punishment.PunishmentFactory;
import pl.kithard.core.player.punishment.command.PunishmentCommand;
import pl.kithard.core.player.punishment.listener.PlayerLoginListener;
import pl.kithard.core.player.ranking.PlayerRankingCommand;
import pl.kithard.core.player.ranking.PlayerRankingService;
import pl.kithard.core.player.settings.command.PlayerSettingsCommand;
import pl.kithard.core.player.task.PlayerSpentTimeTask;
import pl.kithard.core.player.teleport.countdown.PlayerTeleportCountdown;
import pl.kithard.core.player.teleport.listener.PlayerMoveTeleportListener;
import pl.kithard.core.player.variable.*;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.recipe.command.AdminItemsCommand;
import pl.kithard.core.recipe.command.CobbleXCommand;
import pl.kithard.core.recipe.command.CustomRecipeCommand;
import pl.kithard.core.recipe.command.MagicChestGiveCommand;
import pl.kithard.core.recipe.listener.CustomRecipeListener;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.settings.ServerSettingsService;
import pl.kithard.core.settings.command.ServerSettingsCommand;
import pl.kithard.core.settings.listener.ServerSettingsListeners;
import pl.kithard.core.shop.ShopItemCache;
import pl.kithard.core.shop.command.SellCommand;
import pl.kithard.core.shop.command.ShopCommand;
import pl.kithard.core.shop.config.ShopConfiguration;
import pl.kithard.core.task.RankingsRefreshTask;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.adapters.ConfigurationSerializableAdapter;
import pl.kithard.core.util.adapters.InventoryAdapter;
import pl.kithard.core.util.adapters.ItemStackArrayAdapter;
import pl.kithard.core.warp.WarpCache;
import pl.kithard.core.warp.WarpFactory;
import pl.kithard.core.warp.command.WarpCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class CorePlugin extends JavaPlugin {

    private Gson gson;
    private ExecutorService executorService;
    private MongoService mongoService;

    private CorePlayerCache corePlayerCache;
    private CorePlayerFactory corePlayerFactory;
    private PlayerRankingService playerRankingService;
    private PlayerNameTagService playerNameTagService;
    private PlayerBackupService playerBackupService;
    private PlayerBackupFactory playerBackupFactory;

    private GuildCache guildCache;
    private GuildFactory guildFactory;
    private GuildRankingService guildRankingService;
    private RegenCache regenCache;

    private CustomEffectCache customEffectCache;
    private CustomEffectConfiguration customEffectConfiguration;

    private ItemShopServiceCache itemShopServiceCache;
    private ItemShopServiceConfiguration itemShopServiceConfiguration;

    private DropItemConfiguration dropItemConfiguration;

    private AutoMessageConfiguration autoMessageConfiguration;

    private ShopItemCache shopItemCache;
    private ShopConfiguration shopConfiguration;

    private ServerSettings serverSettings;
    private ServerSettingsService serverSettingsService;

    private DepositItemCache depositItemCache;
    private DepositConfiguration depositConfiguration;

    private GeneratorCache generatorCache;
    private GeneratorFactory generatorFactory;

    private WarpCache warpCache;
    private WarpFactory warpFactory;

    private PunishmentCache punishmentCache;
    private PunishmentFactory punishmentFactory;

    private KitConfiguration kitConfiguration;

    private FreeSpaceCache freeSpaceCache;
    private ActionBarNoticeCache actionBarNoticeCache;
    private AchievementCache achievementCache;
    private AntiMacroCache antiMacroCache;
    private CustomEnchantConfiguration customEnchantConfiguration;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                .registerTypeHierarchyAdapter(ItemStack[].class, new ItemStackArrayAdapter())
                .registerTypeHierarchyAdapter(Inventory.class, new InventoryAdapter())
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .serializeNulls()
                .create();

        this.executorService = Executors.newCachedThreadPool();

        this.customEffectConfiguration = new CustomEffectConfiguration(this);
        this.customEffectConfiguration.createConfig();

        this.itemShopServiceConfiguration = new ItemShopServiceConfiguration(this);
        this.itemShopServiceConfiguration.createConfig();

        this.kitConfiguration = ConfigManager.create(KitConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> registry.register(new KitSerdes()));
            it.withBindFile(getDataFolder() + "/kits.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.customEnchantConfiguration = ConfigManager.create(CustomEnchantConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> {
                registry.register(new CustomEnchantWrapperSerdes());
                registry.register(new CustomEnchantSerdes());
            });
            it.withBindFile(getDataFolder() + "/customEnchant.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.dropItemConfiguration = ConfigManager.create(DropItemConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> {
                registry.register(new DropItemSerdes());
                registry.register(new SpecialDropItemSerdes());
            });
            it.withBindFile(getDataFolder() + "/drops.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.autoMessageConfiguration = new AutoMessageConfiguration(this);
        this.autoMessageConfiguration.createConfig();

        this.shopConfiguration = new ShopConfiguration(this);
        this.shopConfiguration.createConfig();

        this.depositConfiguration = new DepositConfiguration(this);
        this.depositConfiguration.createConfig();

        this.depositItemCache = new DepositItemCache(this);
        this.depositItemCache.init();

        this.mongoService = new MongoService(DatabaseConfig.MONGO_URI, this.gson);

        this.serverSettingsService = new ServerSettingsService(this);
        this.serverSettings = this.serverSettingsService.load();

        this.playerRankingService = new PlayerRankingService();

        this.corePlayerCache = new CorePlayerCache();
        this.corePlayerFactory = new CorePlayerFactory(this);
        this.corePlayerFactory.loadAll();

        this.playerNameTagService = new PlayerNameTagService(this);
        this.playerBackupFactory = new PlayerBackupFactory(this);
        this.playerBackupService = new PlayerBackupService(this);

        this.guildCache = new GuildCache(this);
        this.guildFactory = new GuildFactory(this);
        this.guildFactory.loadAll();

        this.regenCache = new RegenCache();

        this.guildRankingService = new GuildRankingService(this);
        this.guildRankingService.sort();

        this.customEffectCache = new CustomEffectCache(this);
        this.customEffectCache.init();

        this.itemShopServiceCache = new ItemShopServiceCache(this);
//        this.itemShopServiceCache.init();

        this.shopItemCache = new ShopItemCache(this);
        this.shopItemCache.init();

        this.generatorCache = new GeneratorCache(this);
        this.generatorFactory = new GeneratorFactory(this);
        this.generatorFactory.loadAll();

        this.warpCache = new WarpCache();
        this.warpFactory = new WarpFactory(this);
        this.warpFactory.loadAll();

        this.punishmentCache = new PunishmentCache();
        this.punishmentFactory = new PunishmentFactory(this);
        this.punishmentFactory.load();

        this.freeSpaceCache = new FreeSpaceCache();
        this.actionBarNoticeCache = new ActionBarNoticeCache();
        this.achievementCache = new AchievementCache();
        this.achievementCache.init();

        this.antiMacroCache = new AntiMacroCache();

        this.initTabList();
        this.initCommands();
        this.initListeners();
        this.initTasks();
        this.initRecipes();
    }

    @Override
    public void onDisable() {

        this.guildFactory.saveAll(false);
        this.corePlayerFactory.saveAll(false);

        System.out.println("Zapisano gildie i userow podczas offania serwera!");

    }

    private void initCommands() {
        FunnyCommands.configuration(() -> this)
                .registerDefaultComponents()
                .type(new PlayerType(super.getServer()))
                .bind(new CorePlayerBind(this.corePlayerCache))
                .bind(new GuildBind(this.corePlayerCache, this))
                .registerComponents(Arrays.asList(
                        new GamemodeCommand(this),
                        new CustomEffectCommand(this),
                        new TpCommand(this),
                        new SpeedCommand(),
                        new SpawnCommand(this),
                        new SetSpawnCommand(),
                        new FlyCommand(this),
                        new InvseeCommand(this),
                        new ClearCommand(this),
                        new ItemShopCommand(this),
                        new TpHereCommand(this),
                        new AdminChatCommand(),
                        new WorkbenchCommand(),
                        new HomeCommand(this),
                        new RepairCommand(),
                        new ListCommand(),
                        new HealCommand(this),
                        new DropCommand(this),
                        new EnderChestCommand(this),
                        new FeedCommand(this),
                        new TeleportRequestCommand(this),
                        new TeleportAcceptCommand(this),
                        new PrivateMessageCommand(this),
                        new IgnoreCommand(this),
                        new PrivateMessageReplyCommand(this),
                        new ShopCommand(this),
                        new TurboDropCommand(this),
                        new SellCommand(this),
                        new PlayerSettingsCommand(this),
                        new BroadcastCommand(),
                        new HelpOpCommand(),
                        new ChatManageCommand(this),
                        new DepositCommand(this),
                        new GuildCommand(),
                        new GuildCreateCommand(this),
                        new VanishCommand(),
                        new GuildHomeTeleportCommand(),
                        new GuildSetHomeCommand(this),
                        new CustomRecipeCommand(),
                        new WarpCommand(this),
                        new PunishmentCommand(this),
                        new GuildInfoCommand(this),
                        new GuildItemsCommand(),
                        new PlayerGuideCommand(),
                        new GuildPanelCommand(this),
                        new GuildAdminCommand(),
                        new GuildAdminGiveItemsCommand(this),
                        new GuildMemberInviteCommand(this),
                        new GuildJoinCommand(this),
                        new GuildKickMemberCommand(this),
                        new ReloadConfigurationCommand(this),
                        new IncognitoCommand(this),
                        new PlayerInfoCommand(this),
                        new GuildAllyInviteCommand(this),
                        new GuildDeputyCommand(this),
                        new GuildLeaveCommand(),
                        new MagicChestGiveCommand(this),
                        new AdminItemsCommand(),
                        new KitCommand(this),
                        new PlayerBackupCommand(this),
                        new CobbleXCommand(),
                        new GuildDeleteCommand(this),
                        new BorderCommand(),
                        new GuildEnlargeCommand(this),
                        new GuildProlongCommand(this),
                        new GuildFriendlyFireCommand(this),
                        new GuildAllyFireCommand(this),
                        new ServerSettingsCommand(this),
                        new GuildAdminTeleportCommand(this),
                        new WhoIsCommand(this),
                        new GuildWarehouseCommand(this),
                        new RankingResetCommand(),
                        new BinCommand(),
                        new GuildOwnerTransferCommand(this),
                        new RegenCommand(this),
                        new FreeSpaceCommand(this),
                        new GuildAlertCommand(this),
                        new AchievementCommand(this),
                        new PlayerRankingCommand(this),
                        new FreezeCommand(this),
                        new DisableProtectionCommand(this),
                        new KitManageCommand(this),
                        new CustomEnchantCommand(this),
                        new GuildMemberNeedHelpCommand(this)
                ))
                .completer("itemShopServices", (context, prefix, limit) -> CommandUtils.collectCompletions(
                        this.itemShopServiceCache.getServices().values(),
                        prefix,
                        limit,
                        ArrayList::new,
                        ItemShopService::getName))
                .completer(new OnlinePlayersCompleter())
                .permissionHandler((message, permission) ->
                        TextUtil.insufficientPermission(message.getCommandSender(), permission))
                .install();

    }

    private void initTasks() {
        new PlayerNameTagRefreshTask(this);
        new DataSaveTask(this);
        new PlayerTeleportCountdown(this);
        new ActionBarNoticeShowTask(this);
        new AutoMessageTask(this);
        new DepositTask(this);
        new RankingsRefreshTask(this);
        new GuildExpireTask(this);
        new GuildHologramTask(this);
        new FreeSpaceTask(this).run();
        new PlayerBackupTask(this);
        new PlayerSpentTimeTask(this);
        new FreezeTask(this);
        new AntiMacroTask(this);
    }

    private void initListeners() {
        new PlayerDataListener(this);
        new PlayerMoveTeleportListener(this);
        new ItemDropListener(this);
        new EnderChestListener(this);
        new AsyncPlayerChatListener(this);
        new CustomRecipeListener(this);
        new GeneratorListener(this);
        new PlayerLoginListener(this);
        new PlayerItemConsumeListener(this);
        new PlayerInteractListener(this);
        new PlayerDeathListener(this);
        new PlayerDamageListener(this);
        new PlayerCommandPreprocessListener(this);
        new SpecialDropsListener(this);
        new GuildTerrainActionsListener(this);
        new PlayerMoveListener(this);
        new BlockCommandsInCombatListener(this);
        new BorderListener(this);
        new GuildPermissionListener(this);
        new PlayerRespawnListener(this);
        new GuildTntExplosionListener(this);
        new GuildHeartListener(this);
        new ServerSettingsListeners(this);
        new GuildChatListener(this);
        new BlockPlaceListener(this);
        new RegenListener(this);
        new GuildPeriscopeListener(this);
        new AchievementListener(this);
        new AntiMacroListener(this);
        new CustomEnchantListener(this);
    }

    private void initRecipes() {
        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.BOY_FARMER.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.OBSIDIAN)
                .setIngredient('b', Material.EMERALD_BLOCK));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.SAND_FARMER.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.SAND)
                .setIngredient('b', Material.EMERALD_BLOCK));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.AIR_FARMER.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.EMERALD_BLOCK)
                .setIngredient('b', Material.DIAMOND_PICKAXE));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.STONE_GENERATOR.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.COBBLESTONE)
                .setIngredient('b', Material.EMERALD_BLOCK));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.COBBLEX.getItem())
                .shape("aaa", "aaa", "aaa")
                .setIngredient('a', Material.COBBLESTONE));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.ENDER_CHEST.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.OBSIDIAN)
                .setIngredient('b', Material.ENDER_PEARL));
    }

    private void initTabList() {
        BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerPointsVariable("player-points", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerKillsVariable("player-kills", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerDeathsVariable("player-deaths", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerAssistsVariable("player-assists", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerKdVariable("player-kd", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerSpentTimeVariable("player-spenttime", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildTagVariable("guild-tag", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildNameVariable("guild-name", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildPointsVariable("guild-points", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildSizeVariable("guild-size", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildKillsVariable("guild-kills", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildDeathsVariable("guild-deaths", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildKdVariable("guild-kd", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildLivesVariable("guild-lives", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new UniquePlayersVariable("players", this));
        BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildsVariable("guilds", this));

        for (int i = 1; i < 17; ++i) {
            BungeeTabListPlusBukkitAPI.registerVariable(this, new PlayerPointsTopVariable("player-points-top" + i, i, this));
        }
        for (int i = 1; i < 17; ++i) {
            BungeeTabListPlusBukkitAPI.registerVariable(this, new GuildPointsTopVariable("guild-points-top" + i, i, this));
        }
    }

    public Gson getGson() {
        return gson;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public MongoService getMongoService() {
        return mongoService;
    }

    public CorePlayerCache getCorePlayerCache() {
        return corePlayerCache;
    }

    public CorePlayerFactory getCorePlayerFactory() {
        return corePlayerFactory;
    }

    public CustomEffectCache getCustomEffectCache() {
        return customEffectCache;
    }

    public CustomEffectConfiguration getCustomEffectConfiguration() {
        return customEffectConfiguration;
    }

    public ItemShopServiceCache getItemShopServiceCache() {
        return itemShopServiceCache;
    }

    public ItemShopServiceConfiguration getItemShopServiceConfiguration() {
        return itemShopServiceConfiguration;
    }

    public DropItemConfiguration getDropItemConfiguration() {
        return dropItemConfiguration;
    }

    public AutoMessageConfiguration getAutoMessageConfiguration() {
        return autoMessageConfiguration;
    }

    public ShopItemCache getShopItemCache() {
        return shopItemCache;
    }

    public ShopConfiguration getShopConfiguration() {
        return shopConfiguration;
    }

    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    public ServerSettingsService getServerSettingsService() {
        return serverSettingsService;
    }

    public DepositItemCache getDepositItemCache() {
        return depositItemCache;
    }

    public DepositConfiguration getDepositConfiguration() {
        return depositConfiguration;
    }

    public GuildCache getGuildCache() {
        return guildCache;
    }

    public GuildFactory getGuildFactory() {
        return guildFactory;
    }

    public RegenCache getRegenCache() {
        return regenCache;
    }

    public GeneratorCache getGeneratorCache() {
        return generatorCache;
    }

    public GeneratorFactory getGeneratorFactory() {
        return generatorFactory;
    }

    public WarpCache getWarpCache() {
        return warpCache;
    }

    public WarpFactory getWarpFactory() {
        return warpFactory;
    }

    public PunishmentCache getPunishmentCache() {
        return punishmentCache;
    }

    public PunishmentFactory getPunishmentFactory() {
        return punishmentFactory;
    }

    public PlayerRankingService getPlayerRankingService() {
        return playerRankingService;
    }

    public GuildRankingService getGuildRankingService() {
        return guildRankingService;
    }

    public PlayerNameTagService getPlayerNameTagService() {
        return playerNameTagService;
    }

    public KitConfiguration getKitConfiguration() {
        return kitConfiguration;
    }

    public PlayerBackupService getPlayerBackupService() {
        return playerBackupService;
    }

    public PlayerBackupFactory getPlayerBackupFactory() {
        return playerBackupFactory;
    }

    public FreeSpaceCache getFreeSpaceCache() {
        return freeSpaceCache;
    }

    public ActionBarNoticeCache getActionBarNoticeCache() {
        return actionBarNoticeCache;
    }

    public AchievementCache getAchievementCache() {
        return achievementCache;
    }

    public AntiMacroCache getAntiMacroCache() {
        return antiMacroCache;
    }

    public CustomEnchantConfiguration getCustomEnchantConfiguration() {
        return customEnchantConfiguration;
    }
}
