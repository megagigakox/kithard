package pl.kithard.core.guild;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.kithard.core.guild.permission.GuildPermissionScheme;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.CoreConstants;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.SchematicPaster;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.Arrays;

public class GuildFactory {

    private final CorePlugin plugin;

    public GuildFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public Guild create(String tag, String name, CorePlayer owner) {
        Player player = owner.source();
        Location home = LocationUtil.toCenter(new Location(
                player.getWorld(),
                player.getLocation().getBlockX(),
                31,
                player.getLocation().getBlockZ()
        ));

        Guild guild = new Guild(tag, name, owner, home);
        updateHologram(guild);

        GuildMember guildMember = new GuildMember(guild.getTag(), owner.getUuid(), owner.getName());
        guild.getMembers().add(guildMember);

        GuildPermissionScheme scheme1 = new GuildPermissionScheme(guild.getTag(), "REKRUT");
        GuildPermissionScheme scheme2 = new GuildPermissionScheme(guild.getTag(), "CZLONEK");
        GuildPermissionScheme scheme3 = new GuildPermissionScheme(guild.getTag(), "ZAUFANY");
        GuildPermissionScheme scheme4 =new GuildPermissionScheme(guild.getTag(), "MISTRZ");
        guild.getPermissionSchemes().add(scheme1);
        guild.getPermissionSchemes().add(scheme2);
        guild.getPermissionSchemes().add(scheme3);
        guild.getPermissionSchemes().add(scheme4);
        guild.setNeedSave(true);

        Bukkit.getScheduler().runTask(this.plugin, () -> this.buildHeartRoom(home));
        owner.source().teleport(home);
        this.plugin.getGuildCache().add(guild);
        this.plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(this.plugin, () -> {
                    this.plugin.getGuildRepository().insert(guild);
                    this.plugin.getGuildRepository().insertMember(guildMember);
                    this.plugin.getGuildRepository().insertScheme(scheme1);
                    this.plugin.getGuildRepository().insertScheme(scheme2);
                    this.plugin.getGuildRepository().insertScheme(scheme3);
                    this.plugin.getGuildRepository().insertScheme(scheme4);
                });

        return guild;
    }

    public void updateHologram(Guild guild) {
        if (guild.getHologram() != null) {
            guild.getHologram().delete();
        }

        guild.setHologram(null);
        CorePlayer owner = this.plugin.getCorePlayerCache().findByUuid(guild.getOwner());
        Hologram hologram = HologramsAPI.createHologram(this.plugin, guild.getRegion()
                .getCenter()
                .clone()
                .add(0.0, 2.5, 0.0));

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        skullMeta.setOwner(owner.getName());
        head.setItemMeta(skullMeta);

        int size = guild.getRegion().getSize() * 2;
        hologram.appendItemLine(head);
        hologram.appendTextLine(TextUtil.color("   &8&l&m--[&b&l&m---&b&l " + guild.getTag() + " &b&l&m---&8&l&m]--"));
        hologram.appendTextLine(TextUtil.color("&7Lider: &f" + owner.getName()));
        hologram.appendTextLine(TextUtil.color("&7Rozmiar cuboida: &f" + size + "x" + size));
        hologram.appendTextLine(TextUtil.color("&7Wygasa za: &f" + TimeUtil.formatTimeMillis(guild.getExpireTime() - System.currentTimeMillis())));
        hologram.appendTextLine(TextUtil.color("&7Atak możliwy za: &f" + (guild.getLastAttackTime() > System.currentTimeMillis()
                ? TimeUtil.formatTimeMillis(guild.getLastAttackTime() - System.currentTimeMillis())
                : "&b&lteraz!")));
        hologram.appendTextLine(TextUtil.color("&7Serca: &c" + guild.getHearts()));

        guild.setHologram(hologram);
    }

    public void delete(Guild guild) {

        for (String allyTag : guild.getAllies()) {

            Guild ally = this.plugin.getGuildCache().findByTag(allyTag);
            if (ally != null) {
                ally.getAllies().remove(guild.getTag());
            }
        }

        guild.getRegion().getCenter()
                .clone()
                .subtract(0,1,0)
                .getBlock()
                .setType(Material.AIR);
        guild.getHologram().delete();

        this.plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(this.plugin, () -> this.plugin.getGuildRepository().delete(guild));
        this.plugin.getGuildCache().remove(guild);
    }

    public void loadAll() {
        this.plugin.getGuildRepository().loadAll().forEach(guild -> this.plugin.getGuildCache().add(guild));
        this.plugin.getGuildRepository().loadMembers();
        this.plugin.getGuildRepository().loadSchemes();
        this.plugin.getGuildRepository().loadRegenBlocks();
        this.plugin.getGuildRepository().loadLogs();
    }

    private void buildHeartRoom(Location location) {

        SchematicPaster.pasteSchematic(
                CoreConstants.CENTER_SCHEMATIC,
                new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                false
        );
    }
}
