package pl.kithard.core.player.nametag;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.incognito.PlayerIncognito;
import pl.kithard.core.util.TextUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerNameTagService {

    private final Map<CorePlayer, Scoreboard> dummies = new HashMap<>();

    private final CorePlugin plugin;
    private final LuckPerms luckPerms;
    private PacketContainer packetContainer;

    public PlayerNameTagService(CorePlugin plugin) {
        this.plugin = plugin;
        this.luckPerms = this.plugin.getServer().getServicesManager().load(LuckPerms.class);
    }

    public void create(Player player1, Player player2) {
        try {
            this.packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

            String name = player1.getName();

            this.packetContainer.getStrings().write(0, name);
            this.packetContainer.getStrings().write(1, name);
            this.packetContainer.getStrings().write(2, TextUtil.color(prefix(player1, player2)));
            this.packetContainer.getStrings().write(3, TextUtil.color(suffix(player1, player2)));

            this.packetContainer.getIntegers().write(0, -1);
            this.packetContainer.getIntegers().write(1, 0);
            this.packetContainer.getIntegers().write(2, 1);

            this.packetContainer.getSpecificModifier(Collection.class).write(0, Collections.singletonList(name));
            ProtocolLibrary.getProtocolManager().sendServerPacket(player2, packetContainer);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void update(Player player1, Player player2) {
        try {

            this.packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

            String name = player1.getName();

            this.packetContainer.getStrings().write(0, name);
            this.packetContainer.getStrings().write(1, name);
            this.packetContainer.getStrings().write(2, TextUtil.color(prefix(player1, player2)));
            this.packetContainer.getStrings().write(3, TextUtil.color(suffix(player1, player2)));

            this.packetContainer.getIntegers().write(0, -1);
            this.packetContainer.getIntegers().write(1, 2);
            this.packetContainer.getIntegers().write(2, 1);

            this.packetContainer.getSpecificModifier(Collection.class).write(0, Collections.singletonList(name));
            ProtocolLibrary.getProtocolManager().sendServerPacket(player2, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void delete(Player player1, Player player2) {
        try {

            this.packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
            final String name = player1.getName();

            this.packetContainer.getStrings().write(0, name); //set name
            this.packetContainer.getIntegers().write(1, 1); //set mode, 0=create team

            ProtocolLibrary.getProtocolManager().sendServerPacket(player2, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void send(Player p) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            create(p, online);
            update(p, online);
        }
    }

    public String prefix(Player player1, Player player2) {
        Guild guild1 = this.plugin.getGuildCache().findByPlayer(player1);
        Guild guild2 = this.plugin.getGuildCache().findByPlayer(player2);

        CorePlayer corePlayer1 = this.plugin.getCorePlayerCache().findByPlayer(player1);
        PlayerIncognito playerIncognito = corePlayer1.getPlayerIncognito();
        boolean isNameIncognito = playerIncognito.isNameIncognito();

        if (!player2.hasPermission("kithard.incognito.bypass")) {
            if (guild1 == null) {
                return isNameIncognito ? "&f&k" : "";
            }

            String tag;
            if (playerIncognito.isTagIncognito()) {
                tag = "?";
            }
            else {
                tag = guild1.getTag();
            }

            if (guild2 == null) {
                return "&c["+ tag + "] " + (isNameIncognito ? "&k" : "");
            }
            if (guild1.getTag().equalsIgnoreCase(guild2.getTag())) {
                return "&a["+ tag + "] " + (isNameIncognito ? "&k" : "");
            }
            if (guild2.getAllies().contains(guild1.getTag())) {
                return "&9["+ tag + "] " + (isNameIncognito ? "&k" : "");
            }

            return "&c["+ tag + "] " + (isNameIncognito ? "&k" : "");
        }

        if (guild1 == null) {
            return "";
        }
        if (guild2 == null) {
            return "&c[" + guild1.getTag() + "] ";
        }
        if (guild1.getTag().equalsIgnoreCase(guild2.getTag())) {
            return "&a[" + guild1.getTag() + "] ";
        }
        if (guild2.getAllies().contains(guild1.getTag())) {
            return "&9[" + guild1.getTag() + "] &9";
        }

        return "&c[" + guild1.getTag() + "] ";
    }

    public String suffix(Player player1, Player player2) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player1);
        if (corePlayer.getPlayerIncognito().isNameIncognito() && player2.hasPermission("kithard.incognito.bypass")) {
            return " &bINCOGNITO";
        }

        if (!corePlayer.getPlayerIncognito().isNameIncognito()) {

            CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player1);
            String group = metaData.getPrimaryGroup();

            return " " + this.plugin.getConfig().getString("prefix." + group);
        }
        return "";
    }

    public void createDummy(CorePlayer corePlayer) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("points", "dummy");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(TextUtil.color("&7pkt."));

        for (Player online : Bukkit.getOnlinePlayers()) {
            CorePlayer onlineUser = this.plugin.getCorePlayerCache().findByPlayer(online);

            objective.getScore(online.getName()).setScore(onlineUser.getPoints());
            online.setScoreboard(board);
        }

        this.dummies.put(corePlayer, board);
    }

    public void updateDummy(CorePlayer corePlayer) {
        if (!this.dummies.containsKey(corePlayer)) {
            this.createDummy(corePlayer);
            return;
        }

        Scoreboard board = dummies.get(corePlayer);
        Objective objective = board.getObjective("points");
        for (Player online : Bukkit.getOnlinePlayers()) {
            CorePlayer onlineCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(online);

            if (corePlayer.getPlayerIncognito().isPointsIncognito()) {
                objective.getScore(online.getName()).setScore(onlineCorePlayer.getPoints() + ThreadLocalRandom.current().nextInt(5, 40));
            } else {
                objective.getScore(online.getName()).setScore(onlineCorePlayer.getPoints());
            }

            online.setScoreboard(board);
        }
    }

}