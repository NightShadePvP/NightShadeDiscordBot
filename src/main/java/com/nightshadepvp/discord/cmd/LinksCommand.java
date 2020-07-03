package com.nightshadepvp.discord.cmd;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;

/**
 * Created by Blok on 11/25/2019.
 */
public class LinksCommand extends Command {

    @Override
    public void run(Member member, String[] args, MessageChannel channel, Message message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("NightShadePvP | Useful Links");
        embedBuilder.setColor(new Color(172, 0, 230));
        embedBuilder.addField("", "» Website: http://www.nightshadepvp.com", false);
        embedBuilder.addField("", "» Discord: discord.nightshadepvp.com", false);
        embedBuilder.addField("", "» Twitter: @NightShadePvPMC", false);
        embedBuilder.addField("", "» Application: www.bit.ly/NS_Apply", false);
        embedBuilder.addBlankField(false);
        embedBuilder.addField("", "» Server IP: uhc1.nightshadepvp.com", false);
        embedBuilder.setAuthor("NightShadePvP");
        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "links";
    }

    @Override
    public Role requiredRole() {
        return null;
    }
}
