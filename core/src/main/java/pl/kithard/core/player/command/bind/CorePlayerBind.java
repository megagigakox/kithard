package pl.kithard.core.player.command.bind;

import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.Bind;
import net.dzikoysk.funnycommands.resources.Context;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.panda_lang.utilities.inject.Resources;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.CorePlayerCache;

public class CorePlayerBind implements Bind {
    private final CorePlayerCache corePlayerCache;

    public CorePlayerBind(CorePlayerCache corePlayerCache) {
        this.corePlayerCache = corePlayerCache;
    }

    @Override
    public void accept(Resources injectorResources) {
        injectorResources.on(CorePlayer.class)
                .assignHandler((property, annotation, args) -> fetchPlayer(CommandUtils.getContext(args)));
    }

    public CorePlayer fetchPlayer(Context context) {
        CommandSender commandSender = context.getCommandSender();

        if (!(commandSender instanceof OfflinePlayer)) {
            throw new IllegalStateException("Cannot use user bind in non-player command");
        }

        return this.corePlayerCache.findByUuid(((OfflinePlayer) commandSender).getUniqueId());
    }
}
