package com.moosedrive.boots.items.containers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.moosedrive.boots.items.IItem;

public class ContainerUtils {

	private ContainerUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String inventorySummary(List<IItem> inventory) {
		HashMap<String,Long> itemCounts = new HashMap<String,Long>();
		for(IItem item : inventory) {
		    Long i = itemCounts.get(item.getName());
		    if(i == null) itemCounts.put(item.getName(), Long.valueOf(1));
		    else itemCounts.put(item.getName(), i + 1);
		}
		return itemCounts
				.entrySet()
				.stream()
				.map(e -> (String)String.join("", e.getKey(), " (" + e.getValue().toString() + ")"))
				.collect(Collectors.joining(", "));
	}

}
