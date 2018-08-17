package com.nightshadepvp.discord.cmd;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Blok on 3/18/2018.
 */
public class CommandHandler
{
    private ArrayList<Command> commands;

    public CommandHandler() {
        this.commands = new ArrayList<>();
        this.registerCommands();
    }

    public void registerCommands() {
        this.registerCommand(new ReportBugCommand());
        this.registerCommand(new SuggestionCommand());
        this.registerCommand(new GetLogsCommand());
        this.registerCommand(new NewGameCommand());
        this.registerCommand(new AddAllCommand());
        this.registerCommand(new SetPlayingCommand());
        this.registerCommand(new DemoteCommand());
        this.registerCommand(new PromoteCommand());
        this.registerCommand(new LinkCommand());
    }

    private void registerCommand(final Command command) {
        this.commands.add(command);
    }

    public void handle(final Command command, final String[] args, final MessageChannel channel, final Member sender, final Message message) {
        final EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("No Permission!");
        builder.setColor(Color.RED);
        for (final Command c : this.commands) {
            if (c.getName().equalsIgnoreCase(command.getName())) {
                if (c.requiredRole() == null || sender.getRoles().contains(c.requiredRole())) {
                    c.run(sender, args, channel, message);
                }
                else {
                    builder.addField("", "You don't have permission to do this command, " + sender.getEffectiveName(), false);
                    channel.sendMessage(builder.build()).queue();
                }
            }
        }
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }
}
