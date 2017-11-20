/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

/**
 *
 * @author cedarrapidsboy
 */
public class Creature {
    
    protected MobName name;
    protected final int mobType;
    protected int numLegs;
    protected final int maxLegs;
    protected int numArms;
    protected final int maxArms;
    protected int numHeads;
    protected final int maxHeads;
    protected int curHealth;
    protected final int maxHealth;

    public Creature(MobName name, int numLegs, int numArms, int numHeads, int maxHealth) {
        this.maxLegs = numLegs;
        this.numLegs = this.maxLegs;
        this.maxArms = numArms;
        this.numArms = this.maxArms;
        this.maxHeads = numHeads;
        this.numHeads = this.maxHeads;
        this.maxHealth = maxHealth;
        this.curHealth = this.maxHealth;
        this.name = name;
        this.mobType = name.type();
    }

    public MobName name() {
        return this.name;
    }

    public int getNumLegs() {
        return numLegs;
    }

    public int getNumArms() {
        return numArms;
    }

    public int getNumHeads() {
        return numHeads;
    }

    public int getCurHealth() {
        return curHealth;
    }

    /**
     * Adds a leg to the mob. Cannot add more than maxLegs
     *
     * @return True if a leg could be added
     */
    public boolean addLeg() {
        if (this.numLegs < this.maxLegs) {
            adjustNumberOfLegs(1);
            return true;
        }
        return false;
    }

    /**
     * Removes a leg from the mob. Cannot have negative count of legs
     *
     * @return True if a leg could be removed
     */
    public boolean removeLeg() {
        if (this.numLegs > 0) {
            adjustNumberOfLegs(-1);
            return true;
        }
        return false;
    }

    /**
     *
     * @param num
     */
    protected void adjustNumberOfLegs(int num) {
        this.numLegs += num;
    }
    
}
