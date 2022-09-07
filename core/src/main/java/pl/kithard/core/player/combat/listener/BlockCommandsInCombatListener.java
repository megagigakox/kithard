package pl.kithard.core.player.combat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.util.TextUtil;

import java.util.Locale;

public class BlockCommandsInCombatListener implements Listener {

    private final static String[] DISALLOWED_COMMANDS = {
            "wb",
            "workbench",
            "craft",
            "craftingi",
            "crafting",
            "bloki",
            "nagroda",
            "os",
            "osiagniecia",
            "achievement",
            "achievements",
            "sklep",
            "shop",
            "depozyt",
            "kit",
            "kity",
            "schowek",
            "depozyt",
            "depo",
            "home",
            "sethome",
            "spawn",
            "tpa",
            "tpaccept",
            "g baza",
            "g dom",
            "g home",
            "g magazyn",
            "warp",
            "enderchest",
            "ec",
            "heal",
            "feed",
            "repair",
            "repair all",
            "fix",
            "fix all"
    };

    private final CorePlugin plugin;

    public BlockCommandsInCombatListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        PlayerCombat playerCombat = corePlayer.getCombat();

        String message = event.getMessage().toLowerCase(Locale.ROOT);
        if (playerCombat.hasFight() && !player.hasPermission("kithard.antilogout.bypass")) {
            for (String it : DISALLOWED_COMMANDS) {
                if (message.contains("/" + it)) {
                    event.setCancelled(true);
                    TextUtil.message(event.getPlayer(), "&8[&4&l!&8] &cTa komenda zostala zablokowana podczas walki!");
                    break;
                }
            }
        }

    }
}
