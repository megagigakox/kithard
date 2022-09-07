package pl.kithard.core.guild;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.guild.permission.GuildPermissionScheme;
import pl.kithard.core.guild.regen.RegenBlock;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.api.database.entity.DatabaseEntity;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@DatabaseEntity(database = "core", collection = "guilds")
public class Guild extends DatabaseEntry {

    @SerializedName("_id")
    private final String tag;

    private final String name;
    private UUID owner;
    private final GuildRegion region;
    private Location home;
    private int lives,
            points,
            kills,
            deaths,
            emeraldBlocks;
    private long expireDate,
            createDate,
            lastAttackDate,
            lastExplodeDate;
    private boolean friendlyFire, allyFire;

    private final Set<RegenBlock> regenBlocks;
    private final Map<GuildLogType, List<GuildLog>> logTypes;
    private final Set<GuildMember> members;
    private final Set<GuildMember> deputies;
    private final Set<String> allies;
    private final List<GuildPermissionScheme> permissionSchemes;
    private final Inventory warehouse;

    private transient Set<UUID> memberInvites;
    private transient Set<String> allyInvites;
    private transient Hologram hologram;


    public Guild(String tag, String name, CorePlayer owner, Location home) {

        this.tag = tag;
        this.name = name;
        this.region = new GuildRegion(home, 25);
        this.owner = owner.getUuid();
        this.home = home;

        this.createDate = System.currentTimeMillis();
        this.expireDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3);
        this.lastAttackDate = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24);

        this.points = 500;
        this.kills = 0;
        this.deaths = 0;
        this.lives = 3;

        this.regenBlocks = new HashSet<>();
        this.logTypes = new HashMap<>();
        this.members = new HashSet<>();
        this.deputies = new HashSet<>();
        this.allies = new HashSet<>();
        this.permissionSchemes = new ArrayList<>();

        this.warehouse = Bukkit.createInventory(null, 54, TextUtil.color("&7Magazyn gildyjny:"));
        this.initialize();
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public GuildRegion getRegion() {
        return region;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public int getLives() {
        return lives;
    }

    public String getHearts() {
        if (lives == 3) {
            return "❤❤❤";
        }
        if (lives == 2) {
            return "❤❤";
        }
        if (lives == 1) {
            return "❤";
        }
        return "";
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addPoints(int index) {
        this.points += index;
    }

    public void addKills(int index) {
        this.kills += index;
    }

    public void addDeaths(int index) {
        this.deaths += index;
    }

    public void removePoints(int index) {
        this.points -= index;
    }

    public double getKd() {
        if (this.getKills() == 0 && this.getDeaths() == 0) {
            return 0.0;
        }
        if (this.getKills() > 0 && this.getDeaths() == 0) {
            return this.getKills();
        }
        if (this.getDeaths() > 0 && this.getKills() == 0) {
            return -this.getDeaths();
        }

        return MathUtil.round(this.getKills() / (double)this.getDeaths(), 2);
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getLastAttackDate() {
        return lastAttackDate;
    }

    public void setLastAttackDate(long lastAttackDate) {
        this.lastAttackDate = lastAttackDate;
    }

    public long getLastExplodeDate() {
        return lastExplodeDate;
    }

    public void setLastExplodeDate(long lastExplodeDate) {
        this.lastExplodeDate = lastExplodeDate;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public boolean isAllyFire() {
        return allyFire;
    }

    public void setAllyFire(boolean allyFire) {
        this.allyFire = allyFire;
    }

    public Set<GuildMember> getMembers() {
        return members;
    }

    public Set<GuildMember> getOnlineMembers() {
        return this.members.stream()
                .filter(guildMember -> Bukkit.getPlayer(guildMember.getUuid()) != null)
                .collect(Collectors.toSet());
    }

    public GuildMember findMemberByName(String name) {
        for (GuildMember guildMember : this.getMembers()) {
            if (guildMember.getName().equalsIgnoreCase(name)) {
                return guildMember;
            }
        }
        return null;
    }

    public GuildMember findMemberByUuid(UUID uuid) {
        for (GuildMember guildMember : this.getMembers()) {
            if (guildMember.getUuid().equals(uuid)) {
                return guildMember;
            }
        }
        return null;
    }
    public boolean isMember(UUID uuid) {
        return this.findMemberByUuid(uuid) != null;
    }

    public Set<GuildMember> getDeputies() {
        return deputies;
    }

    public boolean isOwner(UUID uuid) {
        return this.getOwner().equals(uuid);
    }

    public boolean isDeputyOrOwner(UUID uuid) {
        return deputies.contains(findMemberByUuid(uuid)) || this.getOwner().equals(uuid);
    }

    public boolean isDeputy(UUID uuid) {
        return deputies.contains(findMemberByUuid(uuid));
    }

    public Set<String> getAllies() {
        return allies;
    }

    public Set<UUID> getMemberInvites() {
        return memberInvites;
    }

    public Set<String > getAllyInvites() {
        return allyInvites;
    }

    public List<GuildPermissionScheme> getPermissionSchemes() {
        return permissionSchemes;
    }

    public GuildPermissionScheme findPermissionSchemeByName(String name) {
        for (GuildPermissionScheme guildPermissionScheme : this.getPermissionSchemes()) {
            if (guildPermissionScheme.getName().equals(name)) {
                return guildPermissionScheme;
            }
        }
        return null;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public void sendMessageToOnlineMembers(String message) {

        for (GuildMember guildMember : this.members) {

            Player player = Bukkit.getPlayer(guildMember.getUuid());
            if (player == null) {
                continue;
            }

            TextUtil.message(player, message);

        }

    }

    public Set<RegenBlock> getRegenBlocks() {
        return regenBlocks;
    }

    public List<GuildLog> getLogsByType(GuildLogType type) {
        return this.logTypes.computeIfAbsent(type, x -> new ArrayList<>());
    }

    public void addLog(GuildLog log) {
        List<GuildLog> logs = this.logTypes.computeIfAbsent(log.getType(), x -> new ArrayList<>());
        logs.add(log);
    }

    public void openWarehouse(Player player) {
        player.openInventory(this.warehouse);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
    }

    public void initialize() {
        this.allyInvites = new HashSet<>();
        this.memberInvites = new HashSet<>();
    }

    public int getEmeraldBlocks() {
        return emeraldBlocks;
    }

    public void setEmeraldBlocks(int emeraldBlocks) {
        this.emeraldBlocks = emeraldBlocks;
    }
}
