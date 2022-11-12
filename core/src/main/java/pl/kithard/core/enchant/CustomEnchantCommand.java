package pl.kithard.core.enchant;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class CustomEnchantCommand {

    private final CorePlugin plugin;

    public CustomEnchantCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "enchant",
            aliases = "ench",
            permission = "kithard.commands.enchant",
            acceptsExceeded = true
    )
    public void open(Player player) {
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            return;
        }

        CustomEnchantType customEnchantType = this.plugin.getCustomEnchantConfiguration().findType(player.getItemInHand().getType().name());
        if (customEnchantType == CustomEnchantType.ARMOR || customEnchantType == CustomEnchantType.BOW || customEnchantType == CustomEnchantType.SWORD || customEnchantType == CustomEnchantType.BOOTS) {
            if (!this.plugin.getServerSettings().isEnabled(ServerSettingsType.ENCHANT)) {
                TextUtil.message(player, "&8(&4&l!&8) &cEnchantowanie tych przedmiotow zostalo tymczasowo wylaczone, sprawdz nasz plan edycji na: dsc.gg/kithard aby dowiedziec sie wiecej!");
                return;
            }
        }
        if (customEnchantType == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz tego zenchantowac!");
            return;
        }

        new CustomEnchantGui(plugin).open(player, customEnchantType);
    }
}
