/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.items.armor;

/**
 *
 * @author cedarrapidsboy
 */
public abstract class ArmorItem implements IArmorItem{

    final int armorValue; // The amount of armorValue added to the mob
    final double encumbrance; // The amount of encumbrance (wieght) added to the mob
    protected int condition; // The current durability of the item
    final int durability; // The maximum durability or the item

    ArmorItem(int armorValue, double encumbrance, int condition, int durability) {
        if (durability < 1) {
            durability = 1;
        }
        if (condition < 1) {
            condition = 1;
        } else if (condition > durability) {
            condition = durability;
        }
        if (armorValue < 1) {
            armorValue = 1;
        }
        this.armorValue = armorValue;
        this.encumbrance = encumbrance;
        this.condition = condition;
        this.durability = durability;
    }

    /**
     * Returns a percent representing this item's damage/condition related to
     * its max durability
     *
     * @return
     */
    @Override
    public double getConditionPercent() {
        return this.condition / this.durability;
    }

    /**
     * Returns the item's condition as a percentage of durability (0.0 to 1.0)
     *
     * @return Current condition 0.0-1.0
     */
    @Override
    public int getCondition() {
        return condition;
    }

    /**
     * Set the current item condition as a fraction of the durability
     *
     * @param condition A double value 0 to 1.0
     */
    @Override
    public void setCondition(int condition) {
        // Check bounds on condition
        if (condition > durability) {
            condition = durability;
        } else if (condition < 0) {
            condition = 0;
        }
        this.condition = condition;
    }

    /**
     * A value representing the protection this armorValue provides a mob
     *
     * @return value of current armorValue
     */
    @Override
    public int getArmorValue() {
        return armorValue;
    }

    /**
     * A value representing the weight of this item
     *
     * @return float weight of this item
     */
    @Override
    public double getEncumbrance() {
        return encumbrance;
    }

    /**
     * A value representing the maximum durability of this item.
     *
     * @return int maximum durability
     */
    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public boolean isBroken() {
        return (condition <= 0);
    }

}
