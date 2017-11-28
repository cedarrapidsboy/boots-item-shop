package com.moosedrive.boots.world.shops;

import java.util.ArrayList;
import java.util.List;

import com.moosedrive.boots.items.potions.HealthPotion;
import com.moosedrive.boots.items.potions.Potion;

public class PotionShop extends Shop {

	private static final int STARTING_FUNDS = 100;
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
