package com.moosedrive.boots.world.shops;

/**
 * @author cebarne2
 *
 */
public abstract class Shop {

	protected long shopMoney;
	protected Shop instance;

	/**
	 * Create a new Shop instance with some starting funds
	 * @param money
	 */
	public Shop(long money) {
		this.shopMoney = money;
	}

	/**
	 * Return a summary of the current state of the Store
	 * @return  a summar
	 */
	public abstract String stockText();

	/**
	 * Reduce the shop's funds
	 * @param money
	 */
	public boolean removeShopMoney(long money) {
		if (this.getShopMoney() >= money) {
			this.shopMoney = this.getShopMoney() - money;
			return true;
		}
		return false;
	}

	/**
	 * Increase the shop's funds
	 * @param shopMoney
	 */
	public void addShopMoney(long shopMoney) {
		this.shopMoney = this.getShopMoney() + shopMoney;
	}

	
	/**
	 * @return Current shop funds
	 */
	public long getShopMoney() {
		return shopMoney;
	}

}