package com.nightshadepvp.discord;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 * Created by Blok on 3/31/2018.
 */
public class ChannelHandler {


    public MessageChannel getBugsChannel() {
        return NightShadeBot.getBot().getJda().getTextChannelById(429732109121224724L);
    }

    public MessageChannel getSuggestionsChannel() {
        return NightShadeBot.getBot().getJda().getTextChannelById(429732129707130883L);
    }

    public MessageChannel getPreWhitelistChannel() {
        return NightShadeBot.getBot().getJda().getTextChannelById(462076542756061214L);
    }

    public MessageChannel getRoleLogChannel() {
        return NightShadeBot.getBot().getJda().getTextChannelById(352975356531179522L);
    }

    public Category getStaffCategory(){
        return NightShadeBot.getBot().getJda().getCategoryById(373145114685734933L);
    }

    public MessageChannel getStaffLogChannel(){
        return NightShadeBot.getBot().getJda().getTextChannelById(421097207689641984L);
    }

    public VoiceChannel getStaffMeetingChannel(){
        return NightShadeBot.getBot().getJda().getVoiceChannelById(483037056675741712L);
    }

}

