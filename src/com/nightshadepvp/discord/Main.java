package com.nightshadepvp.discord;

import com.nightshadepvp.discord.cmd.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;

import javax.security.auth.login.LoginException;

/**
 * Created by Blok on 3/31/2018.
 */
public class Main {

    private static JDA jda;
    private static CommandHandler handler;
    private static ChannelHandler channelHandler;

    public static void main(String[] args) throws LoginException, InterruptedException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Settings.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda = builder.buildBlocking();

        handler = new CommandHandler();
        channelHandler = new ChannelHandler(new Main());
        jda.addEventListener(new Listener(new Main()));
        System.out.println("Setup complete!");
    }

    public CommandHandler getCommandHandler() {
        return handler;
    }

    public static JDA getJda() {
        return jda;
    }

    public static Guild getGuid() {
        return getJda().getGuildById(140945203375636480L);
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }
}
