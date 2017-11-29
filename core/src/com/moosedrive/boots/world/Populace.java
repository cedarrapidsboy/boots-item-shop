/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.moosedrive.boots.items.armor.Boot;
import com.moosedrive.boots.items.containers.ContainerUtils;
import com.moosedrive.boots.items.potions.HealthPotion;
import com.moosedrive.boots.items.potions.Potion;
import com.moosedrive.boots.mobs.Creature;
import com.moosedrive.boots.mobs.Customer;
import com.moosedrive.boots.mobs.CustomerFactory;
import com.moosedrive.boots.mobs.MobConstants;
import com.moosedrive.boots.mobs.Monster;
import com.moosedrive.boots.mobs.Spider;
import com.moosedrive.boots.utils.NameUtils;
import com.moosedrive.boots.world.shops.BootShop;

/**
 *
 * @author cedarrapidsboy
 */
public class Populace {

	private static Populace pop;
	private Denizens denizens;
	private final List<List<Creature>> combatList;
	private static final int MIN_CUSTOMERS = 5;
	private static final int SPAWN_MILLIS = 1000;
	private static final int FIGHT_MILLIS = 500;
	private long lastTick = 0;
	private long lastSpawn = 0;
	private long lastFight = 0;

	public int getMonsterCount() {
		return denizens.getMonsters().size();
	}

	public int getCustomerCount() {
		return denizens.getCustomers().size();
	}

	public void addSpider() {
		this.spiderWaitingToSpawn++;
	}

	public void addCustomer() {
		this.customersWaitingToSpawn++;
	}

	private long spiderWaitingToSpawn = 0;
	private long customersWaitingToSpawn = 0;

	private Populace() {
		denizens = new Denizens();
		denizens.setCustomers(new HashSet<Customer>());
		denizens.setMonsters(new HashSet<Creature>());
		combatList = new ArrayList<List<Creature>>();
	}

	public static Populace getInstance() {
		if (pop == null) {
			pop = new Populace();
		}
		return pop;
	}

	public void populateWorld() {
		for (int i = 0; i < MIN_CUSTOMERS; i++) {
			addCustomerAndSpiders(denizens.getCustomers(), denizens.getMonsters());
		}
	}

	/**
	 * Adds one customer and three spiders to provided lists
	 * 
	 * @param custs
	 * @param monsts
	 */
	public static void addCustomerAndSpiders(Set<Customer> custs, Set<Creature> monsts) {
		addCustomer(custs);
		for (int x = 0; x < 10; x++) {
			addSpider(monsts);
		}
	}

	private static void addSpider(Set<Creature> monsts) {
		Spider spider = Spider.getRandomSpider();
		monsts.add(spider);
	}

