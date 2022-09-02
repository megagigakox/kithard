package pl.kithard.core.player.punishment;

import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.punishment.type.Ban;
import pl.kithard.core.player.punishment.type.BanIP;
import pl.kithard.core.player.punishment.type.Mute;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TimeUtil;

public class PunishmentFactory {

    private final CorePlugin plugin;

    public PunishmentFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {

        this.plugin.getMongoService().loadAll(Ban.class).forEach(
                ban -> this.plugin.getPunishmentCache().addBan(ban));

        this.plugin.getMongoService().loadAll(BanIP.class).forEach(
                ban -> this.plugin.getPunishmentCache().addBanIP(ban));

        this.plugin.getMongoService().loadAll(Mute.class).forEach(
                mute -> this.plugin.getPunishmentCache().addMute(mute));

    }

    public void assignBanToPlayer(String punished, String admin, long time, String reason) {
        Ban ban = new Ban(punished, admin, time, reason);
        this.plugin.getPunishmentCache().addBan(ban);

        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().save(ban));

        Player player = this.plugin.getServer().getPlayerExact(punished);
        if (player != null) {
            player.kickPlayer(TextUtil.color(
                    "\n&cZostales zbanowany przez &4" + ban.getAdmin() +
                            "\n&cPowod: &4" + ban.getReason() +
                            "\n&cWygasa: &4" + ((ban.getTime() == 0L) ? "Nigdy!" : ("&cza &4" + TimeUtil.formatTimeMillis(ban.getTime() - System.currentTimeMillis())))));
        }
    }

    public void assignBanIPToPlayer(String punishedIP, String punishedName, String admin, String reason) {
        BanIP ban = new BanIP(punishedIP, admin, reason);
        this.plugin.getPunishmentCache().addBanIP(ban);

        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().save(ban));

        Player player = this.plugin.getServer().getPlayerExact(punishedName);
        if (player != null) {
            player.kickPlayer(TextUtil.color(
                    "&cTwoje ip zostalo zbanowane przez &4" + admin +
                    "\n&cPowod: &4" + reason +
                    "\n&cWygasa: &4Nigdy!"));
        }
    }

    public void assignMuteToPlayer(String punished, String admin, long time, String reason) {
        Mute mute = new Mute(punished, admin, time, reason);
        this.plugin.getPunishmentCache().addMute(mute);

        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().save(mute));

        Player player = this.plugin.getServer().getPlayerExact(punished);
        if (player != null) {
            TextUtil.message(player,
                    "&8[&4&l!&8] &cZostales wyciszony na &4"
                            + (mute.getTime() == 0L ? "zawsze" : TimeUtil.formatTimeMillis(mute.getTime() - System.currentTimeMillis()))
                            + " &cprzez &4" + admin + " &cz powodem: &4" + reason);
        }
    }

    public void reassignBanFromPlayer(String punished) {

        Ban ban = this.plugin.getPunishmentCache().findBan(punished);

        this.plugin.getPunishmentCache().removeBan(ban);
        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().delete(ban));

    }

    public void reassignBanIPFromPlayer(String punished) {

        BanIP ban = this.plugin.getPunishmentCache().findBanIP(punished);

        this.plugin.getPunishmentCache().removeBanIP(ban);
        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().delete(ban));

    }

    public void reassignMuteFromPlayer(String punished) {

        Mute mute = this.plugin.getPunishmentCache().findMute(punished);

        this.plugin.getPunishmentCache().removeMute(mute);
        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().delete(mute));

    }

}
