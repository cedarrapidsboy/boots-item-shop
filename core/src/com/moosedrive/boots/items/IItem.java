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
     * @return base item price
     */
    int getBasePrice();
	/**
     * A value representing the weight of this item
     *
     * @return float weight of this item
     */
    double getEncumbrance();
    
    String getName();
}
