/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.math.MathUtils;
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
    

    private Populace() {
        customers = new HashSet<Customer>();
        monsters = new HashSet<Creature>();
    }

    public static Populace getInstance() {
        if (pop == null) {
            pop = new Populace();
        }
        return pop;
    }
    
    public void populateWorld() {
    	for (int i = 0; i < 5; i++) {
    		Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "", "", 100);
    		cust.addItem(new HealthPotion(Potion.POTION_SMALL));
    		customers.add(cust);
    		for (int x = 0; x < 3; x++) {
    			//Add some spiders per customer
    			monsters.add(Spider.getSpider(NameUtils.getSimpleName("Icky Spider", MobConstants.MOB_TYPE_SPIDER), MathUtils.random(10, 30)));
    		}
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
        Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "", "from the machine", MathUtils.random(50, 255));
        customers.add(cust);
        System.out.println("Added customer to populace: " + cust.name().getName());
        System.out.println("Customers: " + customers.size());
    }

    public String worldStatusText() {
        StringBuilder lines = new StringBuilder();
        Iterator<Customer> custit = customers.iterator();
        Customer cust;
        while (custit.hasNext()) {
            cust = custit.next();
        	lines.append(cust.name().getName());
            lines.append("[");
            lines.append(ContainerUtils.inventorySummary(cust.getContents()));
            lines.append("]");
            lines.append(" is doing nothing.");
            if (custit.hasNext()) {
                lines.append(System.getProperty("line.separator"));
            }
        }
        
        lines.append(System.getProperty("line.separator"));
        lines.append("===");
        
        Iterator<Creature> creatit = monsters.iterator();
        if (creatit.hasNext()) {
        	lines.append(System.getProperty("line.separator"));
        }
        while (creatit.hasNext()) {
            lines.append(((Creature) creatit.next()).name().getName());
            lines.append(" is doing nothing.");
            if (creatit.hasNext()) {
                lines.append(System.getProperty("line.separator"));
            }
        }

        return lines.toString();
    }

}
