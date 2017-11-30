package com.moosedrive.boots.world.shops;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.moosedrive.boots.items.armor.Boot;

public class BootShopStock {
	private final class BootConditionComparator implements Comparator<Boot> {
		@Override
		public int compare(Boot o1, Boot o2) {

			return Integer.valueOf(o1.getCondition()).compareTo(o2.getCondition());
		}
	}

	private final class BootCostComparator implements Comparator<Boot> {
		@Override
		public int compare(Boot o1, Boot o2) {

			return Integer.valueOf(o1.getBasePrice()).compareTo(o2.getBasePrice());
		}
	}

	private final Set<Boot> stock;
	private List<Boot> sortedByCost;
	private List<Boot> sortedByCondition;

	public BootShopStock() {
		stock = new HashSet<Boot>();
		sortedByCost = new ArrayList<Boot>();
		sortedByCondition = new ArrayList<Boot>();
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
		sortedByCost = stock.stream().sorted(new BootCostComparator().reversed()).collect(Collectors.toList());
		sortedByCondition = stock.stream().sorted(new BootConditionComparator().reversed())
				.collect(Collectors.toList());

	}

	public void removeBoot(Boot boot) {
		stock.remove(boot);
		sortedByCost = stock.stream().sorted(new BootCostComparator().reversed()).collect(Collectors.toList());
		sortedByCondition = stock.stream().sorted(new BootConditionComparator().reversed())
				.collect(Collectors.toList());
	}

	public int size() {
		return stock.size();
	}
}