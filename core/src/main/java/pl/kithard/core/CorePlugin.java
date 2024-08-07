package pl.kithard.core;

import codecrafter47.bungeetablistplus.api.bukkit.BungeeTabListPlusBukkitAPI;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import net.dzikoysk.funnycommands.FunnyCommands;
import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.completers.OnlinePlayersCompleter;
import net.dzikoysk.funnycommands.resources.types.PlayerType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kithard.core.abyss.AbbysTask;
import pl.kithard.core.antigrief.AntiGriefCache;
import pl.kithard.core.antigrief.AntiGriefListener;
import pl.kithard.core.antigrief.AntiGriefTask;
import pl.kithard.core.antimacro.AntiMacroCache;
import pl.kithard.core.antimacro.AntiMacroListener;
import pl.kithard.core.antimacro.AntiMacroTask;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.api.reward.RewardRepository;
import pl.kithard.core.automessage.config.AutoMessageConfiguration;
import pl.kithard.core.automessage.task.AutoMessageTask;
import pl.kithard.core.border.command.BorderCommand;
import pl.kithard.core.border.listener.BorderListener;
import pl.kithard.core.boss.BossCommand;
import pl.kithard.core.boss.BossListener;
import pl.kithard.core.boss.BossService;
import pl.kithard.core.boss.BossTask;
import pl.kithard.core.configuration.command.ReloadConfigurationCommand;
import pl.kithard.core.database.task.DataSaveTask;
import pl.kithard.core.deposit.DepositItemConfiguration;
import pl.kithard.core.deposit.DepositItemSerdes;
import pl.kithard.core.deposit.command.DepositCommand;
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
import pl.kithard.core.freeze.FreezeListener;
import pl.kithard.core.freeze.FreezeTask;
import pl.kithard.core.generator.GeneratorCache;
import pl.kithard.core.generator.GeneratorFactory;
import pl.kithard.core.generator.GeneratorRepository;
import pl.kithard.core.generator.listener.GeneratorListener;
import pl.kithard.core.grouptp.GroupTeleportCache;
import pl.kithard.core.grouptp.GroupTeleportCommand;
import pl.kithard.core.grouptp.GroupTeleportListener;
import pl.kithard.core.grouptp.GroupTeleportTask;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.guild.GuildCreator;
import pl.kithard.core.guild.GuildRepository;
import pl.kithard.core.guild.bucket.BucketInteractionListener;
import pl.kithard.core.guild.bucket.BucketWaterDeleteTask;
import pl.kithard.core.guild.command.*;
import pl.kithard.core.guild.command.admin.GuildAdminCommand;
import pl.kithard.core.guild.command.bind.GuildBind;
import pl.kithard.core.guild.freespace.FreeSpaceCache;
import pl.kithard.core.guild.freespace.command.FreeSpaceCommand;
import pl.kithard.core.guild.freespace.task.FreeSpaceTask;
import pl.kithard.core.guild.listener.GuildChatListener;
import pl.kithard.core.guild.listener.GuildHeartListener;
import pl.kithard.core.guild.listener.GuildTerrainActionsListener;
import pl.kithard.core.guild.listener.GuildTntExplosionListener;
import pl.kithard.core.guild.logblock.GuildLogBlockListener;
import pl.kithard.core.guild.panel.command.GuildPanelCommand;
import pl.kithard.core.guild.periscope.listener.GuildPeriscopeListener;
import pl.kithard.core.guild.permission.listener.GuildPermissionListener;
import pl.kithard.core.guild.ranking.GuildRankingService;
import pl.kithard.core.guild.regen.GuildRegenBlockSaveTask;
import pl.kithard.core.guild.regen.GuildRegenCache;
import pl.kithard.core.guild.regen.command.GuildRegenCommand;
import pl.kithard.core.guild.regen.listener.GuildRegenListener;
import pl.kithard.core.guild.report.GuildAdminReportCommand;
import pl.kithard.core.guild.report.GuildReportCache;
import pl.kithard.core.guild.report.GuildReportCommand;
import pl.kithard.core.guild.task.GuildExpireTask;
import pl.kithard.core.guild.task.GuildHologramTask;
import pl.kithard.core.guild.task.GuildShadowBlockProtectionTask;
import pl.kithard.core.guild.variables.*;
import pl.kithard.core.guild.warehouse.WarehouseListener;
import pl.kithard.core.itemshop.ItemShopService;
import pl.kithard.core.itemshop.ItemShopServiceConfiguration;
import pl.kithard.core.itemshop.ItemShopServiceExecutor;
import pl.kithard.core.itemshop.ItemShopServiceSerdes;
import pl.kithard.core.itemshop.command.ItemShopCommand;
import pl.kithard.core.kit.KitConfiguration;
import pl.kithard.core.kit.KitSerdes;
import pl.kithard.core.kit.command.KitCommand;
import pl.kithard.core.kit.command.KitManageCommand;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.CorePlayerCache;
import pl.kithard.core.player.CorePlayerRepository;
import pl.kithard.core.player.achievement.AchievementCache;
import pl.kithard.core.player.achievement.AchievementCommand;
import pl.kithard.core.player.achievement.AchievementListener;
import pl.kithard.core.player.actionbar.ActionBarNoticeCache;
import pl.kithard.core.player.actionbar.task.ActionBarNoticeShowTask;
import pl.kithard.core.player.backup.PlayerBackupFactory;
import pl.kithard.core.player.backup.PlayerBackupRepository;
import pl.kithard.core.player.backup.PlayerBackupService;
import pl.kithard.core.player.backup.command.PlayerBackupCommand;
import pl.kithard.core.player.chat.command.ChatManageCommand;
import pl.kithard.core.player.chat.listener.AsyncPlayerChatListener;
import pl.kithard.core.player.combat.listener.BlockCombatInteractionsListener;
import pl.kithard.core.player.combat.listener.BlockCombatPlaceListener;
import pl.kithard.core.player.combat.listener.PlayerDamageListener;
import pl.kithard.core.player.combat.listener.PlayerDeathListener;
import pl.kithard.core.player.command.*;
import pl.kithard.core.player.command.bind.CorePlayerBind;
import pl.kithard.core.player.enderchest.command.EnderChestCommand;
import pl.kithard.core.player.enderchest.listener.EnderChestListener;
import pl.kithard.core.player.home.command.PlayerHomeCommand;
import pl.kithard.core.player.incognito.PlayerIncognitoSerivce;
import pl.kithard.core.player.incognito.command.IncognitoCommand;
import pl.kithard.core.player.listener.*;
import pl.kithard.core.player.nametag.PlayerNameTagService;
import pl.kithard.core.player.nametag.task.PlayerNameTagRefreshTask;
import pl.kithard.core.player.punishment.PunishmentCache;
import pl.kithard.core.player.punishment.PunishmentFactory;
import pl.kithard.core.player.punishment.PunishmentRepository;
import pl.kithard.core.player.punishment.command.PunishmentCommand;
import pl.kithard.core.player.punishment.listener.PlayerLoginListener;
import pl.kithard.core.player.ranking.PlayerRankingCommand;
import pl.kithard.core.player.ranking.PlayerRankingService;
import pl.kithard.core.player.reward.RewardCommand;
import pl.kithard.core.player.reward.RewardTask;
import pl.kithard.core.player.settings.command.PlayerSettingsCommand;
import pl.kithard.core.player.task.PlayerSpentTimeTask;
import pl.kithard.core.player.teleport.countdown.PlayerTeleportCountdown;
import pl.kithard.core.player.variable.*;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.recipe.command.AdminItemsCommand;
import pl.kithard.core.recipe.command.CobbleXCommand;
import pl.kithard.core.recipe.command.CustomRecipeCommand;
import pl.kithard.core.recipe.command.MagicChestGiveCommand;
import pl.kithard.core.recipe.listener.CustomRecipeListener;
import pl.kithard.core.safe.SafeCache;
import pl.kithard.core.safe.SafeCommand;
import pl.kithard.core.safe.SafeListener;
import pl.kithard.core.safe.SafeRepository;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.settings.ServerSettingsConfiguration;
import pl.kithard.core.settings.ServerSettingsSerdes;
import pl.kithard.core.settings.command.ServerSettingsCommand;
import pl.kithard.core.settings.listener.ServerSettingsListeners;
import pl.kithard.core.shop.ShopConfiguration;
import pl.kithard.core.shop.command.AdminShopCommand;
import pl.kithard.core.shop.command.SellCommand;
import pl.kithard.core.shop.command.ShopCommand;
import pl.kithard.core.shop.item.ShopItemSerdes;
import pl.kithard.core.shop.item.ShopVillagerItemSerdes;
import pl.kithard.core.shop.item.ShopVillagerSerdes;
import pl.kithard.core.spawn.SpawnProtectionListener;
import pl.kithard.core.task.RankingsRefreshTask;
import pl.kithard.core.trade.TradeCache;
import pl.kithard.core.trade.TradeListener;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.warp.WarpCache;
import pl.kithard.core.warp.WarpFactory;
import pl.kithard.core.warp.WarpSerdes;
import pl.kithard.core.warp.command.WarpCommand;

