/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.armor.Boot;
import com.moosedrive.boots.items.armor.IArmorItem;
import com.moosedrive.boots.items.potions.HealthPotion;
import com.moosedrive.boots.world.WorldTile;

/**
 * Represents a customer of the item shop. A customer will have funds, an
 * inventory, and equipment (equipped items).
 *
 * @author cedarrapidsboy
 */
public class Customer extends Creature {
	
	public static int MOVEMENT_RANGE = 5;

	@Override
	public double getEncumbrance() {
		return super.getEncumbrance() + equippedArmor.stream().mapToDouble(IArmorItem::getEncumbrance).sum();
	}

	private final static int BASE_DMG = 10;

	private List<IArmorItem> equippedArmor;

	public Customer(MobName name, int numLegs, int numArms, int numHeads, int maxHealth, int strength, WorldTile loc) {
		super(name, numLegs, numArms, numHeads, maxHealth, BASE_DMG, strength, loc);
		equippedArmor = new ArrayList<IArmorItem>();
	}
	
	

	@Override
	public int getRange() {
		return MOVEMENT_RANGE;
	}



	@Override
	protected void adjustNumberOfLegs(int num) {
		super.adjustNumberOfLegs(num);
		equipBestBoots();
	}

	/**
	 * @return Combined armor value of equipped armor
	 */
	public int getArmorValue() {
		return equippedArmor.parallelStream().filter(armor -> armor instanceof IArmorItem)
				.map(armor -> (IArmorItem) armor).mapToInt(armor -> armor.getArmorValue()).sum();
	}

	/**
	 * Equips the best boots from the inventory and equipped items
	 * 
	 * @return Boots that were equipped (may be empty)
	 */
	public List<Boot> equipBestBoots() {
		takeOffBoots();
		List<Boot> boots = getInventoryBoots().stream().sorted(Comparator.comparing(Boot::getArmorValue).reversed())
				.collect(Collectors.toList());
		int bootsToPutOn = (boots.size() > getNumLegs()) ? getNumLegs() : boots.size();
		for (int i = 0; i < bootsToPutOn; i++) {
			Boot boot = boots.get(i);
			removeItem(boot);
			this.equippedArmor.add(boot);
		}
		return getEquippedBoots();
	}

	private void takeOffBoots() {
		List<Boot> equipped = getEquippedBoots();
		// remove boots from equipped items
		equippedArmor = equippedArmor.stream().filter(armor -> !(armor instanceof Boot)).map(armor -> (Boot) armor)
				.collect(Collectors.toList());
		// add boots to inventory
		inventory.addAll(equipped);
	}

	/**
	 * Return a list of Boots from the equipped armor
	 *
	 * @return
	 */
	public List<Boot> getEquippedBoots() {
		return equippedArmor.stream().filter(armor -> armor instanceof Boot).map(armor -> (Boot) armor)
				.collect(Collectors.toList());
	}

	/**
	 * Return a list of Boots from the equipped armor
	 *
	 * @return
	 */
	private List<Boot> getInventoryBoots() {
		return getContents().stream().filter(armor -> armor instanceof Boot).map(armor -> (Boot) armor)
				.collect(Collectors.toList());
	}

	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return getBaseDamage();
	}

	@Override
	public int applyDamage(int damage) {
		int newDamage = damage - getArmorValue();
		if (newDamage < 0) {
			newDamage = 0;
		}
		long newHealth = getCurHealth() - newDamage;
		if (newHealth < 0) {
			newHealth = 0;
		}
		setCurHealth(newHealth);

		// damage boots
		if (getEquippedBoots().size() > 0) {
			ArrayList<Boot> boots = new ArrayList<Boot>(getEquippedBoots());
			Boot aBoot = null;
			if (boots.size() > 0) {
				aBoot = boots.get(MathUtils.random(0, boots.size() - 1));
			}
			if (aBoot != null) {
				aBoot.setCondition(aBoot.getCondition() - 1);
				if (aBoot.isBroken()) {
					equippedArmor.remove(aBoot);
				}
			}
		}

		return newDamage;
	}

	public int bootsNeeded() {
		return getNumLegs() - getEquippedBoots().size();
	}

	public long heal() {

		HealthPotion pot = (HealthPotion) getContents().stream().filter(i -> i instanceof HealthPotion)
				.max(Comparator.comparing(p -> ((HealthPotion) p).getBasePrice())).orElse(null);
		if (pot != null && (getCurHealth() < maxHealth * .5F)) {
			long healingVal = pot.drinkPotion();
			long prevHealth = getCurHealth();
			setCurHealth(getCurHealth() + healingVal);
			if (getCurHealth() > maxHealth) {
				setCurHealth(maxHealth);
			}

			removeItem(pot);
			return getCurHealth() - prevHealth;
		}
		return 0;
	}

}
