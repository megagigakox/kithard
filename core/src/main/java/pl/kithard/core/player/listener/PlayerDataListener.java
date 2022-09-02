package pl.kithard.core.player.listener;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.player.nametag.PlayerNameTagService;
import pl.kithard.core.player.ranking.PlayerRankingService;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.CorePlayerCache;
import pl.kithard.core.player.CorePlayerFactory;
import pl.kithard.core.player.backup.PlayerBackupType;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class PlayerDataListener implements Listener {

    private final CorePlugin plugin;
    private final CorePlayerCache corePlayerCache;
    private final CorePlayerFactory corePlayerFactory;
    private final PlayerRankingService playerRankingService;
    private final PlayerNameTagService playerNameTagService;
    private final GuildCache guildCache;

    public PlayerDataListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.corePlayerCache = plugin.getCorePlayerCache();
        this.corePlayerFactory = plugin.getCorePlayerFactory();
        this.playerRankingService = plugin.getPlayerRankingService();
        this.playerNameTagService = plugin.getPlayerNameTagService();
        this.guildCache = plugin.getGuildCache();
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.corePlayerCache.findByUuid(player.getUniqueId());

        event.setJoinMessage(null);

        if (corePlayer == null) {
            corePlayer = this.corePlayerFactory.create(
                    player.getUniqueId(),
                    player.getName(),
                    player.getAddress().getHostString()
            );
            corePlayer.setNeedSave(true);

            this.corePlayerCache.add(corePlayer);
            this.playerRankingService.add(corePlayer);

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setGameMode(GameMode.SURVIVAL);

            LocationUtil.randomTeleport(player);
            InventoryUtil.addItem(
                    player,
                    Arrays.asList(
                            ItemBuilder.from(new ItemStack(Material.DIAMOND_PICKAXE))
                            .enchant(Enchantment.DIG_SPEED, 3)
                            .enchant(Enchantment.DURABILITY, 1)
                            .build(),
                            new ItemStack(Material.WOOD, 32),
                            new ItemStack(Material.ENDER_CHEST),
                            new ItemStack(Material.COOKED_BEEF, 64)
                    )
            );

        }

        if (!corePlayer.getName().equalsIgnoreCase(player.getName())) {
            String newUsername = player.getName();
            this.corePlayerCache.updateUsername(corePlayer, newUsername);

            Guild guild = this.guildCache.findByPlayer(player);
            if (guild != null) {
                guild.findMemberByUuid(player.getUniqueId()).setName(newUsername);
                guild.setNeedSave(true);
            }
        }

        for (Player it : Bukkit.getOnlinePlayers()) {

            CorePlayer itCorePlayer = this.corePlayerCache.findByPlayer(it);
            if (itCorePlayer.isVanish()) {
                player.hidePlayer(it);
            }

            if (corePlayer.isVanish()) {
                it.hidePlayer(player);
            }
        }

        this.plugin.getPlayerNameTagService().createDummy(corePlayer);
        corePlayer.setLastTimeMeasurement(System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        for (Player it : Bukkit.getOnlinePlayers()) {
            this.playerNameTagService.delete(player, it);
        }

        CorePlayer corePlayer = this.corePlayerCache.findByPlayer(player);
        if (corePlayer.getCombat().hasFight()) {
            player.setHealth(0);
            Bukkit.broadcastMessage(TextUtil.color("&8[&4&l!&8] &cGracz &4" + player.getName() + " &cwylogowal sie podczas walki!"));
        }

        this.plugin.getPlayerBackupFactory().create(player, PlayerBackupType.QUIT, "null", 0);
        this.plugin.getExecutorService().execute(() -> this.plugin.getMongoService().save(corePlayer));
    }

}
