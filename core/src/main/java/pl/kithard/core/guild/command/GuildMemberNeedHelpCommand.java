package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.util.TextUtil;

import java.util.concurrent.TimeUnit;

@FunnyComponent
public class GuildMemberNeedHelpCommand {

    private final CorePlugin plugin;

    public GuildMemberNeedHelpCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g pp",
            aliases = "pp",
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        guild.getOnlineMembers().forEach(guildMember ->
                this.plugin.getActionBarNoticeCache().add(guildMember.getUuid(), ActionBarNotice.builder()
                        .type(ActionBarNoticeType.NEED_HELP)
                        .text("&f" + player.getName() + " &7potrzebuje pomocy, kordy&8: &fx:" + player.getLocation().getBlockX() + " z:" + player.getLocation().getBlockZ())
                        .expireTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15))
                        .build())
        );

        TextUtil.message(player, "&8(&2&l!&8) &aWyslano prosbe o pomoc z twoimi kordynatami do czlonkow twojej gildii!");
    }
}
