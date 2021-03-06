/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world.shops;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.armor.Boot;

/**
 *
 * @author cedarrapidsboy
 */
public class BootShop extends Shop {

	private static BootShop thisShop;
	long shopMoney;
	private BootShopStock stock = new BootShopStock();
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
		return stock.getStockByCost();
	}

	private BootShop(long money) {
		super(money);
		stock = new BootShopStock();
	}

	public void addBoot(Boot boot) {
		this.stock.addBoot(boot);
	}

	public void takeBoot(Boot boot) {
		this.stock.removeBoot(boot);
	}

	/**
	 *
	 * @return Number of items in stock
	 */
	public int count() {
		return stock.size();
	}

	public void addBoot() {
		stock.addBoot(ArmorFactory.getRandomBoot());

	}

	public static BootShop getInstance() {
		if (thisShop == null) {
			thisShop = new BootShop(STARTING_FUNDS);
		}
		return thisShop;
	}

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.world.shops.IShop#stockText()
	 */
	@Override
	public String stockText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Boots: ");
		sb.append(String.valueOf(count()));
		sb.append(System.getProperty("line.separator"));
		sb.append("Money: ");
		sb.append(getShopMoney());
		return sb.toString();
	}

}
