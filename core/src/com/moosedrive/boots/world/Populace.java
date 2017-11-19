/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.mobs.Customer;
import com.moosedrive.boots.mobs.CustomerFactory;
import com.moosedrive.boots.mobs.MobConstants;
import com.moosedrive.boots.utils.NameUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author cedarrapidsboy
 */
public class Populace {

    private static Populace pop;
    private final Set<Customer> customers;

    private Populace() {
        customers = Collections.synchronizedSet(new HashSet());
    }

    public static Populace getInstance() {
        if (pop == null) {
            pop = new Populace();
        }
        return pop;
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
        Iterator it = customers.iterator();
        while (it.hasNext()) {
            lines.append(((Customer) it.next()).name().getName());
            lines.append(" is doing nothing.");
            if (it.hasNext()) {
                lines.append(System.getProperty("line.separator"));
            }
        }
        return lines.toString();
    }

}
