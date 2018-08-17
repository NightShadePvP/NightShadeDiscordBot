package com.nightshadepvp.discord.utils;

/**
 * Created by Blok on 8/17/2018.
 */
public class ParseUtils {
    public static boolean isBoolean(final String string) {
        try {
            Boolean.parseBoolean(string);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
