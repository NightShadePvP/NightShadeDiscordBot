package com.nightshadepvp.discord;

import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * Created by Blok on 3/31/2018.
 */
public class ChannelHandler {

    private Main main;

    public ChannelHandler(Main main) {
        this.main = main;
    }

    public MessageChannel getBugsChannel(){
        return Main.getJda().getTextChannelById(429732109121224724L);
    }

}
