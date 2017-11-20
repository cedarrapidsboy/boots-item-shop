/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import com.badlogic.gdx.utils.Json;

/**
 *
 * @author Chad
 */
public class Monster extends Creature {
    
    public Monster(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
        super(name, numLegs, numArms, numHeads, maxHealth);
    }
    
}
