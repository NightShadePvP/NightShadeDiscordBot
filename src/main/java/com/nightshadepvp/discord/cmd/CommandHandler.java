package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.cmd.staff.GetAttendanceCommand;
import com.nightshadepvp.discord.cmd.staff.StartUHC1Command;
import com.nightshadepvp.discord.cmd.staff.StartUHC2Command;
import com.nightshadepvp.discord.cmd.staff.WhitelistCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Blok on 3/18/2018.
 */
public class CommandHandler {
    private ArrayList<Command> commands;

    private NightShadeBot nightShadeBot;

    public CommandHandler(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;

        this.commands = new ArrayList<>();
        this.registerCommands();
    }

    public void registerCommands() {
        this.registerCommand(new ReportBugCommand(nightShadeBot));
        this.registerCommand(new SuggestionCommand(nightShadeBot));
        this.registerCommand(new GetLogsCommand(nightShadeBot));
        this.registerCommand(new NewGameCommand(nightShadeBot));
        this.registerCommand(new AddAllCommand(nightShadeBot));
        this.registerCommand(new SetPlayingCommand(nightShadeBot));
        this.registerCommand(new DemoteCommand(nightShadeBot));
        this.registerCommand(new PromoteCommand(nightShadeBot));
        this.registerCommand(new LinkCommand(nightShadeBot));
        this.registerCommand(new WhitelistCommand(nightShadeBot));
        this.registerCommand(new GetAttendanceCommand(nightShadeBot));
        this.registerCommand(new StartUHC1Command());
        this.registerCommand(new StartUHC2Command());
        this.registerCommand(new SetPlayingCommand(nightShadeBot));
        this.registerCommand(new LinksCommand());
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
                if (c.requiredRole() == null || (sender.getRoles().size() != 0 && sender.getRoles().get(0).getPosition() >= c.requiredRole().getPosition())) {
                    c.run(sender, args, channel, message);
                } else {
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
