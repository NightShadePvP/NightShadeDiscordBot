package com.nightshadepvp.discord.utils;

import com.nightshadepvp.discord.NightShadeBot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Blok on 8/17/2018.
 */
public class ServerUtils {

    private static NightShadeBot ref;

    public static void setRef(NightShadeBot ref) {
        ServerUtils.ref = ref;
    }

    public static boolean userHasRole(String role, Member member){
        Role r = ref.getGuild().getRolesByName(role, true).get(0);
        return member.getRoles().contains(r);
    }

    public static Role getRole(String role){
        return ref.getGuild().getRolesByName(role, true).get(0);
    }

}
