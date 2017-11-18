/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moosedrive.boots.mobs;

/**
 *
 * @author Chad
 */
public class MobName {
    private String title;
    private String surname;
    private String name;
    private String additionalName;
    private final int mobType;

    /**
     *
     * @param title Mr., Dr., Commander, etc.
     * @param surname equivalent to an last name
     * @param name equivalent to a first name
     * @param additionalName some extra fluff (usually) at the end of a name
     * @param mobType One of MobConstants.MOB_TYPE_XXX
     */
    public MobName(String title, String surname, String name, String additionalName, int mobType) {
        this.title = title;
        this.surname = surname;
        this.name = name;
        this.additionalName = additionalName;
        this.mobType = mobType;
    }
    
    /**
     *
     * @return the mob type (see MobConstants.MOB_TYPE_XXX)
     */
    public int type(){
        return this.mobType;
    }
    /**
     *
     * @return a constructed display name for the mob
     */
    public String getName(){
        switch (this.mobType) {
            case (MobConstants.MOB_TYPE_HUMAN):
                return (title + " " + name + " " + surname + " " + additionalName).trim();
            case (MobConstants.MOB_TYPE_ELF):
                return (title + " " + surname + " " + name + " " + additionalName).trim();
            case (MobConstants.MOB_TYPE_OGRE):
                return (title + " " + name + " " + additionalName).trim();
        }
        //other
        return (title + " " + name + " " + surname + " " + additionalName).trim(); 
    }
    
}
