/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.items.armor;

/**
 * A single boot. Mobs may have less than (or more than) two legs.
 * @author cedarrapidsboy
 */
public class boot {

    public boot(int armorValue, double encumbrance, double condition, int durability) {
        this.armorValue = armorValue;
        this.encumbrance = encumbrance;
        this.condition = condition;
        this.durability = durability;
    }
    

    final int armorValue;          // The amount of armorValue added to the mob
    final double encumbrance;  // The amount of encumbrance (wieght) added to the mob
    private double condition;    // The current durability of the item
    final int durability;     // The maximum durability or the item

    /**
     * Returns the item's condition as a percentage of durability (0.0 to 1.0)
     * @return Current condition 0.0-1.0
     */
    public double getCondition() {
        return condition;
    }

    /**
     * Set the current item condition as a fraction of the durability
     * @param condition A double value 0 to 1.0
     */
    public void setCondition(double condition) {
        // Check bounds on condition
        if (condition > 1.0)
        {
            condition = 1.0;
        } else if (condition < 0.0) {
            condition = 0.0;
        } 
        this.condition = condition;
    }

    /**
     * A value representing the protection this armorValue provides a mob
     * @return value of current armorValue
     */
    public int getArmorValue() {
        return armorValue;
    }

    /**
     * A value representing the weight of this item
     * @return float weight of this item
     */
    public double getEncumbrance() {
        return encumbrance;
    }

    /**
     * A value representing the maximum durability of this item.
     * @return int maximum durability
     */
    public int getMaxDurability() {
        return durability;
    }
    
    /**
     * A value representing the current durability of this item
     * @return in current durability
     */
    public int getCurDurability() {
        return (int) Math.round(this.durability * this.condition);
    }
}
