/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.potions.HealthPotion;
import com.moosedrive.boots.items.potions.Potion;
import com.moosedrive.boots.utils.NameUtils;

/**
 *
 * @author cedarrapidsboy
 */
public class Spider extends Monster {
	private final static int BASE_DMG = 15;

	private Spider(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
		super(name, numLegs, numArms, numHeads, maxHealth, false, BASE_DMG);
	}

	public static Spider getSpider(MobName name, int maxHealth) {
		return new Spider(name, 8, 0, 1, maxHealth);
	}

	@Override
	public int getDamage() {
		return getBaseDamage();
	}

	@Override
	public int applyDamage(int damage) {
		long newHealth = getCurHealth() - damage;
		if (newHealth < 0) {
			newHealth = 0;
		}
		setCurHealth(newHealth);
		return damage;
	}

	public static Spider getRandomSpider() {
		// Add some spiders per customer
		Spider spider = getSpider(NameUtils.getSimpleName("Icky Spider", MobConstants.MOB_TYPE_SPIDER),
				MathUtils.random(10, 30));
		if (MathUtils.random(1, 6) == 1) {
			// 1:6 chance for a random boot
			spider.addItem(ArmorFactory.getRandomBoot());
		}
		if (MathUtils.random(1, 3) == 1) {
			// 1:3 chance for 1-2 health potions
			for (int i = 0; i < MathUtils.random(1,2); i++) {
				spider.addItem(new HealthPotion(Potion.POTION_SMALL));
			}
		}
		if (MathUtils.random(1, 3) == 1) {
			// 1:3 chance for some gold
			spider.setMoney(MathUtils.random(5, 20));
		}
		return spider;
	}

}
