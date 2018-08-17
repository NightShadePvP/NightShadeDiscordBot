package com.nightshadepvp.discord;

import com.nightshadepvp.discord.cmd.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import redis.clients.jedis.Jedis;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Blok on 8/17/2018.
 */
public class NightShadeBot {

    private JDA jda;
    private CommandHandler handler;
    private ChannelHandler channelHandler;
    private Jedis jedis;
    private ScheduledExecutorService executorService;

    private static NightShadeBot bot;

    public NightShadeBot() throws LoginException, InterruptedException {
        bot = this;
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Settings.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda = builder.buildBlocking();
        handler = new CommandHandler();
        channelHandler = new ChannelHandler(new Main());
        jda.addEventListener(new Listener(new Main()));
        executorService = Executors.newScheduledThreadPool(5);
        setupJedis();
        System.out.println("NightShadeBot Setup Complete!");
    }

    public static NightShadeBot getBot() {
        return bot;
    }

    public CommandHandler getCommandHandler() {
        return handler;
    }

    public  JDA getJda() {
        return jda;
    }

    public  Guild getGuild() {
        return getJda().getGuildById(140945203375636480L);
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }

    private void setupJedis(){
        jedis = new Jedis("localhost");
    }

    public Jedis getJedis(){
        if(jedis == null || !jedis.isConnected()){
            jedis = new Jedis("localhost");
        }

        return jedis;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
