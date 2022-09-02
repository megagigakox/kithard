package pl.kithard.core.player.settings;

public enum PlayerSettings {

    COBBLE_DROP("Status dropu z cobbla", false),
    MAGIC_CHEST_MESSAGES("Powiadomienia o dropach z magicznych skrzynek", true),
    DROP_MESSAGES("Powiadomienia o dropie ze stone", true),
    DEATHS_MESSAGES("Powiadomienia o smierciach", true),
    ITEM_SHOP_SERVICES_MESSAGES("Powiadomienia o zakupach z itemshopu", true),
    AUTO_MESSAGES("Automatyczne wiadomosci", true),
    TELEPORT_REQUESTS("Prosby o teleportacje", true),
    PRIVATE_MESSAGES("Prywatne wiadomosci", true),
    ACHIEVEMENTS_CLAIMING_MESSAGES("Powiadomienia o odebranych osiagnieciach", true);

    private final String name;
    private final boolean gui;

    PlayerSettings(String name, boolean gui) {
        this.name = name;
        this.gui = gui;
    }

    public boolean isGui() {
        return gui;
    }

    public String getName() {
        return name;
    }
}
