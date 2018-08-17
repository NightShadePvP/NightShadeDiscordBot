package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;

/**
 * Created by Blok on 8/17/2018.
 */
public class RoomCommand extends Command
{
    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (args == null || args.length == 0) {
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(this.getHelp().build()).queue());
            return;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(this.getHelp().build()).queue());
        }
    }

    @Override
    public String getName() {
        return "room";
    }

    @Override
    public Role requiredRole() {
        return null;
    }

    private EmbedBuilder getHelp() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Room Help");
        usage.addField("Use the command like this!", Settings.PREFIX + "reportbug <info>", true);
        usage.addField("**>room help***", "Shows this message", false);
        usage.addField("**>room create***", "Create a custom room where the following commands can be used to customize it even more", false);
        usage.addField("**>room limit #**", "Sets the number of users allowed in the room, with a maximum user limit of 10.** *Example !room limit 5.", false);
        usage.addField("**>room setname <name>**", "Will set the room name. Using 'Duo' or 'Squad' will also set size to 2 & 4 respectively. Example !room [insert person request room's name] room.", false);
        usage.addField("**>room kick <@user>**", "Will move a user out of your room. Replace @user with a real human on the server.", false);
        usage.addField("**>room invite <@user>**", "Allow players to join your room", false);
        usage.addField("**>room lock**", "Locks room, only allowing invited players to join", false);
        usage.addField("**>room unlock**", "Unlocks room, allowing all users to join", false);
        usage.setColor(Color.RED);
        return usage;
    }
}