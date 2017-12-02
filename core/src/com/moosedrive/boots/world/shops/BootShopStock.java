package com.moosedrive.boots.world.shops;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.moosedrive.boots.items.armor.Boot;

public class BootShopStock {
	private final Set<Boot> stock;
	private List<Boot> sortedByCost;
	private List<Boot> sortedByCondition;

	public BootShopStock() {
		stock = new HashSet<Boot>();
		sortedByCost = new ArrayList<Boot>();
		sortedByCondition = new ArrayList<Boot>();
	}

	/**
	 * This list is a copy. The boots in it are not. Removing or moving items in
	 * this list will not affect the original stock list. Avoid modifying the
	 * objects in this list.
	 * 
	 * @return a copied list of the stock (unsorted)
	 */
	public List<Boot> getStock() {
		synchronized (this) {
			return new ArrayList<Boot>(stock);
		}
	}

	/**
	 * This list is a copy. The boots in it are not. Removing or moving items in
	 * this list will not affect the original stock list. Avoid modifying the
	 * objects in this list.
	 * 
	 * @return A copied list of boots sorted
	 */
	public List<Boot> getStockByCost() {
		synchronized (this) {
			return new ArrayList<Boot>(sortedByCost);
		}
	}

	/**
	 * This list is a copy. The boots in it are not. Removing or moving items in
	 * this list will not affect the original stock list. Avoid modifying the
	 * objects in this list.
	 * 
	 * @return A copied list of boots sorted
	 */
	public List<Boot> getStockByCondition() {
		synchronized (this) {
			return new ArrayList<Boot>(sortedByCondition);
		}
	}

	/**
	 * Adds a boot to the stock. Returns false if the boot is already in the stock.
	 * 
	 * @param boot
	 * @return true if boot was added
	 */
	public boolean addBoot(Boot boot) {
		synchronized (this) {
			if (stock.add(boot)) {
				sortedByCost = stock.stream().sorted(new ArmorCostComparator().reversed()).collect(Collectors.toList());
				sortedByCondition = stock.stream().sorted(new ArmorConditionComparator().reversed())
						.collect(Collectors.toList());
				return true;
			}
			return false;
		}
	}

	/**
	 * Removes a boot from the stock. Returns false if the boot is not in the stock.
	 * 
	 * @param boot
	 * @return true if boot was found and removed
	 */
	public boolean removeBoot(Boot boot) {
		synchronized (this) {
			if (stock.remove(boot)) {
				sortedByCost = stock.stream().sorted(new ArmorCostComparator().reversed()).collect(Collectors.toList());
				sortedByCondition = stock.stream().sorted(new ArmorConditionComparator().reversed())
						.collect(Collectors.toList());
				return true;
			}
			return false;
		}
	}

	/**
	 * @return size of the stock
	 */
	public int size() {
		synchronized (this) {
			return stock.size();
		}
	}
}