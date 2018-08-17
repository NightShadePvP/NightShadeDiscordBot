package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Blok on 8/17/2018.
 */
public class AddAllCommand extends Command {
    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        for (final Member m : NightShadeBot.getBot().getGuild().getMembers()) {
            if (!m.getRoles().contains(NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0))) {
                NightShadeBot.getBot().getGuild().getController().addRolesToMember(m, NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0)).queue();
            }
            if (m.getRoles().contains(NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0))) {
                continue;
            }
            NightShadeBot.getBot().getGuild().getController().addRolesToMember(m, NightShadeBot.getBot().getGuild().getRolesByName("NewGame", false).get(0)).queue();
        }
        channel.sendMessage("Done!").queue();
    }

    @Override
    public String getName() {
        return "addallgames";
    }

    @Override
    public Role requiredRole() {
        return NightShadeBot.getBot().getGuild().getRolesByName("Owner", false).get(0);
    }
}
