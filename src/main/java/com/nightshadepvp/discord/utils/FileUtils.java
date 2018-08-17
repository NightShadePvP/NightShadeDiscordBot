package com.nightshadepvp.discord.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Blok on 8/17/2018.
 */
public class FileUtils {
    public static List<File> getFiles(final File folder, final boolean folders) {
        if (folder == null || !folder.isDirectory()) {
            return Collections.emptyList();
        }
        final ArrayList<File> files = new ArrayList<File>();
        final File[] listFiles = folder.listFiles();
        for (int i = 0; i < listFiles.length; ++i) {
            if (listFiles[i].isDirectory()) {
                if (folders) {
                    files.add(listFiles[i]);
                }
            } else {
                files.add(listFiles[i]);
            }
        }
        return files;
    }
}