	private static void addCustomer(Set<Customer> custs) {
		Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "", "",
				100);
		cust.addItem(new HealthPotion(Potion.POTION_SMALL));
		cust.setMoney(MathUtils.random(10, 100));
		custs.add(cust);
	}

	/**
	 *
	 * @return Number of entities in the populace
	 */
	public int count() {
		return denizens.getCustomers().size() + denizens.getMonsters().size();
	}

	public void addCustomerToWorld() {
		Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "",
				"from the machine", MathUtils.random(50, 255));
		denizens.getCustomers().add(cust);
		System.out.println("Added customer to populace: " + cust.name().getName());
		System.out.println("Customers: " + denizens.getCustomers().size());
	}

	/**
	 * |Name|Health|DMG|AC|Gold|Inventory|Actions|
	 * 
	 * @return A list of string arrays (records) of the world's creatures status'
	 */
	public List<String[]> worldStatus() {
		ArrayList<String[]> records = new ArrayList<String[]>();
		Iterator<Customer> custit = denizens.getCustomers().iterator();
		Customer cust;
		List<Creature> tmpList;
		while (custit.hasNext()) {
			String[] record = new String[7];
			cust = custit.next();
			tmpList = isFighting(cust);
			record[0] = cust.name().getName();
			record[1] = String.valueOf(cust.getCurHealth());
			record[2] = String.valueOf(cust.getDamage());
			record[3] = String.valueOf(cust.getArmorValue());
			record[4] = String.valueOf(cust.getMoney());
			record[5] = ContainerUtils.inventorySummary(cust.getContents());
			record[6] = (tmpList.size() == 0) ? "Adventuring." : "Fighting.";
			records.add(record);
		}

		Iterator<Creature> creatit = denizens.getMonsters().iterator();
		Creature creat;

		while (creatit.hasNext()) {
			String[] record = new String[7];
			creat = creatit.next();
			tmpList = isFighting(creat);
			record[0] = creat.name().getName();
			record[1] = String.valueOf(creat.getCurHealth());
			record[2] = String.valueOf(creat.getDamage());
			record[3] = String.valueOf(0);
			record[4] = String.valueOf(creat.getMoney());
			record[5] = ContainerUtils.inventorySummary(creat.getContents());
			record[6] = (tmpList.size() == 0) ? "Creeping." : "Fighting.";
			records.add(record);
		}

		return records;
	}

	public void worldTick() {
		long currentMillis = TimeUtils.millis();
		while (customersWaitingToSpawn > 0) {
			addCustomer(denizens.getCustomers());
			customersWaitingToSpawn--;
		}
		while (spiderWaitingToSpawn > 0) {
			addSpider(denizens.getMonsters());
			spiderWaitingToSpawn--;
		}
		// Spawns and de-spawns
		lastSpawn = processSpawns(lastSpawn);

		// Process fights
		// Pick fights
		lastFight = processFights(lastFight);

		this.setLastTick(currentMillis);
	}

	private long processFights(long deltaMillis) {
		if ((TimeUtils.millis() - deltaMillis) > FIGHT_MILLIS) {
			// process fights
			denizens.getCustomers().stream().forEach(customer -> {
				List<Creature> fighters = isFighting(customer);
				if (fighters.size() > 0) {
					Creature weakest = fighters.parallelStream().filter(f -> f instanceof Monster)
							.filter(m -> !((Monster) m).isFriendly()).min(Comparator.comparing(m -> m.getCurHealth()))
							.orElse(customer);
					if (weakest != customer) {
						System.out.println(customer.name().getName() + " is fighting " + weakest.name().getName());
						if (customer.getCurHealth() > 0 && weakest.getCurHealth() > 0) {
							// customer takes a swing
							if (customer.heal() == 0) {
								weakest.applyDamage(customer.getDamage());
							} else {
								System.out.println("+++" + customer.name().getName() + " healed during battle.");
							}
							// monster takes a swing
							if (weakest.getCurHealth() >= 0) {
								customer.applyDamage(weakest.getDamage());
							}
						}
						if (customer.getCurHealth() <= 0) {
							// give items to monster because customer died
							customer.getContents().forEach(i -> weakest.addItem(i));
							weakest.setMoney(customer.getMoney() + weakest.getMoney());
						}
						if (weakest.getCurHealth() <= 0) {
							// give items to customer because monster died
							weakest.getContents().forEach(i -> customer.addItem(i));
							customer.setMoney(customer.getMoney() + weakest.getMoney());
							// customer heals if possible and equips new stuff
							if (customer.heal() > 0) {
								System.out.println(
										"+++" + customer.name().getName() + " drinks a potion.");
							}
							// Go buy/sell some boots (instantaneously)
							processPurchases(customer);
							customer.equipBestBoots();
						}

					}
				}
			});
			// Remove dead combatants
			combatList.parallelStream().forEach(l -> {
				List<Creature> toRemove = l.stream().filter(c -> c.getCurHealth() <= 0).collect(Collectors.toList());
				toRemove.parallelStream().forEach(c -> {
					l.remove(c);
					System.out.println("---" + c.name().getName() + " died and has been removed from combat.");
				});

			});
			// Remove combat groups of 1
			combatList.removeAll(combatList.stream().filter(l -> l.size() <= 1).collect(Collectors.toList()));
			// Remove monster-only combats
			ArrayList<List<Creature>> monsterParties = new ArrayList<>();
			Iterator<List<Creature>> it = combatList.iterator();
			while (it.hasNext()) {
				List<Creature> list = it.next();
				boolean isMonsterParty = true;
				Iterator<Creature> c = list.iterator();
				while (c.hasNext()) {
					if (c.next() instanceof Customer) {
						isMonsterParty = false;
					}
				}
				if (isMonsterParty) {
					monsterParties.add(list);
				}
				;
			}
			combatList.removeAll(monsterParties);
			
			// pickfights
			List<Creature> notFighting = denizens.getMonsters().parallelStream().filter(c -> isFighting(c).size() == 0)
					.collect(Collectors.toList());
			notFighting.stream().forEach(m -> {
				// 1:50 chance of picking a fight a customer
				if (MathUtils.random(1, 20) == 1) {
					ArrayList<Customer> entities = new ArrayList<Customer>(denizens.getCustomers());
					startFighting(entities.get(MathUtils.random(entities.size() - 1)), m);
				}
			});
			return TimeUtils.millis();
		}
		return deltaMillis;
	}

	private void processPurchases(Customer customer) {
		ArrayList<Boot> sortedBoots = new ArrayList<Boot>(customer.getContents().stream().filter(i -> i instanceof Boot)
				.map(b -> (Boot) b).sorted(Comparator.comparingInt(b -> BootShop.getBootCost((Boot) b)).reversed())
				.collect(Collectors.toList()));
		// Keep a spare set of boots
		BootShop shop = BootShop.getInstance();
		if (sortedBoots.size() > 2) {
			List<Boot> bootsToSell = sortedBoots.subList(2, sortedBoots.size());
			bootsToSell.forEach(b -> {
				int bootValue = BootShop.getBootCost(b);
				if (shop.getShopMoney() >= bootValue) {
					customer.removeItem(b);
					shop.addBoot(b);
					customer.setMoney(customer.getMoney() + bootValue);
					shop.removeShopMoney(bootValue);
				}
			});
		}

		for (int i = 0; i < customer.bootsNeeded(); i++) {
			List<Boot> browseBoots = shop.viewBootsByCost();
			long funds = customer.getMoney();
			Boot boot = browseBoots.stream().filter(b -> BootShop.getAftermarketBootCost(b) < funds).findFirst()
					.orElse(null);
			if (boot != null) {
				long cost = BootShop.getAftermarketBootCost(boot);
				shop.takeBoot(boot);
				customer.addItem(boot);
				customer.setMoney(customer.getMoney() - cost);
				shop.addShopMoney(cost);
			}
		}

	}

	/**
	 * Adds and removes creatures
	 * 
	 * @param deltaMillis
	 *            time of last spawn update
	 * @return time of last spawn update (same as deltaMillis if update not yet
	 *         scheduled)
	 */
	private long processSpawns(long deltaMillis) {
		if ((TimeUtils.millis() - deltaMillis) > SPAWN_MILLIS) {
			if (denizens.getCustomers().size() < MIN_CUSTOMERS) {
				addCustomerAndSpiders(denizens.getCustomers(), denizens.getMonsters());
			}
			denizens.getCustomers().removeAll(
					denizens.getCustomers().parallelStream().filter(c -> c.getCurHealth() <= 0).collect(Collectors.toList()));
			denizens.getMonsters().removeAll(
					denizens.getMonsters().parallelStream().filter(c -> c.getCurHealth() <= 0).collect(Collectors.toList()));
			if (MathUtils.random(1, 15) == 1) {
				// 1:15 chance for a spider rush
				denizens.getCustomers().forEach(c -> {
					for (int i = 0; i < 5; i++) {
						addSpider();
					}
				});
			}

			return TimeUtils.millis();
		}
		return deltaMillis;
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

	public long getLastTick() {
		return lastTick;
	}

	private void setLastTick(long lastTick) {
		this.lastTick = lastTick;
	}
}
