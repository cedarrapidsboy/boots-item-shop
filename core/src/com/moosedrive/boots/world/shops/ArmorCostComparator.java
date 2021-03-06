package com.moosedrive.boots.world.shops;

import java.util.Comparator;

import com.moosedrive.boots.items.armor.ArmorItem;

public class ArmorCostComparator implements Comparator<ArmorItem> {
	@Override
	public int compare(ArmorItem o1, ArmorItem o2) {

		return Integer.valueOf(o1.getBasePrice()).compareTo(o2.getBasePrice());
	}
}