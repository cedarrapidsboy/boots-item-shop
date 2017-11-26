package com.moosedrive.boots.items.containers;

import java.util.List;

import com.moosedrive.boots.items.IItem;

public interface IContainer {

	/**
	 * A copy of the inventory list. If you are taking items, be sure to
	 * takeItemFromInventory() or clearInventory().
	 * 
	 * @return A copy of the inventory
	 */
	List<IItem> getContents();

	/**
	 * Puts an item in the inventory. Will not add the same item twice.
	 * 
	 * @param item
	 * @return true if the item was added
	 */
	boolean addItem(IItem item);

	/**
	 * Removes an item from the inventory.
	 * 
	 * @param item
	 * @return true if the item was in the inventory
	 */
	boolean removeItem(IItem item);

	/**
	 * Empties the inventory. Returns a copy of the inventory.
	 * 
	 * @return A copy of the inventory list.
	 */
	List<IItem> clear();

	/**
	 * Take (steal) a random item from the inventory.
	 * 
	 * @return A random item from the inventory or null if empty inventory
	 */
	IItem takeRandomItem();

}