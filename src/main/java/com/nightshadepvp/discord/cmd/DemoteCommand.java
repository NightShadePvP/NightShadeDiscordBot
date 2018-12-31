package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.Settings;
import com.nightshadepvp.discord.utils.ParseUtils;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Blok on 8/17/2018.
 */
public class DemoteCommand extends Command
{
    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (args.length < 3) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(this.getUsage().build()).queue());
            return;
        }
        final User targetMember = message.getMentionedUsers().get(0);
        if (targetMember == null) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("You must specify a user to demote!").queue();
                privateChannel.sendMessage(this.getUsage().build()).queue();
            });
            return;
        }
        Role newRole;
        if (args[1].equalsIgnoreCase("None")) {
            newRole = null;
        }
        else {
            if (NightShadeBot.getBot().getGuild().getRolesByName(args[1], true) == null) {
                NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
                member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("That role couldn't be found!").queue());
                return;
            }
            newRole = NightShadeBot.getBot().getGuild().getRolesByName(args[1], true).get(0);
        }
        if (!ParseUtils.isBoolean(args[2])) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("Please specify a valid boolean!").queue();
                privateChannel.sendMessage(this.getUsage().build()).queue();
            });
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 3; i < args.length; ++i) {
            stringBuilder.append(args[i]).append(" ");
        }
        final String reason = stringBuilder.toString().trim();

        NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
        NightShadeBot.getBot().getChannelHandler().getRoleLogChannel().sendMessage(this.getOutput(NightShadeBot.getBot().getGuild().getMember(targetMember), newRole, Boolean.parseBoolean(args[2]), reason).build()).queue();
    }

    @Override
    public String getName() {
        return "demote";
    }

    @Override
    public Role requiredRole() {
        return ServerUtils.getRole("Administrator");
    }

    private EmbedBuilder getUsage() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Invalid Arguments!");
        usage.addField("Use the command like this!", Settings.PREFIX + "demote <@user> <new roles separated by comma> <true/false (true=demotion, false=resignation) [reason]", false);
        usage.setColor(Color.RED);
        return usage;
    }

    private EmbedBuilder getOutput(final Member target, final Role newRole, final boolean demoted, final String reason) {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle(":exclamation:   ***Staff Change***   :exclamation:");
        usage.addBlankField(false);
        if (demoted) {
            if (newRole == null) {
                if (reason != null && reason.length() > 0) {
                    usage.addField(":x: **" + target.getEffectiveName() + "** has been fully demoted **because** *" + reason + "* :x:", "", false);
                }
                else {
                    usage.addField(":x: **" + target.getEffectiveName() + "** has been fully demoted :x:", "", false);
                }
            }
            else if (reason != null && reason.length() > 0) {
                usage.addField(":x: **" + target.getEffectiveName() + "** has been demoted to " + newRole.getName() + " **because** *" + reason + "* :x:", "", false);
            }
            else {
                usage.addField(":x: **" + target.getEffectiveName() + "** has been demoted to " + newRole.getName() + " :x:", "", false);
            }
        }
        else if (reason != null && reason.length() > 0) {
            usage.addField(":sob: ***`" + target.getEffectiveName() + "`*** has unfortunately resigned **because** *" + reason + "* :sob:", "", false);
        }
        else {
            usage.addField(":sob: ***`" + target.getEffectiveName() + "`*** has unfortunately resigned :sob:", "", false);
        }
        usage.setColor(new Color(172, 0, 230));
        return usage;
    }
}
