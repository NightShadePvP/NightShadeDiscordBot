package com.nightshadepvp.discord;

import com.nightshadepvp.discord.cmd.Command;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Created by Blok on 3/31/2018.
 */
public class Listener extends ListenerAdapter{

    private Main main;

    public Listener(Main main) {
        this.main = main;
    }

    public void onReady(ReadyEvent e){
        System.out.println("Bot is ready!");
    }

    public void onMessageReceived(MessageReceivedEvent e){

        String content = e.getMessage().getContentRaw().trim();

        if(content.startsWith(Settings.PREFIX)){
            ArrayList<Command> cmds = getMain().getCommandHandler().getCommands();
            if(cmds.stream().anyMatch(command -> content.startsWith(Settings.PREFIX + command.getName()))){
                Command cmd = cmds.stream().filter(command -> content.startsWith(Settings.PREFIX + command.getName())).findFirst().get();
                String[] args = content.split(" ");
                String[] toReturn;

                if(args == null || args.length == 0){
                    getMain().getCommandHandler().handle(cmd, null, e.getChannel(), e.getMember(), e.getMessage());
                }else{
                    ArrayList<String> arguements = new ArrayList<>();
                    for (String s : args){
                        if(s != null && s.length() > 0){
                            arguements.add(s);
                        }
                    }

                    arguements.remove( 0);
                    toReturn = arguements.toArray(new String[arguements.size()]);


                    getMain().getCommandHandler().handle(cmd, toReturn, e.getChannel(), e.getMember(), e.getMessage());
                }


            }
        }
    }

    public Main getMain() {
        return main;
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e){

        if(e.getUser().isFake() || e.getUser().isBot()) return;

        if(e.getChannel().getIdLong() == main.getChannelHandler().getBugsChannel().getIdLong() || e.getChannel().getIdLong() == main.getChannelHandler().getSuggestionsChannel().getIdLong()){
            if(e.getReactionEmote().getName().equalsIgnoreCase(":white_check_mark:") || e.getReactionEmote().getName().equalsIgnoreCase("✅")){
                e.getChannel().deleteMessageById(e.getMessageIdLong()).queue();
            }
        }
    }
}
