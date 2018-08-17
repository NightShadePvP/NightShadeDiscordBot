package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.utils.FileUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by Blok on 8/17/2018.
 */
public class GetLogsCommand extends Command
{
    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        String f = format.format(date);

        CompletableFuture.supplyAsync(() -> {
            List<File> found;
            List<File> uhc2;
            found = FileUtils.getFiles(new File("/home/dhillon/minecraft/UHC/logs"), false).stream().filter(file -> file.getName().contains(f)).collect(Collectors.toList());
            member.getUser().openPrivateChannel().queue(privateChannel -> found.forEach(file -> privateChannel.sendFile(file).queue()));
            member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("UHC 2 Logs:").queue());
            uhc2 = FileUtils.getFiles(new File("/home/dhillon/minecraft/UHC/logs"), false).stream().filter(file -> file.getName().contains(f)).collect(Collectors.toList());
            member.getUser().openPrivateChannel().queue(privateChannel -> uhc2.forEach(file -> privateChannel.sendFile(file).queue()));
            return null;
        }).thenAccept(o -> channel.sendMessage(this.getSuccess(member).build()).queue());
    }

    @Override
    public String getName() {
        return "getlogs";
    }

    @Override
    public Role requiredRole() {
        return NightShadeBot.getBot().getGuild().getRolesByName("Senior", false).get(0);
    }

    private EmbedBuilder getSuccess(final Member member) {
        final EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Success!");
        success.addField("", "You have been sent today's logs, " + member.getEffectiveName(), false);
        success.setColor(new Color(172, 0, 230));
        return success;
    }
}
