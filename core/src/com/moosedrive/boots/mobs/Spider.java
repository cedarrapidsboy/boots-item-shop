/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import java.util.ArrayList;

/**
 *
 * @author cedarrapidsboy
 */
public class Spider extends Monster {

	private Spider(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
		super(name, numLegs, numArms, numHeads, maxHealth);
		inventory = new ArrayList<>();
	}

	public Spider getSpider(MobName name, int maxHealth) {
		return new Spider(name, 8, 0, 1, maxHealth);
	}

}
