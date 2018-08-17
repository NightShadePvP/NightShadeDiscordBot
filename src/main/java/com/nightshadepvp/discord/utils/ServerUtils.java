package com.nightshadepvp.discord.utils;

import com.nightshadepvp.discord.NightShadeBot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Blok on 8/17/2018.
 */
public class ServerUtils {

    public static boolean userHasRole(String role, Member member){
        Role r = NightShadeBot.getBot().getGuild().getRolesByName(role, true).get(0);
        return member.getRoles().contains(r);
    }

}
