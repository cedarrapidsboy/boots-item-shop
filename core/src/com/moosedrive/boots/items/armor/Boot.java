/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.items.armor;

import com.moosedrive.boots.items.ItemUtils;

/**
 * A single Boot. Mobs may have less than (or more than) two legs.
 *
 * @author cedarrapidsboy
 */
public class Boot extends ArmorItem {

	Boot(int armorValue, double encumbrance, int condition, int durability) {
		super(armorValue, encumbrance, condition, durability);
	}

	@Override
	public int getBasePrice() {
		return ItemUtils.BOOTS_COST;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Boot";
	}

}
