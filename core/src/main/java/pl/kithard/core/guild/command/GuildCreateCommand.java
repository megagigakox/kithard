package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.border.util.BorderUtil;
import pl.kithard.core.CoreConstants;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TitleUtil;

import java.util.List;

@FunnyComponent
public class GuildCreateCommand {
    private final CorePlugin plugin;

    public GuildCreateCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g zaloz",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {
        GuildCache guildCache = this.plugin.getGuildCache();
        Guild playerGuild = guildCache.findByPlayer(player);
        if (playerGuild != null) {
            TextUtil.message(player, "&8[&4&l!&8] &cPosiadasz juz gildie!");
            return;
        }

        if (args.length != 2) {
            TextUtil.correctUsage(player, "/g zaloz (tag) (nazwa)");
            return;
        }

        String tag = args[0].toUpperCase();
        String name = args[1];

        if (tag.length() > 5 || tag.length() < 2 || name.length() > 32 || name.length() < 4) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie poprawna dlugosc nazwy lub tagu gildii. Tag 2-5 liter, nazwa 4-32");
            return;
        }

        if (guildCache.findByTag(tag) != null || guildCache.findByName(name) != null) {
            TextUtil.message(player, "&8[&4&l!&8] &cGildia o takim tagu lub nazwie juz istnieje!");
            return;
        }

        if (!tag.matches("^[a-zA-Z0-9_]*$") || !name.matches("^[a-zA-Z0-9_]*$")) {
            TextUtil.message(player, "&8[&4&l!&8] &cTag gildii musi byc alfanumeryczny!");
            return;
        }

        if (!guildCache.canCreateGuildBySpawnLocation(player.getLocation())) {
            TextUtil.message(player, "&8[&4&l!&8] &cJestes za blisko spawna, minimalna ilosc kratek od spawna to: &4"
                    + CoreConstants.MIN_DISTANCE_FROM_SPAWN);
            return;
        }

        if (!guildCache.canCreateGuildByGuildLocation(player.getLocation())) {
            TextUtil.message(player, "&8[&4&l!&8] &cJestes za blisko innej gldii, minimalna ilosc kratek od innej gildii to: &4"
                    + CoreConstants.MIN_DISTANCE_FROM_OTHER_GUILD);
            return;
        }

        if (BorderUtil.isBorderNear(player.getLocation(), 120)) {
            TextUtil.message(player, "&8[&4&l!&8] &cJestes za blisko borderu mapy!");
            return;
        }

        List<ItemStack> itemsForGuildCreate;
        if (player.hasPermission("kithard.premium.items")) {
            itemsForGuildCreate = CoreConstants.PREMIUM_GUILD_ITEMS;
        } else {
            itemsForGuildCreate = CoreConstants.PLAYER_GUILD_ITEMS;
        }

        for (ItemStack itemStack : itemsForGuildCreate) {
            if (!InventoryUtil.hasItem(player, itemStack.getType(), itemStack.getAmount())){
                TextUtil.message(player, "&8[&4&l!&8] &cNie posiadasz &4wszystkich itemow &cna zalożenie gildii!");
                return;
            }
        }

        InventoryUtil.removeItems(player, itemsForGuildCreate);

        CorePlayer owner = this.plugin.getCorePlayerCache().findByPlayer(player);
        owner.getGuildHistory().add(tag);
        Guild newGuild = this.plugin.getGuildFactory().create(tag, name, owner);

        Bukkit.broadcastMessage(TextUtil.color("&8[&3&l!&8] &7Gracz &f" + player.getName() + " &7zalożyl gildie &8[&3" + tag + "&8] &b" + name + "&7!"));
        TitleUtil.title(player,
                "&3&lZalożyles gildie " +
                        "&8[&f" +
                        newGuild.getTag()
                        + "&8]",
                "&fUprawnienia czlonkow znajdziesz pod &b/g panel",
                20, 60, 20);
    }
}
