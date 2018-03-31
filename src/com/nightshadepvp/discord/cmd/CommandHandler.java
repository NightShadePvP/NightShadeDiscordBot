package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Blok on 3/18/2018.
 */
public class CommandHandler {

    private  ArrayList<Command> commands;

    public CommandHandler() {
        commands = new ArrayList<>();
        registerCommands();
    }

    public void registerCommands(){
        registerCommand(new ReportBugCommand(new Main()));
    }

    private void registerCommand(Command command){
        this.commands.add(command);
    }


    public void handle(Command command, String[] args, MessageChannel channel, Member sender, Message message){

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("No Permission!");

        builder.setColor(Color.RED);


        for (Command c : this.commands){
            if(c.getName().equalsIgnoreCase(command.getName())){
                if(c.requiredRole() == null || sender.getRoles().contains(c.requiredRole())){
                    c.run(sender, args, channel, message);
                }else{
                    builder.addField("", "You don't have permission to do this command, " + sender.getEffectiveName(), false);
                    channel.sendMessage(builder.build()).queue();
                }

            }
        }
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
