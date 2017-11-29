package com.moosedrive.boots.world;

import java.util.Set;

import com.moosedrive.boots.mobs.Creature;
import com.moosedrive.boots.mobs.Customer;

public class Denizens {
	private Set<Customer> customers;
	private Set<Creature> monsters;

	public Denizens() {
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public Set<Creature> getMonsters() {
		return monsters;
	}

	public void setMonsters(Set<Creature> monsters) {
		this.monsters = monsters;
	}
}