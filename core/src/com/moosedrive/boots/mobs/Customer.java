/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import com.moosedrive.boots.items.armor.Boot;
import com.moosedrive.boots.items.armor.IArmor;
import com.moosedrive.boots.items.IItem;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a customer of the item shop. A customer will have funds, an
 * inventory, and equipment (equipped items).
 *
 * @author cedarrapidsboy
 */
public class Customer {

    private MobName name;
    private final int mobType;
    private int numLegs;
    private final int maxLegs;
    private int numArms;
    private final int maxArms;
    private int numHeads;
    private final int maxHeads;
    private int curHealth;
    private final int maxHealth;
    private List<IArmor> equippedArmor;
    private List<IItem> inventory;

    public Customer(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
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

    public int getCurHealth() {
        return curHealth;
    }

    /**
     * Return a list of Boots from the equipped armor
     *
     * @return
     */
    private List<Boot> getEquippedBoots() {
        return equippedArmor.stream()
                .filter(armor -> armor instanceof Boot)
                .map(armor -> (Boot) armor)
                .collect(Collectors.toList());
    }
     /**
     * Return a list of Boots from the equipped armor
     *
     * @return
     */
    private List<Boot> getInventoryBoots() {
        return inventory.stream()
                .filter(armor -> armor instanceof Boot)
                .map(armor -> (Boot) armor)
                .collect(Collectors.toList());
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
    private void adjustNumberOfLegs(int num) {
        this.numLegs += num;
        if (num < 0) {
            List<Boot> boots = getEquippedBoots()
                    .stream()
                    .sorted((Boot boot1, Boot boot2)
                            -> Double.compare(boot1.getCondition() / boot1.getDurability(),
                            boot2.getCondition() / boot2.getDurability()))
                    .collect(Collectors.toList());
            int bootsToRemove = boots.size() - this.numLegs;
            //Place boots in the inventory if there are more boots than legs
            for (int i = 0; i < bootsToRemove; i++) {
                Boot bootToStore = boots.remove(0);
                this.inventory.add(bootToStore);
                this.equippedArmor.remove(bootToStore);
            }
        } else if (num > 0) {
             List<Boot> boots = getInventoryBoots()
                    .stream()
                    .sorted((Boot boot1, Boot boot2)
                            -> Double.compare(boot1.getConditionPercent(),
                            boot2.getConditionPercent()))
                    .collect(Collectors.toList());
            int bootsToPutOn = boots.size() - num + 1;
            //Put on boot from inventory with highest durability
            for (int i = 0; i < bootsToPutOn; i++) {
                Boot bootToAdd = boots.remove(boots.size() - 1);
                this.equippedArmor.add(bootToAdd);
                this.inventory.remove(bootToAdd);
            }
        }
    }
}
