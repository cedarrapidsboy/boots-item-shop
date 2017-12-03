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
import com.moosedrive.boots.items.containers.IContainer;
import com.moosedrive.boots.world.WorldTile;

/**
 *
 * @author cedarrapidsboy
 */
public abstract class Creature implements IContainer {

	protected MobName name;
	protected final int mobType;
	protected int numLegs;
	protected final int maxLegs;
	protected int numArms;
	protected final int maxArms;
	protected int numHeads;
	protected final int maxHeads;
	protected long curHealth;
	private int strength;
	private WorldTile location;
	
	public WorldTile getLocation() {
		return location;
	}

	public void setLocation(WorldTile location) {
		this.location = location;
	}

	public final static int CAPACITY_STRENGTH_MULTIPLIER = 10;
	
	

	protected void setCurHealth(long curHealth) {
		this.curHealth = curHealth;
	}

	protected final int maxHealth;
	protected long money;
	protected List<IItem> inventory;
	private int baseDamage;

	public Creature(MobName name, int numLegs, int numArms, int numHeads, int maxHealth, int baseDamage, int strength, WorldTile loc) {
		this.maxLegs = numLegs;
		this.numLegs = this.maxLegs;
		this.maxArms = numArms;
		this.numArms = this.maxArms;
		this.maxHeads = numHeads;
		this.numHeads = this.maxHeads;
		this.maxHealth = maxHealth;
		this.curHealth = this.maxHealth;
		this.name = name;
		this.mobType = name.type();
		this.money = 0;
		this.inventory = new ArrayList<IItem>();
		this.baseDamage = baseDamage;
		this.strength = strength;
		this.location = loc;
	}

	public MobName name() {
		return this.name;
	}

	public int getNumLegs() {
		return numLegs;
	}

	public int getNumArms() {
		return numArms;
	}

	public int getNumHeads() {
		return numHeads;
	}

	public long getCurHealth() {
		return curHealth;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	/**
	 * Adds a leg to the mob. Cannot add more than maxLegs
	 *
	 * @return True if a leg could be added
	 */
	public boolean addLeg() {
		if (this.numLegs < this.maxLegs) {
			adjustNumberOfLegs(1);
			return true;
		}
		return false;
	}

	/**
	 * Removes a leg from the mob. Cannot have negative count of legs
	 *
	 * @return True if a leg could be removed
	 */
	public boolean removeLeg() {
		if (this.numLegs > 0) {
			adjustNumberOfLegs(-1);
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param num
	 */
	protected void adjustNumberOfLegs(int num) {
		this.numLegs += num;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moosedrive.boots.mobs.IContainer#getInventoryItems()
	 */
	@Override
	public List<IItem> getContents() {
		return new ArrayList<>(inventory);
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
		return (!inventory.contains(item) && inventory.add(item));
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
		return inventory.remove(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moosedrive.boots.mobs.IContainer#clearInventory()
	 */
	@Override
	public List<IItem> clear() {
		List<IItem> list = getContents();
		inventory.clear();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moosedrive.boots.mobs.IContainer#takeRandomItemFromInventory()
	 */
	@Override
	public IItem takeRandomItem() {
		if (!inventory.isEmpty()) {
			return inventory.remove(MathUtils.random(inventory.size() - 1));
		}
		return null;
	}

	/**
	 * @return the damage creature can inflict pre-modifiers
	 */
	public int getBaseDamage() {
		return baseDamage;
	}

	/**
	 * @return the damage the creature can inflict
	 */
	public abstract int getDamage();

	/**
	 * Apply damage to the creature. Damage may be madified depending on unique
	 * conditions and armor.
	 * 
	 * @param damage
	 *            Raw damage to try and apply
	 * @return damage actually applied
	 */
	public abstract int applyDamage(int damage);

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public int getCapacity() {
		return this.strength * CAPACITY_STRENGTH_MULTIPLIER;
	}
	
	/**
	 * Sum the encumbrance of items.
	 * Should be extended for mob types that have added equipped inventory.
	 * @return total encumbrance of contained items
	 */
	public double getEncumbrance() {
		return getContents().stream().mapToDouble(IItem::getEncumbrance).sum();
		
	}

}
