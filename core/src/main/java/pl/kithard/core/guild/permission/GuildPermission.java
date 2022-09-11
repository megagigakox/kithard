package pl.kithard.core.guild.permission;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.SkullCreator;

public enum GuildPermission {
    BLOCK_PLACE(new ItemStack(Material.STONE), "Stawianie blokow"),
    BLOCK_BREAK(new ItemStack(Material.STONE, 2),"Niszczenie blokow"),
    OBSIDIAN_BREAK(new ItemStack(Material.OBSIDIAN), "Niszczenie obsydianu"),
    OBSIDIAN_PLACE(new ItemStack(Material.OBSIDIAN, 2), "Stawianie obsydianu"),
    BEACON(new ItemStack(Material.BEACON), "Niszczenie beacona"),
    BEACON_USE(new ItemStack(Material.BEACON, 2), "Używanie beacona"),
    TNT_PLACEMENT(new ItemStack(Material.TNT), "Stawianie tnt"),
    ANVIL_PLACEMENT(new ItemStack(Material.ANVIL), "Stawianie kowadel"),
    RED_STONE_PLACEMENT(new ItemStack(Material.REDSTONE), "Stawianie redstone"),
    BOY_FARMER_PLACEMENT(new ItemStack(Material.CLAY), "Stawianie boyfarmerow"),
    SAND_FARMER_PLACEMENT(new ItemStack(Material.CLAY), "Stawianie sandfarmerow"),
    AIR_FARMER_PLACEMENT(new ItemStack(Material.CLAY), "Stawianie kopaczy fosy"),
    PISTON_PLACEMENT(new ItemStack(Material.PISTON_BASE), "Stawianie pistonow"),
    SAND_PLACEMENT(new ItemStack(Material.SAND), "Stawianie piasku"),
    GRAVEL_PLACEMENT(new ItemStack(Material.GRAVEL), "Stawianie gravelu"),
    END_STONE_PLACEMENT(new ItemStack(Material.ENDER_STONE), "Stawianie stoniarek"),
    END_STONE_BREAK(new ItemStack(Material.ENDER_STONE, 2), "Niszczenie stoniarek zlotym kilofem"),
    WATER_USE(new ItemStack(Material.WATER_BUCKET), "Korzystanie z wody"),
    LAVA_USE(new ItemStack(Material.LAVA_BUCKET), "Korzystanie z lavy"),
    CHEST_ACCESS(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM5MGVlZGUzODFiYjg0NDdmN2Q3MmUxNWI1NjM0NzY4M2UwMmMxN2M5YjhmYzZiZWNkNzI2ZjBhNTJjN2ZjMSJ9fX0="),
            "Otwieranie i niszczenie skrzynek"
    ),
    HOPPER_ACCESS(new ItemStack(Material.HOPPER), "Otwieranie i niszczenie leji"),
    FLINT_USE(new ItemStack(Material.FLINT_AND_STEEL), "Korzystanie z zapalniczki"),
    GUILD_HOME_CREATING(new ItemStack(Material.TRIPWIRE_HOOK), "Ustawianie nowej pozycji bazy gildyjnej"),
    PANEL_ACCESS(new ItemStack(Material.NETHER_STAR), "Dostep do panelu gildyjnego"),
    WAREHOUSE_ACCESS(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJjMmE5ZTNiOTkxNDkyYWNkNjg0ODA0ZjQ2NmJkZTY4MTNmODcyYWU5MDZkNmE2NWQ4YWQ2YTZmYjQwMDA1YyJ9fX0="),
            "Dostep do magazynu"
    ),
    TELEPORTATION_USE(new ItemStack(Material.BLAZE_ROD), "Używanie /tpaccept na terenie gildii"),
    MEMBER_INVITE(new ItemStack(Material.PAPER), "Zaprasznie czlonkow"),
    MEMBER_KICK(new ItemStack(Material.BARRIER), "Wyrzucanie czlonkow"),
    PVP_CHANGE(new ItemStack(Material.DIAMOND_SWORD), "Zmiana trybu pvp"),
    ALLY_CONCLUDING(new ItemStack(Material.NAME_TAG), "Zarzadzanie sojuszami gildyjnymi"),
    GUILD_ENLARGING(new ItemStack(Material.POWERED_RAIL), "Powiekszanie gildii"),
    GUILD_PROLONG(new ItemStack(Material.WATCH), "Przedlużanie ważnosci gildii"),
    PERISCOPE_ACCESS(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJkYzgzNjQ5M2ZmZjNlNDk2ZTRjNTU3NmI0M2FmMzRjOTFkMzNjZmFiYTEyODE0MGM3YWRiZGNmMzA0NSJ9fX0="),
            "Korzystanie z peryskopu gildyjnego"
    ),
    GUILD_ALERT_ACCESS(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU5ZWFhZjAxMDdhM2NhOGFkNTU3ODBiZjY4YmM4ZmQxY2Q5YzI4YjA5NWQ2ZDk3ZmMwNjZiYTIwNDNjMTc1YSJ9fX0="),
            "Uzywanie komendy /g alert"
    ),
    GUILD_REGEN_ACCESS(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1NjhhMTUyZGI1NzA1OTNjYzg5ZmFiYzAzMWJlY2RiOWJkYmRkZDFlYmI5MDM3ZWJhZTQ4ZGE5OGYwZTdjNyJ9fX0="),
            "Dostep do regeneracji cuboida"
    ),
    PERMISSION_MANAGE(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTdjNjZmNWE0YjQwODAwNWIzMzZkYTY2NzZlOGY2YTJhNjdlZWEzMTVmYjdlOTEzNjBhY2MwNDc4MDJmYTMyMCJ9fX0="),
            "Zarzadzanie permisjami czlonkow & schematow"
    ),
    VIEWING_LOGS(
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRiODlhZDA2ZDMxOGYwYWUxZWVhZjY2MGZlYTc4YzM0ZWI1NWQwNWYwMWUxY2Y5OTlmMzMxZmIzMmQzODk0MiJ9fX0="),
            "Przegladanie logow gildyjnych"
    );

    private final ItemStack icon;
    private final String name;

    GuildPermission(ItemStack icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
