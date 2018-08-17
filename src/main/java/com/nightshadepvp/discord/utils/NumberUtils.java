package com.nightshadepvp.discord.utils;

/**
 * Created by Blok on 8/17/2018.
 */
public class NumberUtils {
    public static boolean isInt(final String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

