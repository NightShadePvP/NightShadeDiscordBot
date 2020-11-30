package com.nightshadepvp.discord.cmd;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;

/**
 * Created by Blok on 3/18/2018.
 */
public abstract class Command {
    public abstract void run(Member member, String[] args, MessageChannel channel, Message message);

    public abstract String getName();

    public abstract Role requiredRole();
}
