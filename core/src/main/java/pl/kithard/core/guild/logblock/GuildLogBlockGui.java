package pl.kithard.core.guild.logblock;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

public class GuildLogBlockGui {

    private final CorePlugin plugin;

    public GuildLogBlockGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .rows(3)
                .create();
    }
}
