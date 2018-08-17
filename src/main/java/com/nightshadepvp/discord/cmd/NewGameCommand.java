package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;

/**
 * Created by Blok on 8/17/2018.
 */
public class NewGameCommand extends Command {
    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (member.getRoles().contains(NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0))) {
            NightShadeBot.getBot().getGuild().getController().removeRolesFromMember(member, NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0)).queue();
            channel.sendMessage(this.toggleOff(member).build()).queue();
        } else {
            NightShadeBot.getBot().getGuild().getController().addRolesToMember(member, NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0)).queue();
            channel.sendMessage(this.toggleOn(member).build()).queue();
        }
    }

    @Override
    public String getName() {
        return "newgame";
    }

    @Override
    public Role requiredRole() {
        return null;
    }

    private EmbedBuilder toggleOff(final Member member) {
        final EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Success!");
        success.addField("", "You will no longer be pinged for UHC Games, " + member.getEffectiveName(), false);
        success.setColor(Color.RED);
        return success;
    }

    private EmbedBuilder toggleOn(final Member member) {
        final EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Success!");
        success.addField("", "You will now be pinged for UHC Games, " + member.getEffectiveName(), false);
        success.setColor(new Color(172, 0, 230));
        return success;
    }
}
