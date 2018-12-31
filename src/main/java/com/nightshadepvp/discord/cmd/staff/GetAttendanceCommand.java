package com.nightshadepvp.discord.cmd.staff;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.cmd.Command;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Blok on 8/25/2018.
 */
public class GetAttendanceCommand extends Command {

    @Override
    public void run(Member member, String[] args, MessageChannel channel, Message message) {
        if(!member.getVoiceState().inVoiceChannel() || member.getVoiceState().getChannel().getIdLong() != NightShadeBot.getBot().getChannelHandler().getStaffMeetingChannel().getIdLong()){
            channel.sendMessage(message.getAuthor().getAsMention() + ", you must be in the staff meeting channel to do this command!").queue();
            return;
        }

        File file = new File("attendance.txt");
        if(file.exists()) file.mkdirs();
        new Thread(() -> {
            for (Member m : member.getVoiceState().getChannel().getMembers()){
                try {
                    FileUtils.write(file, m.getEffectiveName(), "UTF-8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();

        channel.sendMessage("Success!").queue();

    }

    @Override
    public String getName() {
        return "getattendance";
    }

    @Override
    public Role requiredRole() {
        return ServerUtils.getRole("Administrator");
    }
}
