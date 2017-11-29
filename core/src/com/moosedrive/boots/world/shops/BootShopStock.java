package com.moosedrive.boots.world.shops;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.moosedrive.boots.items.armor.Boot;

public class BootShopStock {
	private Set<Boot> stock;
	private TreeSet<Boot> sortedByCost;
	private TreeSet<Boot> sortedByCondition;

	public BootShopStock() {
		stock = new HashSet<Boot>();
		sortedByCost = new TreeSet<Boot>(new Comparator<Boot>() {
			@Override
			public int compare(Boot o1, Boot o2) {

				return Integer.valueOf(o1.getBasePrice()).compareTo(o2.getBasePrice());
			}
		}.reversed());

		sortedByCondition = new TreeSet<Boot>(new Comparator<Boot>() {
			@Override
			public int compare(Boot o1, Boot o2) {

				return Integer.valueOf(o1.getCondition()).compareTo(o2.getCondition());
			}
		}.reversed());
	}

	public List<Boot> getStock() {
		return new ArrayList<Boot>(stock);
	}

	public List<Boot> getStockByCost() {
		return new ArrayList<Boot>(sortedByCost);
	}

	public List<Boot> getStockByCondition() {
		return new ArrayList<Boot>(sortedByCondition);
	}

	public void addBoot(Boot boot) {
		stock.add(boot);
		sortedByCost.add(boot);
		sortedByCondition.add(boot);
	}

	public void removeBoot(Boot boot) {
		stock.remove(boot);
		sortedByCondition.remove(boot);
		sortedByCost.remove(boot);
	}
	public int size() {
		return stock.size();
	}
}