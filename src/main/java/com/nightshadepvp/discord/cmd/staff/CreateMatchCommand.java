package com.nightshadepvp.discord.cmd.staff;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.cmd.Command;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.core.entities.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Blok on 8/20/2018.
 */
public class CreateMatchCommand extends Command {

    @Override
    public void run(Member member, String[] args, MessageChannel channel, Message message) {

        if(NightShadeBot.getBot().getJda().getTextChannelsByName(member.getEffectiveName() + "-match", false) != null){
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("You already have an open match channel!").queue();
            });
            NightShadeBot.getBot().getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            return;
        }

        //Made sure they don't already have a match channel

        //---------------------
        // Open Channel
        //---------------------

        String name = member.getUser().getName() + "-match";
        NightShadeBot.getBot().getChannelHandler().getStaffCategory().createTextChannel(name).queue();
        TextChannel textChannel = NightShadeBot.getBot().getJda().getTextChannelsByName(name, true).get(0);

    }

    @Override
    public String getName() {
        return "creatematch";
    }

    @Override
    public Role requiredRole() {
        return ServerUtils.getRole("Trial Host");
    }
}
