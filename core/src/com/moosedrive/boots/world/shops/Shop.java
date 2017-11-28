package com.moosedrive.boots.world.shops;

public abstract class Shop {

	protected long shopMoney;
	protected Shop instance;

	public Shop(long money) {
		this.shopMoney = money;
	}

	public abstract String stockText();

	public void removeShopMoney(long money) {
		this.shopMoney = this.getShopMoney() - money;
	}

	public void addShopMoney(long shopMoney) {
		this.shopMoney = this.getShopMoney() + shopMoney;
	}

	
	public long getShopMoney() {
		return shopMoney;
	}

}