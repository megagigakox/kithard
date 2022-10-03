package pl.kithard.core.player.chat.listener;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.punishment.type.Mute;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.concurrent.TimeUnit;

public class AsyncPlayerChatListener implements Listener {

    private final CorePlugin plugin;
    private final LuckPerms luckPerms;

    public AsyncPlayerChatListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.luckPerms = this.plugin.getServer().getServicesManager().load(LuckPerms.class);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ServerSettings serverSettings = this.plugin.getServerSettings();

        if (!serverSettings.isEnabled(ServerSettingsType.CHAT) && !player.hasPermission("kithard.commands.chatmanage"))  {
            TextUtil.message(player, "&8(&4&l!&8) &cChat jest aktualnie &4wylaczony&c!");
            event.setCancelled(true);
            return;
        }

        Mute mute = this.plugin.getPunishmentCache().findMute(player.getName());
        long currentTimeMillis = System.currentTimeMillis();
        if (mute != null) {

            if (mute.getTime() != 0L && mute.getTime() <= currentTimeMillis) {
                this.plugin.getPunishmentFactory().reassignMuteFromPlayer(player.getName());
                event.setCancelled(true);
                return;
            }

            TextUtil.message(player,
                    "&8(&4&l!&8) &cJestes wyciszony na &4"
                            + (mute.getTime() == 0L ? "zawsze" : TimeUtil.formatTimeMillis(mute.getTime() - currentTimeMillis))
                            + " &cprzez &4" + mute.getAdmin() + " &cz powodem: &4" + mute.getReason());
            event.setCancelled(true);
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        if (corePlayer.getCooldown().getChatCooldown() > currentTimeMillis && !player.hasPermission("kithard.commands.chatmanage")) {
            TextUtil.message(player,
                    "&8(&4&l!&8) &cNastepnym razem na chacie możesz napisac za &4" +
                            TimeUtil.formatTimeMillis(corePlayer.getCooldown().getChatCooldown() - currentTimeMillis));
            event.setCancelled(true);
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByPlayer(player);
        int points = corePlayer.getPoints();

        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String group = metaData.getPrimaryGroup();
        String format = this.plugin.getConfig().getString("formats." + group);
        format = TextUtil.color(format
                .replace("{POINTS}", String.valueOf(points))
                .replace("{TAG}", guild == null ? "" : "&8[&c" + guild.getTag() + "&8] ")
                .replace("{PLAYER}", player.getName())
                .replace("{RANK}", this.plugin.getConfig().getString("prefix." + group))
                .replace("{MESSAGE}", ChatColor.stripColor(event.getMessage().replace("%", "%%").replace("&", ""))));

        corePlayer.getCooldown().setChatCooldown(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5));
        event.setFormat(format);
    }

}