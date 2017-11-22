/**
 * 
 */
package com.moosedrive.boots.items.potions;

import com.moosedrive.boots.items.IItem;
import com.moosedrive.boots.items.ItemUtils;

/**
 * @author Chad
 *
 */
public class HealthPotion implements IItem {

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.items.IItem#getCondition()
	 */
	@Override
	public int getCondition() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.items.IItem#getConditionPercent()
	 */
	@Override
	public double getConditionPercent() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.items.IItem#getEncumbrance()
	 */
	@Override
	public double getEncumbrance() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.items.IItem#getDurability()
	 */
	@Override
	public int getDurability() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.items.IItem#setCondition(int)
	 */
	@Override
	public void setCondition(int condition) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.moosedrive.boots.items.IItem#isBroken()
	 */
	@Override
	public boolean isBroken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBasePrice() {
		return ItemUtils.HEALTHPOTION_COST;
	}

}
