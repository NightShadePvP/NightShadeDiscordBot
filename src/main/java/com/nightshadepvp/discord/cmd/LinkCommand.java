package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.utils.ServerUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import redis.clients.jedis.Jedis;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 8/17/2018.
 */
public class LinkCommand extends Command {

    private NightShadeBot nightShadeBot;

    public LinkCommand(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (ServerUtils.userHasRole("Linked", member)) {
            channel.sendMessage(this.getPermError().build()).queue();
            return;
        }

        Jedis jedisPub = nightShadeBot.getJedisPublish();

        if (jedisPub.get(member.getUser().getId()) != null) {
            channel.sendMessage(getCodeAlreadyUsed().build()).queue();
            return;
        }

        channel.sendMessage(this.getSuccess().build()).queue();
        final String code = Long.toHexString(Double.doubleToLongBits(ThreadLocalRandom.current().nextDouble()));
        member.getUser().openPrivateChannel().queue(privateChannel -> {
            privateChannel.sendMessage("Hey there! To link your Discord to your in game client on NightShade, perform the following command in game:").queue();
            privateChannel.sendMessage("/linkdiscord " + code).queue();
            privateChannel.sendMessage("Your code will be valid for 10 minutes").queue();
        });
        jedisPub.set(code, member.getUser().getId());
        jedisPub.expire(member.getUser().getId(), 600);
    }

    @Override
    public String getName() {
        return "link";
    }

    @Override
    public Role requiredRole() {
        return null;
    }

    private EmbedBuilder getPermError() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Error!");
        usage.addField("You have already linked your account!", "If you would like to link your account to a different account, please ask an admin to unlink your current account.", true);
        usage.setColor(Color.RED);
        return usage;
    }

    private EmbedBuilder getCodeAlreadyUsed() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Error!");
        usage.addField("You recently sent a code!", "You have already been sent a link code! Make sure to check your Discord DM's", true);
        usage.setColor(Color.RED);
        return usage;
    }

    private EmbedBuilder getSuccess() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Success");
        usage.addField("Please check your private messages", "A confirmation code has been sent to your private messages on Discord", true);
        usage.setColor(new Color(172, 0, 230));
        return usage;
    }
}
