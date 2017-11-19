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

    private BootShop() {
        stock = Collections.synchronizedSet(new HashSet());
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

    /**
     *
     * @return Number of items in stock
     */
    public int count() {
        return stock.size();
    }

    public void addBoot() {
        stock.add(ArmorFactory.getRandomBoot());
        System.out.println("Boot stock: " + stock.size());

    }

    public static BootShop getInstance() {
        if (thisShop == null) {
            thisShop = new BootShop();
        }
        return thisShop;
    }
    
    public String stockText(){
        StringBuilder sb = new StringBuilder();
        sb.append("Boots: ");
        sb.append(String.valueOf(count()));
        return sb.toString();
    }
}
