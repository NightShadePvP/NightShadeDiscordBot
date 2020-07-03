package com.nightshadepvp.discord.cmd.staff;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by Blok on 8/20/2018.
 */
public class WhitelistCommand extends Command {
    
    private NightShadeBot nightShadeBot;

    public WhitelistCommand(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    private HashSet<String> whitelistedPlayers = new HashSet<>();

    @Override
    public void run(Member member, String[] args, MessageChannel channel, Message message) {
        //>whitelist name
        if (args.length != 2) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("Please specify a singular user in #pre-whitelist").queue();
            });
            return;
        }

        if (!nightShadeBot.getChannelHandler().getPreWhitelistChannel().getId().equalsIgnoreCase(channel.getId())) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("Please only execute this command in #pre-whitelist!").queue();
            });
            return;
        }

        String player = args[0];

        if (whitelistedPlayers.contains(player)) {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("You have already requested that account to be pre-whitelisted!").queue();
            });
            return;
        }

        String server = args[1];
        if (server.equalsIgnoreCase("UHC1") || server.equalsIgnoreCase("UHC2")) {
            whitelistedPlayers.add(player);
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            try {
                Runtime.getRuntime().exec("screen -S " + server.toLowerCase() + "-X stuff ewl add " + player + "^M");
            } catch (IOException e) {
                nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
                member.getUser().openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("There was a problem whitelisting your account!").queue();
                });
                e.printStackTrace();
                return;
            }
            nightShadeBot.getChannelHandler().getStaffLogChannel().sendMessage(getToSend(player, server, member).build()).queue();


        } else {
            nightShadeBot.getExecutorService().schedule(() -> message.delete().queue(), 1L, TimeUnit.SECONDS);
            member.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("The server must be UHC1 or UHC2!").queue();
            });
        }
    }

    @Override
    public String getName() {
        return "whitelist";
    }

    @Override
    public Role requiredRole() {
        return null;
    }

    private EmbedBuilder getToSend(final String player, String server, final Member sender) {
        final EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Player Whitelisted");
        success.addField("", player + "has been whitelisted on " + server + "(" + sender.getEffectiveName() + ")", false);
        success.addBlankField(false);
        success.setColor(new Color(172, 0, 230));
        return success;
    }
}
