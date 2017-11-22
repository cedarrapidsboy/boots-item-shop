/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

/**
 *
 * @author cedarrapidsboy
 */
public class Spider extends Monster {
	private final static int BASE_DMG = 10;

	private Spider(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
		super(name, numLegs, numArms, numHeads, maxHealth, false, BASE_DMG);
	}

	public static Spider getSpider(MobName name, int maxHealth) {
		return new Spider(name, 8, 0, 1, maxHealth);
	}

}
