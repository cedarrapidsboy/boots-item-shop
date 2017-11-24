/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.containers.ContainerUtils;
import com.moosedrive.boots.items.potions.HealthPotion;
import com.moosedrive.boots.items.potions.Potion;
import com.moosedrive.boots.mobs.Creature;
import com.moosedrive.boots.mobs.Customer;
import com.moosedrive.boots.mobs.CustomerFactory;
import com.moosedrive.boots.mobs.MobConstants;
import com.moosedrive.boots.mobs.Spider;
import com.moosedrive.boots.utils.NameUtils;

/**
 *
 * @author cedarrapidsboy
 */
public class Populace {

	private static Populace pop;
	private final Set<Customer> customers;
	private final Set<Creature> monsters;
	private final List<List<Creature>> combatList;

	private Populace() {
		customers = new HashSet<Customer>();
		monsters = new HashSet<Creature>();
		combatList = new ArrayList<List<Creature>>();
	}

	public static Populace getInstance() {
		if (pop == null) {
			pop = new Populace();
		}
		return pop;
	}

	public void populateWorld() {
		for (int i = 0; i < 5; i++) {
			Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "",
					"", 100);
			cust.addItem(new HealthPotion(Potion.POTION_SMALL));
			cust.setMoney(MathUtils.random(10, 100));
			customers.add(cust);
			for (int x = 0; x < 3; x++) {
				// Add some spiders per customer
				monsters.add(Spider.getSpider(NameUtils.getSimpleName("Icky Spider", MobConstants.MOB_TYPE_SPIDER),
						MathUtils.random(10, 30)));
			}
			monsters.parallelStream().forEach(m -> {
				if (MathUtils.random(1, 50) == 1) {
					// 1:50 chance for a random boot
					m.addItem(ArmorFactory.getRandomBoot());
				}
				if (MathUtils.random(1, 20) == 1) {
					// 1:20 chance for a health potion
					m.addItem(new HealthPotion(Potion.POTION_SMALL));
				}
				if (MathUtils.random(1, 10) == 1) {
					// 1:10 chance for some gold
					m.setMoney(MathUtils.random(5, 20));
				}
			});
		}
	}

	/**
	 *
	 * @return Number of entities in the populace
	 */
	public int count() {
		return customers.size();
	}

	public void addCustomerToWorld() {
		Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "",
				"from the machine", MathUtils.random(50, 255));
		customers.add(cust);
		System.out.println("Added customer to populace: " + cust.name().getName());
		System.out.println("Customers: " + customers.size());
	}

	/**
	 * |Name|Health|DMG|AC|Gold|Inventory|Actions|
	 * 
	 * @return A list of string arrays (records) of the world's creatures status'
	 */
	public List<String[]> worldStatus() {
		ArrayList<String[]> records = new ArrayList<String[]>();
		Iterator<Customer> custit = customers.iterator();
		Customer cust;
		while (custit.hasNext()) {
			String[] record = new String[7];
			cust = custit.next();
			record[0] = cust.name().getName();
			record[1] = String.valueOf(cust.getCurHealth());
			record[2] = String.valueOf(cust.getDamage());
			record[3] = String.valueOf(0);
			record[4] = String.valueOf(cust.getMoney());
			record[5] = ContainerUtils.inventorySummary(cust.getContents());
			record[6] = "Adventuring.";
			records.add(record);
		}

		Iterator<Creature> creatit = monsters.iterator();
		Creature creat;

		while (creatit.hasNext()) {
			String[] record = new String[7];
			creat = creatit.next();
			record[0] = creat.name().getName();
			record[1] = String.valueOf(creat.getCurHealth());
			record[2] = String.valueOf(creat.getDamage());
			record[3] = String.valueOf(0);
			record[4] = String.valueOf(creat.getMoney());
			record[5] = ContainerUtils.inventorySummary(creat.getContents());
			record[6] = "Creeping.";
			records.add(record);
		}

		return records;
	}

	/**
	 * Checks if creature is fighting and returns the combat participants
	 * 
	 * @param creature
	 * @return List of creatures fighting with/against creature. Empty list if
	 *         creature not fighting.
	 */
	private List<Creature> isFighting(Creature creature) {
		Iterator<List<Creature>> it = combatList.iterator();
		while (it.hasNext()) {
			List<Creature> fighters = it.next();
			if (fighters.contains(creature)) {
				return fighters;
			}
		}
		return new ArrayList<>();
	}

	/**
	 * Makes the customer and creature fight. One will join the fight if the other
	 * is already engaged. Otherwise, a new fight between the two is started.
	 * 
	 * @param customer
	 * @param creature
	 * @return List of existing combatants, or a new list, with the customer and
	 *         creature added. Empty list if both are already fighting in other
	 *         battles. null if one was already fighting but something goes wrong.
	 */
	private List<Creature> startFighting(Customer customer, Creature creature) {
		boolean customerFighting = isFighting(customer).size() > 0;
		boolean creatureFighting = isFighting(creature).size() > 0;
		if (customerFighting && creatureFighting) {
			// Both are already in combat -- cannot fight each other (or already are)
			return new ArrayList<>();
		} else if (!customerFighting && !creatureFighting) {
			// Neither are fighting so they fight each other
			ArrayList<Creature> newFight = new ArrayList<>();
			newFight.add(customer);
			newFight.add(creature);
			combatList.add(newFight);
			return newFight;
		} else {
			// One is fighting.. so the other will join in the existing combat
			Iterator<List<Creature>> it = combatList.iterator();
			List<Creature> combatants = null;
			while (it.hasNext()) {
				combatants = it.next();
				if (customerFighting && combatants.contains(customer)) {
					combatants.add(creature);
					return combatants;
				} else if (creatureFighting && combatants.contains(creature)) {
					combatants.add(customer);
					return combatants;
				}
			}
		}
		// For some reason the combat could not be found
		return null;
	}
}