import java.util.ArrayList;
import java.util.Arrays;

public final class CorePlugin extends JavaPlugin {

    private DatabaseService databaseService;

    private CorePlayerCache corePlayerCache;
    private CorePlayerRepository corePlayerRepository;
    private PlayerRankingService playerRankingService;
    private PlayerNameTagService playerNameTagService;
    private PlayerBackupService playerBackupService;
    private PlayerBackupFactory playerBackupFactory;
    private PlayerBackupRepository playerBackupRepository;
    private PlayerIncognitoSerivce playerIncognitoSerivce;
    private RewardRepository rewardRepository;

    private GuildCache guildCache;
    private GuildRepository guildRepository;
    private GuildCreator guildFactory;
    private GuildRankingService guildRankingService;
    private GuildRegenCache regenCache;
    private GuildReportCache guildReportCache;

    private CustomEffectCache customEffectCache;
    private CustomEffectConfiguration customEffectConfiguration;

    private ItemShopServiceConfiguration itemShopServiceConfiguration;
    private ItemShopServiceExecutor itemShopServiceExecutor;

    private DropItemConfiguration dropItemConfiguration;

    private AutoMessageConfiguration autoMessageConfiguration;

    private ShopConfiguration shopConfiguration;

    private ServerSettings serverSettings;
    private ServerSettingsConfiguration serverSettingsConfiguration;

