package pl.kithard.core.player;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.player.achievement.Achievement;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.deposit.DepositItem;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.player.cooldown.PlayerCooldown;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.player.home.PlayerHome;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.player.teleport.PlayerTeleport;
import pl.kithard.core.shop.item.ShopSellItem;
import pl.kithard.core.api.database.entity.DatabaseEntity;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.drop.DropItem;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

@DatabaseEntity(database = "core", collection = "players")
public class CorePlayer extends DatabaseEntry {

    @SerializedName("_id")
    private final UUID uuid;
    private String name;
    private final String ip;

    private double money, earnedMoney, spendMoney;
    private int points, kills, deaths, assists, killStreak;

    private long turboDrop, spentTime, protection;

    private final Set<UUID> ignoredPlayers;
    private final Set<String> disabledSellItems;
    private final Set<PlayerSettings> disabledSettings;
    private final Set<String> disabledDropItems;
    private final Set<String> guildHistory;
    private final Set<Achievement> claimedAchievements;
    private final List<PlayerHome> homes;
    private final List<PlayerEnderChest> enderChests;
    private final Map<String, Integer> minedDropItems;
    private final Map<String, Integer> depositItems;
    private final Map<String, Long> kitCooldowns;
    private final Map<AchievementType, Long> achievementProgress;

    private transient long lastTimeMeasurement;
    private transient boolean vanish;
    private transient boolean incognito;
    private transient UUID reply;
    private transient PlayerCombat combat;
    private transient PlayerTeleport teleport;
    private transient PlayerCooldown cooldown;
    private transient Map<UUID, Long> teleportRequests;
    private transient Map<UUID, Long> lastDeaths;

    public CorePlayer(UUID uuid, String name, String ip) {
        this.uuid = uuid;
        this.name = name;
        this.ip = ip;

        this.money = 1200;
        this.points = 500;

        this.disabledSellItems = new HashSet<>();
        this.ignoredPlayers = new HashSet<>();
        this.disabledSettings = new HashSet<>();
        this.disabledDropItems = new HashSet<>();
        this.guildHistory = new HashSet<>();
        this.claimedAchievements = new HashSet<>();
        this.homes = new ArrayList<>();
        this.enderChests = new ArrayList<>();
        this.minedDropItems = new HashMap<>();
        this.depositItems = new HashMap<>();
        this.kitCooldowns = new HashMap<>();
        this.achievementProgress = new HashMap<>();

        this.enderChests.addAll(
                Arrays.asList(
                        new PlayerEnderChest(1, "default", "Brak opisu"),
                        new PlayerEnderChest(2, "vip", "Brak opisu"),
                        new PlayerEnderChest(3, "svip", "Brak opisu"),
                        new PlayerEnderChest(4, "sponsor", "Brak opisu"),
                        new PlayerEnderChest(5, "hard", "Brak opisu")
                )
        );

        this.homes.addAll(
                Arrays.asList(
                        new PlayerHome(1, "default"),
                        new PlayerHome(2, "vip"),
                        new PlayerHome(3, "svip"),
                        new PlayerHome(4, "sponsor"),
                        new PlayerHome(5, "hard")
                )
        );

        this.initialize();
    }

    public CorePlayer(Player player) {
        this(player.getUniqueId(), player.getName(), player.getAddress().getAddress().getHostAddress());
    }

