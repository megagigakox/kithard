package pl.kithard.core.player.punishment;

import pl.kithard.core.player.punishment.type.Ban;
import pl.kithard.core.player.punishment.type.BanIP;
import pl.kithard.core.player.punishment.type.Mute;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PunishmentCache {

    private final Map<String, Ban> bannedPlayers = new HashMap<>();
    private final Map<String, BanIP> bannedIpPlayers = new HashMap<>();
    private final Map<String, Mute> mutedPlayers = new HashMap<>();

    public void addBan(Ban ban) {
        this.bannedPlayers.put(ban.getPunished().toLowerCase(Locale.ROOT), ban);
    }

    public void addBanIP(BanIP ban) {
        this.bannedIpPlayers.put(ban.getPunishedIP(), ban);
    }

    public void addMute(Mute mute) {
        this.mutedPlayers.put(mute.getPunished().toLowerCase(Locale.ROOT), mute);
    }

    public void removeBan(Ban ban) {
        this.bannedPlayers.remove(ban.getPunished().toLowerCase(Locale.ROOT));
    }

    public void removeBanIP(BanIP ban) {
        this.bannedIpPlayers.remove(ban.getPunishedIP());
    }

    public void removeMute(Mute mute) {
        this.mutedPlayers.remove(mute.getPunished().toLowerCase(Locale.ROOT));
    }

    public Ban findBan(String punished) {
        return this.bannedPlayers.get(punished.toLowerCase(Locale.ROOT));
    }

    public BanIP findBanIP(String punished) {
        return this.bannedIpPlayers.get(punished);
    }

    public Mute findMute(String punished) {
        return this.mutedPlayers.get(punished.toLowerCase(Locale.ROOT));
    }



}
