package com.moosedrive.boots.world.shops;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.moosedrive.boots.items.armor.Boot;

public class BootShopStock {
	private final class BootConditionComparator implements Comparator<Boot> {
		@Override
		public int compare(Boot o1, Boot o2) {

			int comparison = Integer.valueOf(o1.getCondition()).compareTo(o2.getCondition());
			if (o1.equals(o2)) {
				return 0;
			} else if (comparison == 0) {
				//We've verified the objects are different BUT condition is the same, put this boot next in line
				comparison = 1;
			}
			return comparison;
		}
	}
	private final class BootCostComparator implements Comparator<Boot> {
		@Override
		public int compare(Boot o1, Boot o2) {

			int comparison = Integer.valueOf(o1.getBasePrice()).compareTo(o2.getBasePrice());
			if (o1.equals(o2)) {
				return 0;
			} else if (comparison == 0) {
				//We've verified the objects are different BUT cost is the same, put this boot next in line
				comparison = 1;
			}
			return comparison;
		}
	}
	private final Set<Boot> stock;
	private final TreeSet<Boot> sortedByCost;
	private final TreeSet<Boot> sortedByCondition;

	public BootShopStock() {
		stock = new HashSet<Boot>();
		sortedByCost = new TreeSet<Boot>(new BootCostComparator().reversed());
		sortedByCondition = new TreeSet<Boot>(new BootConditionComparator().reversed());
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
		sortedByCondition.add(boot);
		sortedByCost.add(boot);
		
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