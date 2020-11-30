package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;

/**
 * Created by Blok on 3/31/2018.
 */
public class ReportBugCommand extends Command {
    
    private NightShadeBot nightShadeBot;

    public ReportBugCommand(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (args == null || args.length == 0) {
            channel.sendMessage(this.getUsage().build()).queue();
            return;
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            builder.append(args[i]).append(" ");
        }
        final String bug = builder.toString().trim();
        channel.sendMessage(this.getSuccess(member).build()).queue();
        nightShadeBot.getChannelHandler().getBugsChannel().sendMessage(this.getToSend(bug, member).build()).queue();
    }

    @Override
    public String getName() {
        return "reportbug";
    }

    @Override
    public Role requiredRole() {
        return null;
    }

    private EmbedBuilder getUsage() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Invalid Arguments!");
        usage.addField("Use the command like this!", Settings.PREFIX + "reportbug <info>", true);
        usage.setColor(Color.RED);
        return usage;
    }

    private EmbedBuilder getSuccess(final Member member) {
        final EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Success!");
        success.addField("", "Your bug has been submitted to the developers, " + member.getEffectiveName(), false);
        success.setColor(new Color(172, 0, 230));
        return success;
    }

    private EmbedBuilder getToSend(final String bug, final Member member) {
        final EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Bug Reported!");
        success.addField("", "A Bug Report has been submitted by " + member.getEffectiveName(), false);
        success.addBlankField(false);
        success.addField("Bug Description", bug, false);
        success.setFooter("React with a check mark when the bug report is resolved!", null);
        success.setColor(new Color(172, 0, 230));
        return success;
    }
}
