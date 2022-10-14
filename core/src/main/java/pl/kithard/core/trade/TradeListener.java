package pl.kithard.core.trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TradeListener implements Listener {

    private final CorePlugin plugin;

    public TradeListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof TradeHolder)) {
            return;
        }

        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR || event.getClick().isShiftClick()) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Trade trade = this.plugin.getTradeCache().findByUuid(player.getUniqueId());
        if (trade == null) {
            return;
        }

        int rawSlot = event.getRawSlot();
        if (rawSlot == 3) {
            if (player.getUniqueId().equals(trade.getSender().getUniqueId())) {
                trade.setSenderAccept(!trade.isSenderAccept());
                trade.refreshStatus();
                trade.tryAccept();
                event.setCancelled(true);
                return;
            }
        }
        if (rawSlot == 5) {
            if (player.getUniqueId().equals(trade.getReceiver().getUniqueId())) {
                trade.setReceiverAccept(!trade.isReceiverAccept());
                trade.refreshStatus();
                trade.tryAccept();
                event.setCancelled(true);
                return;
            }
        }

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && event.getClickedInventory().equals(player.getOpenInventory().getTopInventory())) {
            if (player.getUniqueId().equals(trade.getSender().getUniqueId()) && trade.isSenderAccept()) {
                event.setCancelled(true);
                return;
            }

            if (player.getUniqueId().equals(trade.getReceiver().getUniqueId()) && trade.isReceiverAccept()) {
                event.setCancelled(true);
                return;
            }

            List<Integer> allowedSlots = trade.getAllowedSlotsByPlayer(player);
            if (!allowedSlots.contains(rawSlot)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof TradeHolder)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Trade trade = this.plugin.getTradeCache().findByUuid(player.getUniqueId());
        if (trade == null) {
            return;
        }

        List<Integer> allowedSlots = trade.getAllowedSlotsByPlayer(player);
        for (int rawSlot : event.getRawSlots()) {
            if (!allowedSlots.contains(rawSlot)) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof TradeHolder)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        Trade trade = this.plugin.getTradeCache().findByUuid(player.getUniqueId());
        if (trade == null) {
            return;
        }

        if (!trade.isFinished()) {
            trade.giveBackItems();
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> trade.getOppositePlayer(player).closeInventory(), 1L);
        }

        this.plugin.getTradeCache().remove(trade);

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player senderPlayer = event.getPlayer();
        if (event.getRightClicked() instanceof Player && senderPlayer.isSneaking()) {

            if (!LocationUtil.isInSpawn(senderPlayer.getLocation())) {
                return;
            }

            Player receiverPlayer = (Player) event.getRightClicked();
            List<UUID> receiverTradeRequestList = this.plugin.getTradeCache()
                    .getTradeRequestCache()
                    .getIfPresent(receiverPlayer.getUniqueId());

            if (receiverTradeRequestList != null) {
                for (UUID uuid : new ArrayList<>(receiverTradeRequestList)) {

                    if (uuid.equals(senderPlayer.getUniqueId())) {

                        receiverTradeRequestList.remove(uuid);
                        if (receiverTradeRequestList.isEmpty()) {
                            this.plugin.getTradeCache().getTradeRequestCache().invalidate(receiverPlayer.getUniqueId());
                        }

                        if (this.plugin.getTradeCache().findByUuid(receiverPlayer.getUniqueId()) != null) {
                            return;
                        }

                        if (this.plugin.getTradeCache().findByUuid(senderPlayer.getUniqueId()) != null) {
                            return;
                        }

                        Trade trade = this.plugin.getTradeCache().create(senderPlayer, receiverPlayer);
                        trade.openInventory();
                        return;
                    }
                }
            }

            List<UUID> senderTradeRequestList = this.plugin.getTradeCache().getTradeRequestCache().getIfPresent(senderPlayer.getUniqueId());
            if (senderTradeRequestList == null) {
                senderTradeRequestList = new ArrayList<>();
                this.plugin.getTradeCache().getTradeRequestCache().put(senderPlayer.getUniqueId(), senderTradeRequestList);
            }

            if (senderTradeRequestList.contains(receiverPlayer.getUniqueId())) {
                TextUtil.message(senderPlayer, "&8(&4&l!&8) &cWyslales juz prosbe o wymiane do tego gracza!");
                return;
            }

            senderTradeRequestList.add(receiverPlayer.getUniqueId());
            TextUtil.message(senderPlayer, "&8(&3&l!&8) &7Pomyslnie wyslales &3zaproszenie do wymiany &7graczowi: &f{PLAYER}"
                    .replace("{PLAYER}", receiverPlayer.getName()));
            TextUtil.message(receiverPlayer, "&8(&3&l!&8) &7Dostales &3zaproszenie do wymiany &7od gracza: &f{PLAYER}"
                    .replace("{PLAYER}", senderPlayer.getName()));
        }
    }
}
