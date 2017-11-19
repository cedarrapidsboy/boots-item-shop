/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.world.shops;

import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.armor.Boot;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author cedarrapidsboy
 */
public class BootShop {

    private static BootShop thisShop;
    private Set<Boot> stock;
    private final Timer stockUpdater;

    private BootShop() {
        stock = Collections.synchronizedSet(new HashSet());
        stockUpdater = new Timer();
        stockUpdater.schedule(new UpdateTask(true), 1000, 1000);
    }

    public boolean addBoot(Boot boot) {
        return this.stock.add(boot);
    }

    public synchronized Boot takeBoot() {
        Boot boot;
        if (stock.size() > 0) {
            boot = (Boot) stock.toArray()[0];
        } else {
            boot = ArmorFactory.getRandomBoot();
        }
        return boot;
    }

    private class UpdateTask extends TimerTask {

        public UpdateTask(boolean active) {
            this.active = active;
        }

        boolean active;

        @Override
        public void run() {
            if (this.active) {
                stock.add(ArmorFactory.getRandomBoot());
            }
            System.out.println("Boot stock: " + stock.size());
        }

        public void pause() {
            this.active = false;
        }

        public void start() {
            this.active = true;
        }

    }

    public static BootShop getInstance() {
        if (thisShop == null) {
            thisShop = new BootShop();
        }
        return thisShop;
    }
}
