/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.mobs.MobName;

/**
 *
 * @author cedarrapidsboy
 */
public class NameUtils {

	private static Map<Integer, List<String>> firstNameMap = new HashMap<Integer, List<String>>();
	private static Map<Integer, List<String>> lastNameMap = new HashMap<Integer, List<String>>();

	private NameUtils() {
	}

	public static void initializeNames() throws IOException {
		System.out.println("Initializing name cache...");
		firstNameMap.clear();
		lastNameMap.clear();
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
			List<String> names = reader.lines().map(String::trim).collect(Collectors.toList());
			firstNameMap.put(index, names);
			nameCount += names.size();
		}
		// verbs/lastnames
		files = namesDir.list((File dir, String name) -> name.matches("verbs_[0-9][0-9]"));
		fileIterator = Arrays.asList(files).listIterator();
		while (fileIterator.hasNext()) {
			FileHandle file = fileIterator.next();
			System.out.println("...reading file " + file.name());
			Integer index = Integer.valueOf(file.name().split("_")[1]);
			reader = new BufferedReader(file.reader());
			List<String> names = reader.lines().map(String::trim).collect(Collectors.toList());
			lastNameMap.put(index, names);
			nameCount += names.size();
		}
		System.out.println("...FINISHED initializing name cache. (" + nameCount + " names loaded)");

	}

	/**
	 * Gets a first name from the name files. Assumes the files have been read
	 * already. Returns the name "Uninitialized" if the name cache is empty (e.g.,
	 * the application hasn't already called initializeNames()).
	 *
	 * @param mobType
	 *            See MobConstants
	 * @return A first name
	 */
	public static String getRandomFirstName(int mobType) {
		int index = mobType;
		String name = "Uninitialized";
		if (!firstNameMap.containsKey(index)) {
			index = 0;
		}
		if (!firstNameMap.containsKey(index)) {
			index = -1;
		}
		if (index > -1) {
			List<String> names = firstNameMap.get(index);
			name = names.get(MathUtils.random(names.size() - 1));
		}
		return StringUtils.toTitleCase(name);
	}

	/**
	 * Gets a last name from the name files. Assumes the files have been read
	 * already. Returns the name "Uninitialized" if the name cache is empty (e.g.,
	 * the application hasn't already called initializeNames()).
	 *
	 * @param mobType
	 *            See MobConstants
	 * @return A last name
	 */
	public static String getRandomLastName(int mobType) {
		int index = mobType;
		String name = "Uninitialized";
		if (!lastNameMap.containsKey(index)) {
			index = 0;
		}
		if (!lastNameMap.containsKey(index)) {
			index = -1;
		}
		if (index > -1) {
			List<String> names = lastNameMap.get(index);
			name = names.get(MathUtils.random(names.size() - 1));
		}
		return StringUtils.toTitleCase(name);
	}

	public static MobName getSimpleName(String name, int mobType) {
		return new MobName("", "", name, "", mobType);
	}

}
