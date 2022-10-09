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
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.nametag.PlayerNameTagService;
import pl.kithard.core.player.ranking.PlayerRankingService;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.CorePlayerCache;
import pl.kithard.core.player.backup.PlayerBackupType;
import pl.kithard.core.safe.Safe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class PlayerDataListener implements Listener {

    private final CorePlugin plugin;

    public PlayerDataListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.plugin.getAntiMacroCache().getUuidClicksPerSecondMap().put(player.getUniqueId(), 0);
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByUuid(player.getUniqueId());
        event.setJoinMessage(null);

        if (corePlayer == null) {
            corePlayer = new CorePlayer(
                    player.getUniqueId(),
                    player.getName(),
                    player.getAddress().getHostString()
            );
            corePlayer.setProtection(TimeUtil.timeFromString("5m") + System.currentTimeMillis());
            this.plugin.getCorePlayerCache().add(corePlayer);
            this.plugin.getPlayerRankingService().add(corePlayer);

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
            player.teleport(LocationUtil.getRadnomLocation());

            Safe safe = this.plugin.getSafeCache().create(player.getUniqueId(), player.getName());
            CorePlayer finalCorePlayer = corePlayer;
            this.plugin.getServer()
                    .getScheduler()
                    .runTaskAsynchronously(
                            this.plugin,
                            () -> {
                                this.plugin.getCorePlayerRepository().insert(finalCorePlayer);
                                this.plugin.getSafeRepository().insert(safe);
                            }
                    );

            InventoryUtil.addItem(
                    player,
                    Arrays.asList(
                            ItemStackBuilder.of(Material.IRON_PICKAXE)
                                    .enchantment(Enchantment.DIG_SPEED, 3)
                                    .enchantment(Enchantment.DURABILITY, 1)
                                    .asItemStack(),
                            new ItemStack(Material.WOOD, 32),
                            new ItemStack(Material.ENDER_CHEST),
                            new ItemStack(Material.COOKED_BEEF, 64),
                            safe.getItem()
                    )
            );

        }

        if (!corePlayer.getName().equalsIgnoreCase(player.getName())) {
            String newUsername = player.getName();
            this.plugin.getCorePlayerCache().updateUsername(corePlayer, newUsername);

            Guild guild = this.plugin.getGuildCache().findByPlayer(player);
            if (guild != null) {
                guild.findMemberByUuid(player.getUniqueId()).setName(newUsername);
                guild.setNeedSave(true);
            }
        }

        for (Player it : Bukkit.getOnlinePlayers()) {

            CorePlayer itCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(it);
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
            this.plugin.getPlayerNameTagService().delete(player, it);
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
//        if (corePlayer.getBoard() != null) {
//            corePlayer.getBoard().delete();
//            corePlayer.setBoard(null);
//        }
        if (corePlayer.getTeleport() != null) {
            corePlayer.setTeleport(null);
        }

        if (corePlayer.getCombat().hasFight()) {
            player.setHealth(0);
            Bukkit.broadcastMessage(TextUtil.color("&8(&4&l!&8) &cGracz &4" + player.getName() + " &cwylogowal sie podczas walki!"));
            return;
        }

        this.plugin.getPlayerBackupFactory().create(player, PlayerBackupType.QUIT, "null", 0);
    }

}
