/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

import java.util.List;
import java.util.stream.Collectors;

import com.moosedrive.boots.items.IItem;
import com.moosedrive.boots.items.armor.Boot;
import com.moosedrive.boots.items.armor.IArmorItem;

/**
 * Represents a customer of the item shop. A customer will have funds, an
 * inventory, and equipment (equipped items).
 *
 * @author cedarrapidsboy
 */
public class Customer extends Creature {

    private List<IArmorItem> equippedArmor;
    private List<IItem> inventory;

    public Customer(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
        super(name, numLegs, numArms, numHeads, maxHealth);
    }

    @Override
    protected void adjustNumberOfLegs(int num) {
        super.adjustNumberOfLegs(num);
        if (num < 0) {
            List<Boot> boots = getEquippedBoots().stream().sorted((Boot boot1, Boot boot2) -> Double.compare(boot1.getCondition() / boot1.getDurability(), boot2.getCondition() / boot2.getDurability())).collect(Collectors.toList());
            int bootsToRemove = boots.size() - this.numLegs;
            //Place boots in the inventory if there are more boots than legs
            for (int i = 0; i < bootsToRemove; i++) {
                Boot bootToStore = boots.remove(0);
                this.inventory.add(bootToStore);
                this.equippedArmor.remove(bootToStore);
            }
        } else if (num > 0) {
            List<Boot> boots = getInventoryBoots().stream().sorted((Boot boot1, Boot boot2) -> Double.compare(boot1.getConditionPercent(), boot2.getConditionPercent())).collect(Collectors.toList());
            int bootsToPutOn = boots.size() - num + 1;
            //Put on boot from inventory with highest durability
            for (int i = 0; i < bootsToPutOn; i++) {
                Boot bootToAdd = boots.remove(boots.size() - 1);
                this.equippedArmor.add(bootToAdd);
                this.inventory.remove(bootToAdd);
            }
        }
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

}
