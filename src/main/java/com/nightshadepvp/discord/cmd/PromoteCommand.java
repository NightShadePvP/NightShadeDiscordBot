package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Blok on 8/17/2018.
 */
public class PromoteCommand extends Command {

    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (args.length != 2) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(this.getUsage().build()).queue());
            return;
        }
        final User targetMember = message.getMentionedUsers().get(0);
        if (targetMember == null) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("You must specify a user to promote!").queue();
                privateChannel.sendMessage(this.getUsage().build()).queue();
            });
            return;
        }
        if (NightShadeBot.getBot().getGuild().getRolesByName(args[1], true).get(0) == null) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("That role couldn't be found!").queue());
            return;
        }
        final Role newRole = NightShadeBot.getBot().getGuild().getRolesByName(args[1], true).get(0);
        if (NightShadeBot.getBot().getGuild().getMember(targetMember).getRoles().contains(newRole)) {
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage(targetMember.getName() + " already has the " + newRole.getName() + " role!").queue();
                privateChannel.sendMessage(this.getUsage().build()).queue();
            });
            return;
        }
        NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
        NightShadeBot.getBot().getChannelHandler().getRoleLogChannel().sendMessage(this.getOutput(NightShadeBot.getBot().getGuild().getMember(targetMember), newRole).build()).queue();
        NightShadeBot.getBot().getGuild().getController().addRolesToMember(NightShadeBot.getBot().getGuild().getMember(targetMember), newRole).queue();
        targetMember.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("You have been promoted to " + newRole.getName() + "! Congratulations!").queue());
    }

    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public Role requiredRole() {
        return NightShadeBot.getBot().getGuild().getRolesByName("Administrator", false).get(0);
    }

    private EmbedBuilder getUsage() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Invalid Arguments!");
        usage.addField("Use the command like this!", Settings.PREFIX + "promote <@user> <new role>", false);
        usage.setColor(Color.RED);
        return usage;
    }

    private EmbedBuilder getOutput(final Member target, final Role newRole) {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle(":exclamation:   ***Staff Change***   :exclamation:");
        usage.addBlankField(false);
        usage.addField(":tada: **" + target.getEffectiveName() + "** has been promoted to **" + newRole.getName() + "** :tada:", "", false);
        usage.setColor(new Color(172, 0, 230));
        return usage;
    }
}
