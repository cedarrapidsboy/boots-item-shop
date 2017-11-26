package com.moosedrive.boots.items.containers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.IItem;

public class Container implements IContainer {

	private int maxItems;
	protected List<IItem> contents;
	private long money;

	public Container(int maxItems, long money) {
		this.maxItems = maxItems;
		this.setMoney(money);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moosedrive.boots.mobs.IContainer#getInventoryItems()
	 */
	@Override
	public List<IItem> getContents() {
		return new ArrayList<>(contents);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moosedrive.boots.mobs.IContainer#addItemToInventory(com.moosedrive.boots.
	 * items.IItem)
	 */
	@Override
	public boolean addItem(IItem item) {
		return ((contents.size() < maxItems) && !contents.contains(item) && contents.add(item));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moosedrive.boots.mobs.IContainer#removeItemFromInventory(com.moosedrive.
	 * boots.items.IItem)
	 */
	@Override
	public boolean removeItem(IItem item) {
		return contents.remove(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moosedrive.boots.mobs.IContainer#clearInventory()
	 */
	@Override
	public List<IItem> clear() {
		List<IItem> list = getContents();
		contents.clear();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moosedrive.boots.mobs.IContainer#takeRandomItemFromInventory()
	 */
	@Override
	public IItem takeRandomItem() {
		if (!contents.isEmpty()) {
			return contents.remove(MathUtils.random(contents.size() - 1));
		}
		return null;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

}
