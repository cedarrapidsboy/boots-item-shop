/**
 * 
 */
package com.moosedrive.boots.items.potions;

import com.moosedrive.boots.items.ItemUtils;

/**
 * @author cedarrapidsboy
 *
 */
public class HealthPotion extends Potion {

	private int size;
	private boolean full;
	private final double encumbrance_multiplier = 0.0;
	private final double healing_multiplier = 100.0;
	
	public HealthPotion(int size) {
		super();
		this.full = true;
		this.size = size;
	}

	@Override
	public int getBasePrice() {
		return Math.round(ItemUtils.HEALTHPOTION_COST * (size + 1));
	}


	@Override
	public double getEncumbrance() {
		// This likely returns zero
		return (size + 1) * encumbrance_multiplier;
	}
	
	/**
	 * Empties the potion and returns a base healing.
	 * @return amount of healing to apply
	 */
	public long drinkPotion() {
		if (full) {
			full = false;
			return Math.round((size + 1) * healing_multiplier); 
		}
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Health Potion";
	}

}