    private DepositItemConfiguration depositItemConfiguration;

    private GeneratorCache generatorCache;
    private GeneratorFactory generatorFactory;
    private GeneratorRepository generatorRepository;

    private WarpCache warpCache;
    private WarpFactory warpFactory;

    private PunishmentCache punishmentCache;
    private PunishmentFactory punishmentFactory;
    private PunishmentRepository punishmentRepository;

    private KitConfiguration kitConfiguration;

    private FreeSpaceCache freeSpaceCache;
    private ActionBarNoticeCache actionBarNoticeCache;
    private AchievementCache achievementCache;
    private AntiMacroCache antiMacroCache;
    private CustomEnchantConfiguration customEnchantConfiguration;

    private SafeCache safeCache;
    private SafeRepository safeRepository;

    private BossService bossService;
    private TradeCache tradeCache;
    private AntiGriefCache antiGriefCache;
    private GroupTeleportCache groupTeleportCache;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.databaseService = new DatabaseService("mysql.titanaxe.com",3306, "srv235179", "srv235179", "CbccNpZ8");

        this.customEffectConfiguration = new CustomEffectConfiguration(this);
        this.customEffectConfiguration.createConfig();

        this.itemShopServiceConfiguration = ConfigManager.create(ItemShopServiceConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> registry.register(new ItemShopServiceSerdes()));
            it.withBindFile(getDataFolder() + "/itemShopServices.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        this.itemShopServiceExecutor = new ItemShopServiceExecutor(this);

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

        this.depositItemConfiguration = ConfigManager.create(DepositItemConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> registry.register(new DepositItemSerdes()));
            it.withBindFile(getDataFolder() + "/deposit.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.shopConfiguration = ConfigManager.create(ShopConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> {
                registry.register(new ShopItemSerdes());
                registry.register(new ShopVillagerItemSerdes());
                registry.register(new ShopVillagerSerdes());
            });
            it.withBindFile(getDataFolder() + "/shop.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.autoMessageConfiguration = new AutoMessageConfiguration(this);
        this.autoMessageConfiguration.createConfig();

        this.serverSettingsConfiguration = ConfigManager.create(ServerSettingsConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> registry.register(new ServerSettingsSerdes()));
            it.withBindFile(getDataFolder() + "/serverSettings.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        this.serverSettings = serverSettingsConfiguration.getServerSettings();

        this.playerRankingService = new PlayerRankingService();
        this.corePlayerRepository = new CorePlayerRepository(this.databaseService);
        this.corePlayerRepository.prepareTable();
        this.corePlayerCache = new CorePlayerCache();
        this.corePlayerRepository.loadAll().forEach(corePlayer -> {
            this.corePlayerCache.add(corePlayer);
            this.playerRankingService.add(corePlayer);
        });
        this.playerIncognitoSerivce = new PlayerIncognitoSerivce(this);

        this.safeCache = new SafeCache();
        this.safeRepository = new SafeRepository(databaseService);
        this.safeRepository.prepareTable();
        this.safeRepository.loadAll().forEach(safe -> {
            this.safeCache.add(safe);
        });

        this.playerNameTagService = new PlayerNameTagService(this);
        this.playerBackupRepository = new PlayerBackupRepository(this.databaseService);
        this.playerBackupRepository.prepareTable();
        this.playerBackupFactory = new PlayerBackupFactory(this);
        this.playerBackupService = new PlayerBackupService(this);

        this.guildCache = new GuildCache(this);
        this.guildRepository = new GuildRepository(getLogger(), this.databaseService, guildCache);
        this.guildRepository.prepareTable();
        this.guildFactory = new GuildCreator(this);
        this.guildFactory.loadAll();
        this.guildReportCache = new GuildReportCache();

        this.regenCache = new GuildRegenCache();

        this.guildRankingService = new GuildRankingService(this);
        this.guildRankingService.sort();

        this.customEffectCache = new CustomEffectCache(this);
        this.customEffectCache.init();

        this.generatorCache = new GeneratorCache(this);
        this.generatorRepository = new GeneratorRepository(this.databaseService);
        this.generatorRepository.prepareTable();
        this.generatorFactory = new GeneratorFactory(this);
        this.generatorFactory.loadAll();

        this.warpCache = ConfigManager.create(WarpCache.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withSerdesPack(registry -> {
                registry.register(new WarpSerdes());
            });
            it.withBindFile(getDataFolder() + "/warps.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        this.warpFactory = new WarpFactory(this);

        this.punishmentRepository = new PunishmentRepository(this.databaseService);
        this.punishmentRepository.prepareTables();
        this.punishmentCache = new PunishmentCache();
        this.punishmentFactory = new PunishmentFactory(this);
        this.punishmentFactory.load();

        this.freeSpaceCache = new FreeSpaceCache();
        this.actionBarNoticeCache = new ActionBarNoticeCache();
        this.achievementCache = new AchievementCache();
        this.achievementCache.init();

        this.rewardRepository = new RewardRepository(this.databaseService);
        this.antiMacroCache = new AntiMacroCache();
        this.bossService = new BossService();
        this.tradeCache = new TradeCache(this);
        this.antiGriefCache = new AntiGriefCache();
        this.groupTeleportCache = new GroupTeleportCache();
        this.groupTeleportCache.prepareWorld();

        this.initTabList();
        this.initCommands();
        this.initListeners();
        this.initTasks();
        this.initRecipes();
    }

    @Override
    public void onDisable() {

        for (Entity entity : this.getServer().getWorld("world").getEntities()) {
            if (entity instanceof Zombie) {
                entity.remove();
            }
        }

        for (Player player : getServer().getOnlinePlayers()) {

            CorePlayer corePlayer = this.corePlayerCache.findByPlayer(player);
            corePlayer.getCombat().setLastAttackTime(0L);
            corePlayer.getCombat().setLastAssistTime(0L);
            corePlayer.getCombat().setLastAssistPlayer(null);
            corePlayer.getCombat().setLastAttackPlayer(null);

            if (player.getWorld().getName().equals("gtp")) {
                player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
        }

        this.guildRepository.updateAll(this.guildCache.getValues());
        this.corePlayerRepository.updateAll(this.corePlayerCache.getValues());
        this.safeRepository.updateAll(this.safeCache.values());

        this.databaseService.shutdown();
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
                        new PlayerHomeCommand(this),
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
                        new GuildAdminCommand(this),
                        new GuildMemberInviteCommand(this),
                        new GuildJoinCommand(this),
                        new GuildKickMemberCommand(this),
                        new ReloadConfigurationCommand(this),
                        new IncognitoCommand(this),
                        new PlayerInfoCommand(this),
                        new GuildAllyInviteCommand(this),
                        new GuildDeputyCommand(this),
                        new GuildMemberLeaveCommand(this),
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
                        new WhoIsCommand(this),
                        new GuildWarehouseCommand(this),
                        new RankingResetCommand(),
                        new BinCommand(),
                        new GuildOwnerTransferCommand(this),
                        new GuildRegenCommand(this),
                        new FreeSpaceCommand(this),
                        new GuildAlertCommand(this),
                        new AchievementCommand(this),
                        new PlayerRankingCommand(this),
                        new FreezeCommand(this),
                        new DisableProtectionCommand(this),
                        new KitManageCommand(this),
                        new CustomEnchantCommand(this),
                        new GuildMemberNeedHelpCommand(this),
                        new RewardCommand(this),
                        new BlocksCommand(),
                        new GuildFightCommand(),
                        new SaveAllCommand(this),
                        new GuildHeartCommand(),
                        new BossCommand(this),
                        new FreeTurboCommand(this),
                        new GuildInviteAllCommand(this),
                        new SafeCommand(),
                        new RepairPickaxeCommand(this),
                        new AdminShopCommand(this),
                        new GroupTeleportCommand(this),
                        new GuildAdminReportCommand(this),
                        new GuildReportCommand(this),
                        new KickCommand(this),
                        new CorePlayerCommand(this)
                ))
                .completer("itemShopServices", (context, prefix, limit) -> CommandUtils.collectCompletions(
                        this.itemShopServiceConfiguration.getServices(),
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
        new FreeSpaceTask(this, this.getServer().getWorld("world")).run();
        new PlayerSpentTimeTask(this);
        new FreezeTask(this);
        new AntiMacroTask(this);
        new AbbysTask(this);
        new RewardTask(this);
        new GuildShadowBlockProtectionTask(this);
        new GuildRegenBlockSaveTask(this);
        new BossTask(this);
        new AntiGriefTask(this);
        new GroupTeleportTask(this);
        new BucketWaterDeleteTask(this);
    }

    private void initListeners() {
        new PlayerQuitJoinListener(this);
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
        new BlockCombatInteractionsListener(this);
        new BorderListener(this);
        new GuildPermissionListener(this);
        new PlayerRespawnListener(this);
        new GuildTntExplosionListener(this);
        new GuildHeartListener(this);
        new ServerSettingsListeners(this);
        new GuildChatListener(this);
        new BlockCombatPlaceListener(this);
        new GuildRegenListener(this);
        new GuildPeriscopeListener(this);
        new AchievementListener(this);
        new AntiMacroListener(this);
        new CustomEnchantListener(this);
        new SafeListener(this);
        new SpawnProtectionListener(this);
        new WarehouseListener(this);
        new FreezeListener(this);
        new BossListener(this);
        new TradeListener(this);
        new AntiGriefListener(this);
        new GuildLogBlockListener(this);
        new GroupTeleportListener(this);
        new BucketInteractionListener(this);
        new PlayerPickupListener(this);
    }

    private void initRecipes() {
        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.BOY_FARMER.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.OBSIDIAN)
                .setIngredient('b', Material.DIAMOND_BLOCK));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.SAND_FARMER.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.SAND)
                .setIngredient('b', Material.DIAMOND_BLOCK));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.AIR_FARMER.getItem())
                .shape("cac", "cbc", "cac")
                .setIngredient('a', Material.EMERALD_BLOCK)
                .setIngredient('b', Material.DIAMOND_BLOCK)
                .setIngredient('c', Material.COBBLESTONE));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.STONE_GENERATOR.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.COBBLESTONE)
                .setIngredient('b', Material.DIAMOND_BLOCK));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.COBBLEX.getItem())
                .shape("aaa", "aaa", "aaa")
                .setIngredient('a', Material.COBBLESTONE));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.ENDER_CHEST.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.OBSIDIAN)
                .setIngredient('b', Material.ENDER_PEARL));

        Bukkit.addRecipe(new ShapedRecipe(CustomRecipe.ANTI_LEGS.getItem())
                .shape("aaa", "aba", "aaa")
                .setIngredient('a', Material.GOLD_BLOCK)
                .setIngredient('b', Material.GOLD_BOOTS));
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

    public CorePlayerCache getCorePlayerCache() {
        return corePlayerCache;
    }

    public CustomEffectCache getCustomEffectCache() {
        return customEffectCache;
    }

    public CustomEffectConfiguration getCustomEffectConfiguration() {
        return customEffectConfiguration;
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

    public ShopConfiguration getShopConfiguration() {
        return shopConfiguration;
    }

    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    public ServerSettingsConfiguration getServerSettingsConfiguration() {
        return serverSettingsConfiguration;
    }

    public DepositItemConfiguration getDepositItemConfiguration() {
        return depositItemConfiguration;
    }

    public GuildCache getGuildCache() {
        return guildCache;
    }

    public GuildCreator getGuildFactory() {
        return guildFactory;
    }

    public GuildRegenCache getRegenCache() {
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

    public ItemShopServiceExecutor getItemShopServiceExecutor() {
        return itemShopServiceExecutor;
    }

    public CorePlayerRepository getCorePlayerRepository() {
        return corePlayerRepository;
    }

    public DatabaseService getDatabaseService() {
        return databaseService;
    }

    public GuildRepository getGuildRepository() {
        return guildRepository;
    }

    public SafeCache getSafeCache() {
        return safeCache;
    }

    public SafeRepository getSafeRepository() {
        return safeRepository;
    }

    public GeneratorRepository getGeneratorRepository() {
        return generatorRepository;
    }

    public PlayerBackupRepository getPlayerBackupRepository() {
        return playerBackupRepository;
    }

    public BossService getBossService() {
        return bossService;
    }

    public TradeCache getTradeCache() {
        return tradeCache;
    }

    public PlayerIncognitoSerivce getPlayerIncognitoSerivce() {
        return playerIncognitoSerivce;
    }

    public AntiGriefCache getAntiGriefCache() {
        return antiGriefCache;
    }

    public GroupTeleportCache getGroupTeleportCache() {
        return groupTeleportCache;
    }

    public RewardRepository getRewardRepository() {
        return rewardRepository;
    }

    public GuildReportCache getGuildReportCache() {
        return guildReportCache;
    }

    public PunishmentRepository getPunishmentRepository() {
        return punishmentRepository;
    }
}
