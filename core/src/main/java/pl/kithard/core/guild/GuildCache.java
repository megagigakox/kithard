package pl.kithard.core.guild;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.CoreConstants;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuildCache {
    private final CorePlugin plugin;

    private final Map<String, Guild> guildsByTag = new ConcurrentHashMap<>();

    public GuildCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void add(Guild guild) {
        this.guildsByTag.put(guild.getTag().toLowerCase(Locale.ROOT), guild);
    }

    public void remove(Guild guild) {
        this.guildsByTag.remove(guild.getTag().toLowerCase(Locale.ROOT));
    }

    public Guild findByTag(String tag) {
        return this.guildsByTag.get(tag.toLowerCase(Locale.ROOT));
    }

    public Guild findByName(String name) {
        for (Guild guild : this.getValues()) {
            if (guild.getName().equalsIgnoreCase(name)) {
                return guild;
            }
        }

        return null;
    }

    public Guild findByPlayer(Player player) {
        for (Guild guild : this.getValues()) {
            GuildMember guildMember = guild.findMemberByUuid(player.getUniqueId());

            if (guildMember != null) {
                return guild;
            }
        }

        return null;
    }

    public Guild findByPlayerName(String playerName) {
        for (Guild guild : this.getValues()) {
            GuildMember guildMember = guild.findMemberByName(playerName);

            if (guildMember != null) {
                return guild;
            }
        }

        return null;
    }

    public Guild findByLocation(Location location) {
        for (Guild guild : this.getValues()) {
            if (guild.getRegion().isIn(location)) {
                return guild;
            }
        }

        return null;
    }

    public boolean canCreateGuildBySpawnLocation(Location location) {
        int spawnX = location.getWorld().getSpawnLocation().getBlockX();
        int spawnZ = location.getWorld().getSpawnLocation().getBlockZ();

        int minDistance = CoreConstants.MIN_DISTANCE_FROM_SPAWN;

        return Math.abs(location.getBlockX() - spawnX) >= minDistance
                || Math.abs(location.getBlockZ() - spawnZ) >= minDistance;
    }

    public boolean canCreateGuildByGuildLocation(Location loc) {
        int minDistance = CoreConstants.MIN_DISTANCE_FROM_OTHER_GUILD;
        for (Guild guild : this.getValues()) {
            if (Math.abs(guild.getRegion().getX() - loc.getBlockX()) <= minDistance && Math.abs(guild.getRegion().getZ() - loc.getBlockZ()) <= minDistance) {
                return false;
            }
        }
        return true;
    }

    public boolean isNotAllowed(Player player, GuildPermission permission) {
        Guild guild = this.findByPlayer(player);
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        if (corePlayer == null || guild == null) {
            return false;
        }

        GuildMember guildMember = guild.findMemberByUuid(player.getUniqueId());
        if (guildMember == null) {
            return false;
        }

        if (guild.isDeputyOrOwner(corePlayer.getUuid())) {
            return false;
        }

        if (guildMember.isNotAllowed(permission)) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie posiadasz uprawnien do &4" + permission.getName() + "&c! Popros lidera o nadanie uprawnien.");
            return true;
        }

        return false;
    }

    public boolean isNotAllowed(Player player, Location location, GuildPermission permission) {
        Guild guild = this.findByLocation(location);
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        if (corePlayer == null || guild == null) {
            return false;
        }


        GuildMember guildMember = guild.findMemberByUuid(player.getUniqueId());
        if (guildMember == null) {
            return false;
        }

        if (guild.isDeputyOrOwner(corePlayer.getUuid())) {
            return false;
        }

        if (guildMember.isNotAllowed(permission)) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie posiadasz uprawnien do &4" + permission.getName() + "&c! Popros lidera o nadanie uprawnien.");
            return true;
        }

        return false;
    }

    public Collection<Guild> getValues() {
        return this.guildsByTag.values();
    }
}
