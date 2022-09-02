package pl.kithard.core.guild.command.bind;


import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.Bind;
import net.dzikoysk.funnycommands.resources.ValidationException;
import org.panda_lang.utilities.inject.Resources;
import pl.kithard.core.player.command.bind.CorePlayerBind;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.CorePlayerCache;
import pl.kithard.core.util.TextUtil;

public class GuildBind implements Bind {
    private final CorePlayerBind corePlayerBind;
    private final CorePlugin plugin;

    public GuildBind(CorePlayerCache corePlayerCache, CorePlugin plugin) {
        this.corePlayerBind = new CorePlayerBind(corePlayerCache);
        this.plugin = plugin;
    }

    @Override
    public void accept(Resources injectorResources) {
        injectorResources.on(Guild.class).assignHandler((property, annotation, args) -> {
            CorePlayer corePlayer = this.corePlayerBind.fetchPlayer(CommandUtils.getContext(args));

            Guild guild = this.plugin.getGuildCache().findByPlayer(corePlayer.source());
            if (guild == null) {
                throw new ValidationException(TextUtil.color("&8[&4&l!&8] &cNie posiadasz gildii!"));
            }

            return guild;
        });
    }
}
