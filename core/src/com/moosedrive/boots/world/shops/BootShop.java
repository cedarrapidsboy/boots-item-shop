/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world.shops;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.armor.Boot;

/**
 *
 * @author cedarrapidsboy
 */
public class BootShop {

	private static BootShop thisShop;
	private long shopMoney;
	public void removeShopMoney(long money) {
		this.shopMoney = this.getShopMoney() - money;
	}

	public void addShopMoney(long shopMoney) {
		this.shopMoney = this.getShopMoney() + shopMoney;
	}

	private Set<Boot> stock;

	public static final float ARMOR_MARKUP = 0.20F;
	public static final long STARTING_FUNDS = 100;
	public static final int BASE_BOOT_COST = 10;
	public static final float BOOT_CONDITION_MULTIPLIER = 0.5F;

	/**
	 * @param boot
	 * @return Cost of the boot after condition calculation and markup
	 */
	public static int getAftermarketBootCost(Boot boot) {
		return MathUtils.round(getBootCost(boot) * (1 + ARMOR_MARKUP));
	}

	public static int getBootCost(Boot boot) {
		return MathUtils.round(BASE_BOOT_COST  + (boot.getCondition() * BOOT_CONDITION_MULTIPLIER));
	}

	/**
	 * @return A (copy) list of boots sorted by highest value
	 */
	public List<Boot> viewBootsByCost() {
		return stock.stream().filter(i -> i instanceof Boot)
				.sorted(Comparator.comparingInt(b -> getBootCost((Boot)b)).reversed()).collect(Collectors.toList());
	}

	private BootShop() {
		stock = new HashSet<Boot>();
		shopMoney = STARTING_FUNDS;
	}

	public boolean addBoot(Boot boot) {
		return this.stock.add(boot);
	}

	public synchronized boolean takeBoot(Boot boot) {
		return stock.remove(boot);
	}

	/**
	 *
	 * @return Number of items in stock
	 */
	public int count() {
		return stock.size();
	}

	public void addBoot() {
		stock.add(ArmorFactory.getRandomBoot());
		System.out.println("Boot stock: " + stock.size());

	}

	public static BootShop getInstance() {
		if (thisShop == null) {
			thisShop = new BootShop();
		}
		return thisShop;
	}

	public String stockText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Boots: ");
		sb.append(String.valueOf(count()));
		sb.append(System.getProperty("line.separator"));
		sb.append("Money: ");
		sb.append(getShopMoney());
		return sb.toString();
	}

	public long getShopMoney() {
		return shopMoney;
	}

}
