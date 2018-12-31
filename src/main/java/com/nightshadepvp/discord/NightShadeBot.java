package com.nightshadepvp.discord;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nightshadepvp.discord.cmd.CommandHandler;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Guild;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Blok on 8/17/2018.
 */
public class NightShadeBot
{
    private JDA jda;
    private CommandHandler handler;
    private ChannelHandler channelHandler;
    private Jedis jedis;
    private ScheduledExecutorService executorService;
    private static NightShadeBot bot;

    public NightShadeBot() throws LoginException, InterruptedException {
        NightShadeBot.bot = this;
        final JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Settings.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        this.jda = builder.buildBlocking();
        this.handler = new CommandHandler();
        this.channelHandler = new ChannelHandler();
        this.jda.addEventListener(new Listener());
        this.executorService = Executors.newScheduledThreadPool(5);
        this.setupJedis();

        this.setupPunishmentController();
        System.out.println("NightShadeBot Setup Complete!");
    }

    public static NightShadeBot getBot() {
        return NightShadeBot.bot;
    }

    public CommandHandler getCommandHandler() {
        return this.handler;
    }

    public JDA getJda() {
        return this.jda;
    }

    public Guild getGuild() {
        return this.getJda().getGuildById(140945203375636480L);
    }

    public ChannelHandler getChannelHandler() {
        return this.channelHandler;
    }

    private void setupJedis() {
        this.jedis = new Jedis("localhost");
    }

    public Jedis getJedis() {
        if (this.jedis == null || !this.jedis.isConnected()) {
            this.jedis = new Jedis("localhost");
        }
        return this.jedis;
    }

    public ScheduledExecutorService getExecutorService() {
        return this.executorService;
    }

    private void setupPunishmentController() {
        Jedis jedis = getJedis();
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {

                if (!channel.equalsIgnoreCase("punishments")) {
                    return;
                }

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(new java.awt.Color(172, 0, 230));
                embedBuilder.setTitle("New Punishment!");
                embedBuilder.addField("Type:", jsonObject.get("type").getAsString(), false);
                embedBuilder.addField("Player:", jsonObject.get("player").getAsString(), false);
                embedBuilder.addField("Staff Member:", jsonObject.get("punisher").getAsString(), false);
                embedBuilder.addField("Reason: ", jsonObject.get("reason").getAsString(), false);
                embedBuilder.addField("Length: ", jsonObject.get("length").getAsString(), false);

                getChannelHandler().getStaffLogChannel().sendMessage(embedBuilder.build()).queue();
            }
        }, "punishments");
    }
}
