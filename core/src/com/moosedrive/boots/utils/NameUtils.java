/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author cedarrapidsboy
 */
public class NameUtils {

    private static Map<Integer, List<String>> firstNameMap = new HashMap();

    private NameUtils() {
    }

    public static void initializeNames() throws IOException {
        System.out.println("Initializing name cache...");
        firstNameMap.clear();
        long nameCount = 0;
        FileHandle namesDir = Gdx.files.internal("data/names");
        FileHandle[] files = namesDir.list((File dir, String name) -> name.matches("names_[0-9][0-9]"));
        Iterator<FileHandle> fileIterator = Arrays.asList(files).listIterator();
        BufferedReader reader;
        while (fileIterator.hasNext()) {
            FileHandle file = fileIterator.next();
            System.out.println("...reading file " + file.name());
            Integer index = Integer.valueOf(file.name().split("_")[1]);
            reader = new BufferedReader(file.reader());
            List names = reader.lines().collect(Collectors.toList());
            firstNameMap.put(index, names);
            nameCount += names.size();
        }
        System.out.println("...FINISHED initializing name cache. (" + nameCount + " names loaded)");

    }

    /**
     * Gets a first name from the name files. Assumes the files have been read
     * already. Returns the name "Uninitialized" if the name cache is empty
     * (e.g., the application hasn't already called initializeNames()).
     *
     * @param mobType See MobConstants
     * @return A first name
     */
    public static String getRandomFirstName(int mobType) {
        int index = mobType;
        String name = "Uninitialized";
        if (!firstNameMap.containsKey(index)) {
            index = 0;
        } else if (!firstNameMap.containsKey(index)) {
            index = -1;
        }
        if (index > -1) {
            List<String> names = firstNameMap.get(index);
            name = names.get(MathUtils.random(names.size() - 1));
        }
        return name;
    }

}
