package com.nightshadepvp.discord;


import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 * Created by Blok on 3/31/2018.
 */
public class ChannelHandler {

    private NightShadeBot nightShadeBot;

    public ChannelHandler(NightShadeBot nightShadeBot) {
        this.nightShadeBot = nightShadeBot;
    }

    public MessageChannel getBugsChannel() {
        return nightShadeBot.getJda().getTextChannelById(429732109121224724L);
    }

    public MessageChannel getSuggestionsChannel() {
        return nightShadeBot.getJda().getTextChannelById(429732129707130883L);
    }

    public MessageChannel getPreWhitelistChannel() {
        return nightShadeBot.getJda().getTextChannelById(462076542756061214L);
    }

    public MessageChannel getRoleLogChannel() {
        return nightShadeBot.getJda().getTextChannelById(352975356531179522L);
    }

    public Category getStaffCategory(){
        return nightShadeBot.getJda().getCategoryById(373145114685734933L);
    }

    public MessageChannel getStaffLogChannel(){
        return nightShadeBot.getJda().getTextChannelById(421097207689641984L);
    }

    public VoiceChannel getStaffMeetingChannel(){
        return nightShadeBot.getJda().getVoiceChannelById(483037056675741712L);
    }

    public MessageChannel getStaffChatChannel(){
        return nightShadeBot.getJda().getTextChannelById(291676932930797570L);
    }

    public MessageChannel getNeedSpecChannel(){
        return nightShadeBot.getJda().getTextChannelById(744363715847258222L);
    }

}

