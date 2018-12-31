package com.nightshadepvp.discord;

/**
 * Created by Blok on 3/31/2018.
 */

public class Main {
    public static void main(final String[] args) {
        try {
            new NightShadeBot();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Setup complete!");
    }
}

