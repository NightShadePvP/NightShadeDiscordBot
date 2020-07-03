package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Blok on 8/17/2018.
 */
public class AddAllCommand extends Command {
    
    private NightShadeBot nightShadeBot;

    public AddAllCommand(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        for (final Member m : nightShadeBot.getGuild().getMembers()) {
            if (!m.getRoles().contains(nightShadeBot.getGuild().getRolesByName("NewGame", false).get(0))) {
                nightShadeBot.getGuild().getController().addRolesToMember(m, nightShadeBot.getGuild().getRolesByName("NewGame", false).get(0)).queue();
            }
            if (m.getRoles().contains(nightShadeBot.getGuild().getRolesByName("NewGame", false).get(0))) {
                continue;
            }
            nightShadeBot.getGuild().getController().addRolesToMember(m, nightShadeBot.getGuild().getRolesByName("NewGame", false).get(0)).queue();
        }
        channel.sendMessage("Done!").queue();
    }

    @Override
    public String getName() {
        return "addallgames";
    }

    @Override
    public Role requiredRole() {
        return ServerUtils.getRole("Owner");
    }
}
