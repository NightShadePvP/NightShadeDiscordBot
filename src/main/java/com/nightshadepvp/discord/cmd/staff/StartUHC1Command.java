package com.nightshadepvp.discord.cmd.staff;

import com.nightshadepvp.discord.cmd.Command;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.io.File;
import java.io.IOException;

/**
 * Created by Blok on 9/19/2018.
 */
public class StartUHC1Command extends Command {
    @Override
    public void run(Member member, String[] args, MessageChannel channel, Message message) {
        new Thread(() -> {
            try {
                Runtime.getRuntime().exec("./start.sh", null, new File("/home/dhillon/minecraft/UHC"));
                channel.sendMessage(member.getAsMention() + ", Successfully issued the start command on UHC 1!").queue();
            } catch (IOException e) {
                channel.sendMessage(member.getAsMention() + ", there was a problem executing that command! Please consult an admin immediately!").queue();
            }
        }).run();
    }

    @Override
    public String getName() {
        return "startuhc1";
    }

    @Override
    public Role requiredRole() {
        return ServerUtils.getRole("Staff");
    }
}
