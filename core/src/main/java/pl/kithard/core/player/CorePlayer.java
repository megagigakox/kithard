package pl.kithard.core.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.deposit.DepositItem;
import pl.kithard.core.drop.DropItem;
import pl.kithard.core.player.achievement.Achievement;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.player.cooldown.PlayerCooldown;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.player.home.PlayerHome;
import pl.kithard.core.player.incognito.PlayerIncognito;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.player.teleport.PlayerTeleport;
import pl.kithard.core.shop.item.ShopItem;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CorePlayer extends DatabaseEntry {

    private final UUID uuid;
    private String name;

    private double money, earnedMoney, spendMoney;
    private int points;
    private int kills;
    private int deaths;
    private int assists;
    private int killStreak;
    private long turboDrop;
    private long spendTime;
    private long protection;
    private long lastTimeMeasurement;
    private boolean vanish;
    private PlayerIncognito playerIncognito;

    private final PlayerEnderChest enderChest;
    private final Set<UUID> ignoredPlayers;
    private final Set<String> disabledSellItems;
    private final Set<String> disabledSettings;
    private final Set<String> disabledDropItems;
    private final Set<String> guildHistory;
    private final Set<String> claimedAchievements;
    private final Map<String, Integer> minedDrops;
    private final Map<String, Integer> depositItems;
    private final Map<String, Long> achievementProgress;
    private PlayerCombat combat;
    private PlayerCooldown cooldown;
    private List<PlayerHome> homes;
    private Map<UUID, Long> teleportRequests;
    private PlayerTeleport teleport;
    private UUID reply;

    public CorePlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        this.points = 1000;

        this.disabledSellItems = new HashSet<>();
        this.ignoredPlayers = new HashSet<>();
        this.disabledSettings = new HashSet<>();
        this.disabledDropItems = new HashSet<>();
        this.guildHistory = new HashSet<>();
        this.claimedAchievements = new HashSet<>();
        this.minedDrops = new HashMap<>();
        this.depositItems = new ConcurrentHashMap<>();
        this.achievementProgress = new HashMap<>();
        this.enderChest = new PlayerEnderChest(null);

        init();
    }

    public CorePlayer(UUID uuid, String name, double money, double earnedMoney, double spendMoney, int points, int kills, int deaths, int assists, int killStreak, long turboDrop, long spendTime, long protection, boolean vanish, Set<String> disabledSellItems, Set<String> disabledSettings, Set<String> disabledDropItems, Set<String> guildHistory, Set<String> claimedAchievements, Map<String, Integer> minedDrops, Map<String, Integer> depositItems, Map<String, Long> achievementProgress, PlayerEnderChest enderChest) {
        this.uuid = uuid;
        this.name = name;
        this.money = money;
        this.earnedMoney = earnedMoney;
        this.spendMoney = spendMoney;
        this.points = points;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.killStreak = killStreak;
        this.turboDrop = turboDrop;
        this.spendTime = spendTime;
        this.protection = protection;
        this.vanish = vanish;
        this.ignoredPlayers = new HashSet<>();
        this.disabledSellItems = disabledSellItems;
        this.disabledSettings = disabledSettings;
        this.disabledDropItems = disabledDropItems;
        this.guildHistory = guildHistory;
        this.claimedAchievements = claimedAchievements;
        this.minedDrops = minedDrops;
        this.depositItems = depositItems;
        this.achievementProgress = achievementProgress;
        this.enderChest = enderChest;

        init();
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

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
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
        int items = this.getAmountOfDepositItem(depositItem.getName());
        items += amount;
        this.depositItems.put(depositItem.getName(), items);
    }

    public void removeFromDeposit(DepositItem depositItem, int amount) {
        int items = this.getAmountOfDepositItem(depositItem.getName());
        items -= amount;
        this.depositItems.put(depositItem.getName(), items);
    }

    public void addDisabledSellItem(ShopItem shopItem) {
        this.disabledSellItems.add(shopItem.getName());
    }

    public void removeDisabledSellItem(ShopItem shopSellItem) {
        this.disabledSellItems.remove(shopSellItem.getName());
    }

    public boolean isDisabledSellItem(ShopItem shopSellItem) {
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
        findHome(id).setLocation(location);
    }

    public PlayerHome findHome(int id) {
        for (PlayerHome home : this.homes) {
            if (home.getId() == id) {
                return home;
            }
        }
        return null;
    }

    public boolean isDisabledSetting(PlayerSettings settings) {
        return this.disabledSettings.contains(settings.toString());
    }

    public void addDisabledSetting(PlayerSettings settings) {
        this.disabledSettings.add(settings.toString());
    }

    public void removeDisableSetting(PlayerSettings settings) {
        this.disabledSettings.remove(settings.toString());
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

        this.minedDrops.put(dropItem.getName(), drops);
    }

    public int getNumberOfMinedDropItem(String dropItemName) {
        if (this.minedDrops.isEmpty()) {
            return 0;
        }
        if (!this.minedDrops.containsKey(dropItemName)) {
            return 0;
        }
        if (this.minedDrops.get(dropItemName) == null) {
            return 0;
        }
        return this.minedDrops.get(dropItemName);
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
                TextUtil.message(player, "&8(&4&l!&8) &cTeleportujesz sie już gdzies!");
                return;
            }

            TextUtil.message(player, "&8(&3&l!&8) &7Rozgrzewam teleport... &7Zostaniesz przeteleportowany za&8: &f" + delay + " sekund!");
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
        return this.achievementProgress.getOrDefault(type.toString(), 0L);
    }

    public void addAchievementProgress(AchievementType type, long progress) {
        this.achievementProgress.put(type.toString(), getAchievementProgress(type) + progress);
    }

    public void setAchievementProgress(AchievementType type, long progress) {
        this.achievementProgress.put(type.toString(), progress);
    }

    public boolean isAchievementClaimed(Achievement achievement) {
        return this.claimedAchievements.contains(achievement.getUniqueId());
    }

    public void addClaimedAchievement(Achievement achievement) {
        this.claimedAchievements.add(achievement.getUniqueId());
    }

    public long getProtection() {
        return protection;
    }

    public void setProtection(long protection) {
        this.protection = protection;
    }

    public Set<String> getDisabledSellItems() {
        return disabledSellItems;
    }

    public Set<String> getDisabledDropItems() {
        return disabledDropItems;
    }

    public Map<String, Integer> getMinedDrops() {
        return minedDrops;
    }

    public Map<String, Integer> getDepositItems() {
        return depositItems;
    }

    public Map<String, Long> getAchievementProgress() {
        return achievementProgress;
    }

    public Set<String> getClaimedAchievements() {
        return claimedAchievements;
    }

    public Set<String> getDisabledSettings() {
        return disabledSettings;
    }

    public PlayerEnderChest getEnderChest() {
        return enderChest;
    }

    public void init() {
        this.combat = new PlayerCombat();
        this.cooldown = new PlayerCooldown();
        this.teleportRequests = new HashMap<>();
        this.playerIncognito = new PlayerIncognito();

        this.homes = new ArrayList<>();
        this.homes.add(new PlayerHome(1, null));
        this.homes.add(new PlayerHome(2, null));
        this.homes.add(new PlayerHome(3, null));
        this.homes.add(new PlayerHome(4, null));
        this.homes.add(new PlayerHome(5, null));
    }

    public PlayerIncognito getPlayerIncognito() {
        return playerIncognito;
    }

}
