package pl.kithard.core.trade;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.TextUtil;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TradeCache {

    private final CorePlugin plugin;

    private final Cache<UUID, List<UUID>> tradeRequestCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(30L))
            .build();
    private final Map<UUID, Trade> tradeMap = new HashMap<>();

    public TradeCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public Trade create(Player sender, Player receiver) {
        Inventory inventory = Bukkit.createInventory(new TradeHolder(), 54, TextUtil.color("&3&lWymiana"));
        for (int i = 4; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, GuiHelper.BLACK_STAINED_GLASS_PANE);
        }
        Trade trade = new Trade(sender, receiver, inventory);
        add(trade);
        return trade;
    }

    public Trade findByUuid(UUID uuid) {
        return this.tradeMap.get(uuid);
    }

    public void add(Trade trade) {
        this.tradeMap.put(trade.getReceiver().getUniqueId(), trade);
        this.tradeMap.put(trade.getSender().getUniqueId(), trade);
    }

    public void remove(Trade trade) {
        this.tradeMap.remove(trade.getReceiver().getUniqueId());
        this.tradeMap.remove(trade.getSender().getUniqueId());
    }

    public Cache<UUID, List<UUID>> getTradeRequestCache() {
        return tradeRequestCache;
    }
}
