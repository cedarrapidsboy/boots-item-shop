/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.IItem;

/**
 *
 * @author 
 */
public class Monster extends Creature {
    
    protected List<IItem> inventory;

	protected Monster(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
        super(name, numLegs, numArms, numHeads, maxHealth);
    }

	/**
	 * A copy of the inventory list. If you are taking items, be sure to
	 * takeItemFromInventory() or clearInventory().
	 * 
	 * @return A copy of the inventory
	 */
	public List<IItem> getInventoryItems() {
		return new ArrayList<>(inventory);
	}

	/**
	 * Puts an item in the creature inventory. Will not add the same item twice.
	 * 
	 * @param item
	 * @return true if the item was added
	 */
	public boolean addItemToInventory(IItem item) {
		return (!inventory.contains(item) && inventory.add(item));
	}

	/**
	 * Removes an item from the creature inventory.
	 * 
	 * @param item
	 * @return true if the item was in the inventory
	 */
	public boolean removeItemFromInventory(IItem item) {
		return inventory.remove(item);
	}

	/**
	 * Empties the creature's inventory. Returns a copy of the inventory.
	 * @return A copy of the inventory list.
	 */
	public List<IItem> clearInventory() {
		List<IItem> list = getInventoryItems();
		inventory.clear();
		return list;
	}

	/**
	 * Take (steal) a random item from the inventory. 
	 * @return A random item from the inventory or null if empty inventory
	 */
	public IItem takeRandomItemFromInventory() {
		if (!inventory.isEmpty()) {
			return inventory.remove(MathUtils.random(inventory.size()-1));
		}
		return null;
	}
    
}
