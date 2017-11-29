/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.items.armor;

import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author cedarrapidsboy
 */
public class ArmorFactory {

	/**
	 *
	 * @param armorValue
	 *            An integer representing the protection this item provides
	 * @param encumbrance
	 *            A double representing this item's weight
	 * @param condition
	 *            A double representing a percentage of the items durability
	 * @param durability
	 *            An int representing the durability of the item
	 * @return Boot
	 */
	public static Boot getBoot(int armorValue, double encumbrance, int condition, int durability) {
		return new Boot(armorValue, encumbrance, condition, durability);
	}

	/**
	 * Get a random boot with random stats armorValue 0-5 encumbrance 0.1-1.0
	 * durability 3-10
	 *
	 * @return Boot
	 */
	public static Boot getRandomBoot() {
		int randDurability = MathUtils.random(10, 20);
		int randArmor = MathUtils.random(1, 5);
		double randEncumbrance = MathUtils.random(0.1F, 1.0F);
		return new Boot(randArmor, randEncumbrance, randDurability, randDurability);
	}
}
