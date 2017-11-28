package com.moosedrive.boots.world.shops;

import com.moosedrive.boots.items.potions.HealthPotion;

public class PotionShop extends Shop {

	private PotionShop instance;

	private PotionShop(long money) {
		super(money);
	}
	
	
	public HealthPotion getHealthPotion(int size) {
		return new HealthPotion(size);
	}
	
	public Shop getInstance(long money) {
		if (this.instance == null) {
			this.instance = new PotionShop(money);
		}
		return this.instance;
	}

	@Override
	public String stockText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
