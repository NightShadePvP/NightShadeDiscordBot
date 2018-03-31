package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.Main;
import com.nightshadepvp.discord.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Blok on 3/31/2018.
 */
public class ReportBugCommand extends Command{

    private Main main;

    public ReportBugCommand(Main main) {
        this.main = main;
    }

    /***
     *  @param member Member who sent the message
     * @param args Arguments passed

     * @param channel Channel command was sent in

     * @param message Message object
     */
    @Override
    public void run(Member member, String[] args, MessageChannel channel, Message message) {
        if(args == null ||args.length == 0){
            channel.sendMessage(getUsage().build()).queue();
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            builder.append(args[i]).append(" ");
        }

        String bug = builder.toString().trim();

        channel.sendMessage(getSuccess(member).build()).queue();
        main.getChannelHandler().getBugsChannel().sendMessage(getToSend(bug, member).build()).queue();
    }

    @Override
    public String getName() {
        return "reportbug";
    }

    @Override
    public Role requiredRole() {
        return null;
    }

    private EmbedBuilder getUsage(){
        EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Invalid Arguments!");
        usage.addField("Use the command like this!", Settings.PREFIX + "reportbug <info>", true);
        usage.setColor(Color.RED);

        return usage;
    }

    private int getNextID(){
        File file = new File("bugs.txt");
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            return (int) b.lines().count() + 1;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private EmbedBuilder getSuccess(Member member){
        EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Success!");
        success.addField("", "Your bug has been submitted to the developers, " + member.getEffectiveName(), false);
        success.setColor(new Color(172, 0, 230));

        return success;
    }

    private EmbedBuilder getToSend(String bug, Member member){
        EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Bug Reported!");
        success.addField("", "A Bug Report has been submitted by " + member.getEffectiveName(), false);
        success.addBlankField(false);
        success.addField("", bug, false);
        success.setFooter("React with a check mark when the bug report is resolved!", null);
        success.setColor(new Color(172, 0, 230));

        return success;
    }
}
