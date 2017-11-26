/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.items.armor;

import com.moosedrive.boots.items.IBreakableItem;

/**
 *
 * @author cedarrapidsboy
 */
public interface IArmorItem extends IBreakableItem {

	/**
	 * A value representing the protection this armorValue provides a mob
	 *
	 * @return value of current armorValue
	 */
	int getArmorValue();
}
