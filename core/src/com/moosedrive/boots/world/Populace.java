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
import java.util.Collections;
import java.util.HashSet;
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
    private final Timer customerTimer;

    private Populace() {
        customers = Collections.synchronizedSet(new HashSet());
        customerTimer = new Timer();
        customerTimer.schedule(new UpdateTask(true), 1 * 1000, 3 * 1000);
    }

    public static Populace getInstance() {
        if (pop == null) {
            pop = new Populace();
        }
        return pop;
    }

    private class UpdateTask extends TimerTask {

        public UpdateTask(boolean active) {
            this.active = active;
        }

        boolean active;

        @Override
        public void run() {
            if (this.active) {
                Customer cust = CustomerFactory.getHuman("", NameUtils.getRandomFirstName(MobConstants.MOB_TYPE_HUMAN), "", "from the machine", MathUtils.random(50, 255));
                customers.add(cust);
                System.out.println("Added customer to populace: " + cust.name().getName());

//TODO get some boots from the store
            }
            System.out.println("Customers: " + customers.size());
        }

        public void pause() {
            this.active = false;
        }

        public void start() {
            this.active = true;
        }

    }

}
