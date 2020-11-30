package com.nightshadepvp.discord;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nightshadepvp.discord.cmd.CommandHandler;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.apache.commons.lang3.Validate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Blok on 8/17/2018.
 */
public class NightShadeBot {
    private JDA jda;
    private CommandHandler handler;
    private ChannelHandler channelHandler;
    private Jedis jedisMain;
    private Jedis jedisPublish;
    private ScheduledExecutorService executorService;

    public NightShadeBot() throws LoginException, InterruptedException {
        final JDABuilder builder = JDABuilder.createDefault(Settings.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.playing("uhc1.nightshadepvp.com"));
        builder.setChunkingFilter(ChunkingFilter.NONE);
        this.jda = builder.build();
        jda.awaitReady();
        this.handler = new CommandHandler(this);
        this.channelHandler = new ChannelHandler(this);
        this.jda.addEventListener(new Listener(this));
        this.executorService = Executors.newScheduledThreadPool(5);
        this.setupJedis();
        ServerUtils.setRef(this);

        this.setupPunishmentController();
        System.out.println("NightShadeBot Setup Complete!");
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
        this.jedisMain = new Jedis("localhost");
        this.jedisPublish = new Jedis("localhost");
    }

    public Jedis getJedisMain() {
        if (this.jedisMain == null || !this.jedisMain.isConnected()) {
            this.jedisMain = new Jedis("localhost");
        }

        return this.jedisMain;
    }

    public Jedis getJedisPublish() {
        if (this.jedisPublish == null || !this.jedisPublish.isConnected()) {
            this.jedisPublish = new Jedis("localhost");
        }

        return this.jedisPublish;
    }

    public ScheduledExecutorService getExecutorService() {
        return this.executorService;
    }

    private void setupPunishmentController() {
        Jedis jedis = this.getJedisMain();
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                JsonParser jsonParser = new JsonParser();

                if (channel.equalsIgnoreCase("punishments")) {
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
                } else if (channel.equalsIgnoreCase("playerLink")) {
                    JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

                    Long id = jsonObject.get("discordID").getAsLong();
                    String rank = jsonObject.get("rank").getAsString();
                    String ign = jsonObject.get("username").getAsString();

                    System.out.println("Received Data:");
                    System.out.println("ID: " + id);
                    System.out.println("Rank: " + rank);
                    System.out.println("Username: " + ign);

                    Guild guild = getGuild();
                    User user = jda.getUserById(id);
                    Validate.notNull(user, "The user wasn't found!");
                    Member member = guild.getMember(user);
                    guild.addRoleToMember(member, ServerUtils.getRole(rank)).queue();
                    guild.addRoleToMember(member, ServerUtils.getRole("Linked")).queue();
                    user.openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage("Your Discord is now linked to the minecraft account: " + ign).queue();
                        privateChannel.sendMessage("If you want to unlink your account, open a ticket and ask staff the unlink your account.").queue();
                    });
                } else if (channel.equalsIgnoreCase("matches")) {
                    JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

                    getGuild().getRolesByName("NewGame", false).get(0).getManager().setMentionable(true).queue();
                    jda.getTextChannelById("593865007700377640").sendMessage(jda.getRolesByName("NewGame", false).get(0).getAsMention()).queue();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.GREEN);
                    builder.setTitle("**NightShadePvP UHC Match**");
                    builder.addField("**Host**", jsonObject.get("host").getAsString(), false);
                    //builder.addField("**Opening Time**", nightShadeMatch.getOpens() +" UTC", false);
                    builder.addField("**Scenarios**", jsonObject.get("scenarios").getAsString(), false);
                    builder.addField("**TeamSize**", jsonObject.get("teamsize").getAsString(), false);
                    builder.addField("**IP**", jsonObject.get("ip").getAsString(), false);
                    builder.addField("**MatchPost**", jsonObject.get("matchpost").getAsString(), true);
                    jda.getTextChannelById("593865007700377640").sendMessage(builder.build()).queue();
                    getGuild().getRolesByName("NewGame", false).get(0).getManager().setMentionable(false).queue();
                }else if (channel.equalsIgnoreCase("needspec")){
                    JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

                    String sender = jsonObject.get("player").getAsString();
                    int players = jsonObject.get("amount").getAsInt();
                    String server = jsonObject.get("server").getAsString();
                    MessageChannel messageChannel = getChannelHandler().getNeedSpecChannel();
                    messageChannel.sendMessage(ServerUtils.getRole("Trial").getAsMention() + ", " + ServerUtils.getRole("Staff").getAsMention() + ", " + ServerUtils.getRole("Senior").getAsMention() + ": " + sender + " needs specs on " + server + " with " + players + " players!").queue();
                }
            }
        }, "punishments", "playerLink", "matches", "needspec");
    }

    public CommandHandler getCommandHandler() {
        return handler;
    }
}
