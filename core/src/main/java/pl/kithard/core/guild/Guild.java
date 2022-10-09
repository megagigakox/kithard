package pl.kithard.core.guild;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.guild.permission.GuildPermissionScheme;
import pl.kithard.core.guild.regen.GuildRegenBlock;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.api.database.entity.DatabaseEntity;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Guild extends DatabaseEntry {

    private final String tag;

    private final String name;
    private UUID owner;
    private final GuildRegion region;
    private Location home;
    private int
            hp,
            lives,
            points,
            kills,
            deaths;
    private long
            expireTime,
            createTime,
            lastAttackTime,
            lastExplodeTime;
    private boolean friendlyFire, allyFire;

    private final Map<GuildLogType, List<GuildLog>> logs = new HashMap<>();
    private final Set<GuildMember> members = new HashSet<>();
    private final List<GuildPermissionScheme> permissionSchemes = new ArrayList<>();
    private Set<UUID> deputies = new HashSet<>();
    private Set<String> allies = new HashSet<>();
    private ItemStack[] warehouseContents;
    private Inventory warehouse;

    private long guildWarCooldown;
    private Set<UUID> memberInvites;
    private Set<String> allyInvites;
    private Hologram hologram;

    public Guild(String tag, String name, UUID owner, GuildRegion region, Location home, int hp, int lives, int points, int kills, int deaths, long expireTime, long createTime, long lastAttackTime, long lastExplodeTime, boolean friendlyFire, boolean allyFire, Set<UUID> deputies, Set<String> allies, ItemStack[] warehouseContents) {
        this.tag = tag;
        this.name = name;
        this.owner = owner;
        this.region = region;
        this.home = home;
        this.hp = hp;
        this.lives = lives;
        this.points = points;
        this.kills = kills;
        this.deaths = deaths;
        this.expireTime = expireTime;
        this.createTime = createTime;
        this.lastAttackTime = lastAttackTime;
        this.lastExplodeTime = lastExplodeTime;
        this.friendlyFire = friendlyFire;
        this.allyFire = allyFire;
        this.deputies = deputies;
        this.allies = allies;
        this.warehouseContents = warehouseContents;

        this.initialize();
    }

    public Guild(String tag, String name, CorePlayer owner, Location home) {

        this.tag = tag;
        this.name = name;
        this.region = new GuildRegion(home, 25);
        this.owner = owner.getUuid();
        this.home = home;

        this.createTime = System.currentTimeMillis();
        this.expireTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3);
        this.lastAttackTime = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24);

        this.points = 500;
        this.kills = 0;
        this.deaths = 0;
        this.lives = 3;
        this.hp = 500;

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
        if (lives == 5) {
            return "❤❤❤❤❤";
        }
        if (lives == 4) {
            return "❤❤❤❤";
        }
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

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public long getLastExplodeTime() {
        return lastExplodeTime;
    }

    public void setLastExplodeTime(long lastExplodeTime) {
        this.lastExplodeTime = lastExplodeTime;
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

    public Set<UUID> getDeputies() {
        return deputies;
    }

    public boolean isOwner(UUID uuid) {
        return this.getOwner().equals(uuid);
    }

    public boolean isDeputyOrOwner(UUID uuid) {
        return deputies.contains(uuid) || this.getOwner().equals(uuid);
    }

    public boolean isDeputy(UUID uuid) {
        return deputies.contains(uuid);
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

    public List<GuildLog> getLogsByType(GuildLogType type) {
        return this.logs.computeIfAbsent(type, x -> new ArrayList<>());
    }

    public GuildLog addLog(GuildLog log) {
        List<GuildLog> logs = this.logs.computeIfAbsent(log.getType(), x -> new ArrayList<>());
        logs.add(log);
        return log;
    }

    public void openWarehouse(Player player) {
        if (this.warehouseContents != null) {
            this.warehouse.setContents(warehouseContents);
        }

        player.openInventory(this.warehouse);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
    }

    public void initialize() {
        this.allyInvites = new HashSet<>();
        this.memberInvites = new HashSet<>();

        this.warehouse = Bukkit.createInventory(null, 54, TextUtil.color("&7Magazyn gildii&8: &3" + tag));
    }

    public long getGuildWarCooldown() {
        return guildWarCooldown;
    }

    public void setGuildWarCooldown(long guildWarCooldown) {
        this.guildWarCooldown = guildWarCooldown;
    }

    public ItemStack[] getWarehouseContents() {
        return warehouseContents;
    }

    public void setWarehouseContents(ItemStack[] warehouseContents) {
        this.warehouseContents = warehouseContents;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
