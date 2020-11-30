package com.nightshadepvp.discord.cmd;

import com.nightshadepvp.discord.NightShadeBot;
import com.nightshadepvp.discord.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

/**
 * Created by Blok on 8/17/2018.
 */

public class SetPlayingCommand extends Command {
    
    private NightShadeBot nightShadeBot;

    public SetPlayingCommand(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    @Override
    public void run(final Member member, final String[] args, final MessageChannel channel, final Message message) {
        if (args == null || args.length == 0) {
            channel.sendMessage(this.getUsage().build()).queue();
            return;
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            builder.append(args[i]).append(" ");
        }
        final String playing = builder.toString().trim();
        nightShadeBot.getJda().getPresence().setActivity(Activity.playing(playing));
        channel.sendMessage("Done!").queue();
        return;
    }

    @Override
    public String getName() {
        return "setplaying";
    }

    @Override
    public Role requiredRole() {
        return nightShadeBot.getGuild().getRolesByName("Administrator", false).get(0);
    }

    private EmbedBuilder getUsage() {
        final EmbedBuilder usage = new EmbedBuilder();
        usage.setTitle("Invalid Arguments!");
        usage.addField("Use the command like this!", Settings.PREFIX + "setplaying <message>", true);
        usage.setColor(Color.RED);
        return usage;
    }
}