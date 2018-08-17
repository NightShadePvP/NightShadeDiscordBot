package com.nightshadepvp.discord;

import com.nightshadepvp.discord.cmd.Command;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Created by Blok on 3/31/2018.
 */
public class Listener extends ListenerAdapter
{
    @Override
    public void onReady(final ReadyEvent e) {
        System.out.println("Bot is ready!");
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent e) {
        final User user = e.getAuthor();
        final String content = e.getMessage().getContentRaw().trim();
        if (content.startsWith(Settings.PREFIX)) {
            final ArrayList<Command> cmds = NightShadeBot.getBot().getCommandHandler().getCommands();
            if (cmds.stream().anyMatch(command -> content.startsWith(Settings.PREFIX + command.getName()))) {
                final Command cmd = cmds.stream().filter(command -> content.startsWith(Settings.PREFIX + command.getName())).findFirst().get();
                final String[] args = content.split(" ");
                if (args == null || args.length == 0) {
                    NightShadeBot.getBot().getCommandHandler().handle(cmd, null, e.getChannel(), e.getMember(), e.getMessage());
                }
                else {
                    final ArrayList<String> arguements = new ArrayList<String>();
                    for (final String s : args) {
                        if (s != null && s.length() > 0) {
                            arguements.add(s);
                        }
                    }
                    arguements.remove(0);
                    final String[] toReturn = arguements.toArray(new String[arguements.size()]);
                    NightShadeBot.getBot().getCommandHandler().handle(cmd, toReturn, e.getChannel(), e.getMember(), e.getMessage());
                }
            }
        }
        if (e.getMessage().getMentionedRoles().contains(NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0))) {
            if (user.isBot() || user.isFake()) {
                return;
            }
            if (e.getMember().getRoles().contains(NightShadeBot.getBot().getGuild().getRolesByName("Owner", false).get(0))) {
                return;
            }
            e.getMessage().delete().queue();
        }
    }

    @Override
    public void onMessageReactionAdd(final MessageReactionAddEvent e) {
        if (e.getUser().isFake() || e.getUser().isBot()) {
            return;
        }
        if ((e.getChannel().getIdLong() == NightShadeBot.getBot().getChannelHandler().getBugsChannel().getIdLong() || e.getChannel().getIdLong() == NightShadeBot.getBot().getChannelHandler().getSuggestionsChannel().getIdLong()) && (e.getReactionEmote().getName().equalsIgnoreCase(":white_check_mark:") || e.getReactionEmote().getName().equalsIgnoreCase("\u2705"))) {
            e.getChannel().deleteMessageById(e.getMessageIdLong()).queue();
        }
    }
}

