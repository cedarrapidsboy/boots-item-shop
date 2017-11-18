/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import com.moosedrive.boots.items.armor.ArmorItem;
import com.moosedrive.boots.items.armor.Boot;
import com.moosedrive.boots.items.armor.IArmor;
import com.moosedrive.boots.items.armor.IItem;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a customer of the item shop. A customer will have funds, an
 * inventory, and equipment (equipped items).
 *
 * @author cedarrapidsboy
 */
public class Customer {

    private int numLegs;
    private int numArms;
    private int numHeads;
    private int curHealth;
    private final int maxHealth;
    private List<IArmor> equippedArmor;
    private List<IItem> inventory;

    public Customer(int numLegs, int numArms, int numHeads, int maxHealth) {
        this.numLegs = numLegs;
        this.numArms = numArms;
        this.numHeads = numHeads;
        this.maxHealth = maxHealth;
        this.curHealth = this.maxHealth;
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

    public int getCurHealth() {
        return curHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Return a list of Boots from the equipped armor
     * @return
     */
    public List<Boot> getEquippedBoots() {
        return equippedArmor.stream().filter(armor -> armor instanceof Boot).map(armor -> (Boot) armor).collect(Collectors.toList());
    }

    public void adjustNumberOfHeads(int num){
        //TODO drop most broken helmet into inventory
        this.numHeads += num;
    }
    
        public void adjustNumberOfLegs(int num){
        //TODO drop most broken boot into inventory
        this.numLegs += num;
    }
}
