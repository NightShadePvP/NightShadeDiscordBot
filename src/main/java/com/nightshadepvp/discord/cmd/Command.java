package com.nightshadepvp.discord.cmd;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Blok on 3/18/2018.
 */
public abstract class Command {
    public abstract void run(Member member, String[] args, MessageChannel channel, Message message);

    public abstract String getName();

    public abstract Role requiredRole();
}
