/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.items;

/**
 *
 * @author cedarrapidsboy
 */
public interface IItem {

    /**
     * Returns the item's condition as a percentage of durability (0.0 to 1.0)
     *
     * @return Current condition 0.0-1.0
     */
    int getCondition();

    double getConditionPercent();

    /**
     * A value representing the weight of this item
     *
     * @return float weight of this item
     */
    double getEncumbrance();

    /**
     * A value representing the maximum durability of this item.
     *
     * @return int maximum durability
     */
    int getDurability();

    /**
     * Set the current item condition as a fraction of the durability
     *
     * @param condition A double value 0 to 1.0
     */
    void setCondition(int condition);

    /**
     * An item is broken if its condition reaches 0
     *
     * @return
     */
    boolean isBroken();
}