    public Player source() {
        return Bukkit.getPlayer(this.getUuid());
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public double getMoney() {
        return MathUtil.round(money,2);
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getEarnedMoney() {
        return MathUtil.round(earnedMoney, 2);
    }

    public void setEarnedMoney(double earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public double getSpendMoney() {
        return MathUtil.round(spendMoney,2);
    }

    public void setSpendMoney(double spendMoney) {
        this.spendMoney = spendMoney;
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

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
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

    public void addAssists(int index) {
        this.assists += index;
    }

    public void removePoints(int index) {
        this.points -= index;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void addKillStreak(int killStreak) {
        this.killStreak += killStreak;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
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

        return MathUtil.round(this.getKills() / (double) this.getDeaths(), 2);
    }

    public long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    public long getLastTimeMeasurement() {
        return lastTimeMeasurement;
    }

    public void setLastTimeMeasurement(long lastTimeMeasurement) {
        this.lastTimeMeasurement = lastTimeMeasurement;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public boolean isIncognito() {
        return incognito;
    }

    public void setIncognito(boolean incognito) {
        this.incognito = incognito;
    }

    public void setTurboDrop(long turboDrop) {
        this.turboDrop = turboDrop;
    }

    public long getTurboDrop() {
        return this.turboDrop;
    }

    public UUID getReply() {
        return reply;
    }

    public void setReply(UUID reply) {
        this.reply = reply;
    }

    public int getAmountOfDepositItem(String depositName) {
        if (depositName == null) {
            return 0;
        }
        if (this.depositItems.isEmpty()) {
            return 0;
        }
        if (!this.depositItems.containsKey(depositName)) {
            return 0;
        }
        if (this.depositItems.get(depositName) == null) {
            return 0;
        }
        return this.depositItems.get(depositName);
    }

    public void addToDeposit(DepositItem depositItem, int amount) {
        int items = this.getAmountOfDepositItem(depositItem.getId());
        items += amount;
        this.depositItems.put(depositItem.getId(), items);
    }

    public void removeFromDeposit(DepositItem depositItem, int amount) {
        int items = this.getAmountOfDepositItem(depositItem.getId());
        items -= amount;
        this.depositItems.put(depositItem.getId(), items);
    }

    public void addDisabledSellItem(ShopSellItem shopSellItem) {
        this.disabledSellItems.add(shopSellItem.getName());
    }

    public void removeDisabledSellItem(ShopSellItem shopSellItem) {
        this.disabledSellItems.remove(shopSellItem.getName());
    }

    public boolean isDisabledSellItem(ShopSellItem shopSellItem) {
        return this.disabledSellItems.contains(shopSellItem.getName());
    }

    public Map<UUID, Long> getTeleportRequests() {
        return teleportRequests;
    }

    public void addTeleportRequest(UUID uuid) {
        this.teleportRequests.put(uuid, System.currentTimeMillis());
    }

    public void removeTeleportRequest(UUID uuid) {
        this.teleportRequests.remove(uuid);
    }

    public boolean isTeleportRequestFromUuid(UUID uuid) {
        return this.teleportRequests.get(uuid) != null
                && this.teleportRequests.get(uuid) + TimeUnit.MINUTES.toMillis(1) > System.currentTimeMillis();
    }

    public Set<UUID> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public void addIgnoredUuid(UUID uuid) {
        this.ignoredPlayers.add(uuid);
    }

    public void removeIgnoredUuid(UUID uuid) {
        this.ignoredPlayers.remove(uuid);
    }

    public boolean isIgnoredUuid(UUID uuid) {
        return this.ignoredPlayers.contains(uuid);
    }

    public List<PlayerHome> getHomes() {
        return homes;
    }

    public void setHome(int id, Location location) {
        findPlayerHome(id).setLocation(location);
    }

    public List<PlayerEnderChest> getEnderChests() {
        return enderChests;
    }

    public PlayerHome findPlayerHome(int id) {
        for (PlayerHome home : this.homes) {
            if (home.getId() == id) {
                return home;
            }
        }
        return null;
    }

    public boolean isDisabledSetting(PlayerSettings settings) {
        return this.disabledSettings.contains(settings);
    }

    public void addDisabledSetting(PlayerSettings settings) {
        this.disabledSettings.add(settings);
    }

    public void removeDisableSetting(PlayerSettings settings) {
        this.disabledSettings.remove(settings);
    }

    public boolean isDisabledDropItem(DropItem dropItem) {
        return this.disabledDropItems.contains(dropItem.getName());
    }

    public void addDisabledDropItem(DropItem dropItem) {
        this.disabledDropItems.add(dropItem.getName());
    }

    public void removeDisabledDropItem(DropItem dropItem) {
        this.disabledDropItems.remove(dropItem.getName());
    }

    public void addMinedDropItem(DropItem dropItem, int amount) {
        int drops = this.getNumberOfMinedDropItem(dropItem.getName());
        drops += amount;

        this.minedDropItems.put(dropItem.getName(), drops);
    }

    public int getNumberOfMinedDropItem(String dropItemName) {
        if (this.minedDropItems.isEmpty()) {
            return 0;
        }
        if (!this.minedDropItems.containsKey(dropItemName)) {
            return 0;
        }
        if (this.minedDropItems.get(dropItemName) == null) {
            return 0;
        }
        return this.minedDropItems.get(dropItemName);
    }

    public Map<UUID, Long> getLastDeaths() {
        return lastDeaths;
    }

    public Map<String, Long> getKitCooldowns() {
        return kitCooldowns;
    }

    public long getTime(String kitName){
        return this.kitCooldowns.getOrDefault(kitName,(long) 0);
    }

    public PlayerCooldown getCooldown() {
        return cooldown;
    }

    public PlayerTeleport getTeleport() {
        return teleport;
    }

    public void setTeleport(PlayerTeleport teleport) {
        this.teleport = teleport;
    }

    public Set<String> getGuildHistory() {
        return guildHistory;
    }

    public void teleport(Location location, long delay) {
        Player player = this.source();
        if (player != null) {

            if (player.hasPermission("kithard.teleport.bypass")) {
                player.teleport(location);
                return;
            }

            if (this.teleport != null) {
                TextUtil.message(player, "&8[&4&l!&8] &cTeleportujesz sie już gdzies!");
                return;
            }

            PlayerTeleport playerTeleport = new PlayerTeleport();
            playerTeleport.setTeleportLocation(location);
            playerTeleport.setTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(delay));
            this.setTeleport(playerTeleport);
        }
    }

    public PlayerCombat getCombat() {
        return combat;
    }

    public long getAchievementProgress(AchievementType type) {
        return this.achievementProgress.getOrDefault(type, 0L);
    }

    public void addAchievementProgress(AchievementType type, long progress) {
        this.achievementProgress.put(type, getAchievementProgress(type) + progress);
    }

    public void setAchievementProgress(AchievementType type, long progress) {
        this.achievementProgress.put(type, progress);
    }

    public boolean isAchievementClaimed(Achievement achievement) {
        return this.claimedAchievements.contains(achievement);
    }

    public void addClaimedAchievement(Achievement achievement) {
        this.claimedAchievements.add(achievement);
    }

    public void initialize() {
        this.teleportRequests = new HashMap<>();
        this.lastDeaths = new HashMap<>();
        this.combat = new PlayerCombat();
        this.cooldown = new PlayerCooldown();
        super.setNeedSave(false);
    }

    public long getProtection() {
        return protection;
    }

    public void setProtection(long protection) {
        this.protection = protection;
    }
}
