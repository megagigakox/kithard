package pl.kithard.core.guild.log;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.ItemStackBuilder;

public enum GuildLogType {

    MEMBER_JOIN("Dolaczenie czlonkow", ItemStackBuilder.of(Material.FEATHER).asItemStack()),
    MEMBER_QUIT("Wyjscie czlonkow", ItemStackBuilder.of(Material.WOOD_DOOR).asItemStack()),
    MEMBER_INVITE("Zapraszanie czlonkow", ItemStackBuilder.of(Material.BLAZE_ROD).asItemStack()),
    MEMBER_KICK("Wyrzcanie czlonkow", ItemStackBuilder.of(Material.FENCE_GATE).asItemStack()),
    ALLY_INCLUDE("Zawieranie sojuszy", ItemStackBuilder.of(Material.NAME_TAG).amount(1).asItemStack()),
    ALLY_BREAK("Zrywanie sojuszy", ItemStackBuilder.of(Material.NAME_TAG).amount(2).asItemStack()),
    GUILD_PROLONG("Przedluzanie gildii", ItemStackBuilder.of(Material.WATCH).asItemStack()),
    GUILD_ENLARGE("Powiekszanie gildii", ItemStackBuilder.of(Material.POWERED_RAIL).asItemStack()),
    OTHER("Inne", ItemStackBuilder.of(Material.BUCKET).asItemStack());

    private final String name;
    private final ItemStack icon;

    GuildLogType(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }
}
