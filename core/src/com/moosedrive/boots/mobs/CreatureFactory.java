/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

/**
 *
 * @author
 */
public class CreatureFactory {

	/**
	 * Create a new human customer.
	 *
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param additionalName
	 * @param health
	 * @return
	 */
	public static Customer getHuman(String title, String firstName, String lastName, String additionalName,
			int health, int strength) {
		MobName humanName = new MobName(title, lastName, firstName, additionalName, MobConstants.MOB_TYPE_HUMAN);
		return new Customer(humanName, 2, 2, 1, health, strength);
	}

}
