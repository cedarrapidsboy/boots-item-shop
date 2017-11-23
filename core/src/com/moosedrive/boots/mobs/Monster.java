/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import com.moosedrive.boots.items.containers.IContainer;

/**
 *
 * @author 
 */
public abstract class Monster extends Creature implements IContainer {
	

	private boolean isFriendly;
    
	public Monster(MobName name, int numLegs, int numArms, int numHeads, int maxHealth, boolean friendly, int baseDamage) {
        super(name, numLegs, numArms, numHeads, maxHealth, baseDamage);
        this.setFriendly(friendly);
    }

	public boolean isFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}
    
}
