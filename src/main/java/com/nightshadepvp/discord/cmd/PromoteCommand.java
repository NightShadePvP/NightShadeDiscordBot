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
    
    private NightShadeBot nightShadeBot;

    public PromoteCommand(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (args.length != 2) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(this.getUsage().build()).queue());
            return;
        }
        final User targetMember = message.getMentionedUsers().get(0);
        if (targetMember == null) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("You must specify a user to promote!").queue();
                privateChannel.sendMessage(this.getUsage().build()).queue();
            });
            return;
        }
        if (nightShadeBot.getGuild().getRolesByName(args[1], true).get(0) == null) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("That role couldn't be found!").queue());
            return;
        }
        final Role newRole = nightShadeBot.getGuild().getRolesByName(args[1], true).get(0);
        if (nightShadeBot.getGuild().getMember(targetMember).getRoles().contains(newRole)) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage(targetMember.getName() + " already has the " + newRole.getName() + " role!").queue();
                privateChannel.sendMessage(this.getUsage().build()).queue();
            });
            return;
        }
        nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
        nightShadeBot.getChannelHandler().getRoleLogChannel().sendMessage(this.getOutput(nightShadeBot.getGuild().getMember(targetMember), newRole).build()).queue();
        nightShadeBot.getGuild().getController().addRolesToMember(nightShadeBot.getGuild().getMember(targetMember), newRole).queue();
        targetMember.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("You have been promoted to " + newRole.getName() + "! Congratulations!").queue());
    }

    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public Role requiredRole() {
        return nightShadeBot.getGuild().getRolesByName("Administrator", false).get(0);
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
