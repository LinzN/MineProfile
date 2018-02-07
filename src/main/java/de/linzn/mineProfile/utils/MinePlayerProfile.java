/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.UUID;


public class MinePlayerProfile {
    private String playerName;
    private UUID playerUUID;
    private ItemStack[] inventory;
    private ItemStack[] armor;
    private ItemStack[] enderChest;
    private Collection<PotionEffect> effects;
    private int level;
    private float exp;
    private double maxHealth;
    private double health;
    private int food;
    private GameMode gamemode;
    private int fireTicks;
    private int slot;
    private boolean fly;
    private boolean vanish;

    public MinePlayerProfile(Player player) {
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId();
    }

    /* Inventory data */
    /* Set content */
    public void setInventoryContent(ItemStack[] data) {
        this.inventory = data;
    }

    public ItemStack[] getInventoryContent() {
        return this.inventory;
    }

    /* Get content */

    public void setInventoryContent(String data) {
        this.inventory = SerializerFull.itemStackArrayFromBase64(data);
    }

    public String getInventoryContentSerialized() {
        return SerializerFull.itemStackArrayToBase64(this.inventory);
    }
    /* Inventory data end */


    /* Armor data */
    /* Set content */
    public void setArmorContent(ItemStack[] data) {
        this.armor = data;
    }

    /* Get content */
    public ItemStack[] getArmorContent() {
        return this.armor;
    }

    public void setArmorContent(String data) {
        this.armor = SerializerFull.itemStackArrayFromBase64(data);
    }

    public String getArmorContentSerialized() {
        return SerializerFull.itemStackArrayToBase64(this.armor);
    }

    /* Armor data end */


    /* EnderChest data */
    /* Set content */
    public void setEnderchestContent(ItemStack[] data) {
        this.enderChest = data;
    }

    /* Get content */
    public ItemStack[] getEnderchestContent() {
        return this.enderChest;
    }

    public void setEnderchestContent(String data) {
        this.enderChest = SerializerFull.itemStackArrayFromBase64(data);
    }

    public String getEnderchestContentSerialized() {
        return SerializerFull.itemStackArrayToBase64(this.enderChest);
    }

    /* EnderChest data end */

    /* Potion data */
    /* Set content */
    public void setPotionEffects(Collection<PotionEffect> data) {
        this.effects = data;
    }

    /* Get content */
    public Collection<PotionEffect> getPotionEffects() {
        return this.effects;
    }

    public void setPotionEffects(String data) {
        this.effects = SerializerFull.potionEffectsFromString(data);
    }

    public String getPotionEffectsSerialized() {
        return SerializerFull.potionEffectsToString(this.effects);
    }
    /* Potion data end */

    /* Gamemode data */
    /* Set content */
    public void setGameMode(String gameMode) {
        this.gamemode = GameMode.valueOf(gameMode);
    }

    /* Get content */
    public GameMode getGameMode() {
        return this.gamemode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gamemode = gameMode;
    }

    public String getGameModeToString() {
        return this.gamemode.name();
    }
    /* Gamemode end */


    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getExp() {
        return this.exp;
    }


    public void setExp(float exp) {
        this.exp = exp;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getFood() {
        return this.food;
    }

    public void setFood(int data) {
        this.food = data;
    }


    public int getFireTicks() {
        return this.fireTicks;
    }

    public void setFireTicks(int data) {
        this.fireTicks = data;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int data) {
        this.slot = data;
    }

    public boolean getFly() {
        return this.fly;
    }

    public void setFly(boolean value) {
        this.fly = value;
    }

    public int getFlyInt() {
        if (this.fly) {
            return 1;
        }
        return 0;
    }

    public void setFlyInt(int value) {
        this.fly = value == 1;
    }

    public boolean getVanish() {
        return this.vanish;
    }

    public void setVanish(boolean value) {
        this.vanish = value;
    }

    public int getVanishInt() {
        if (this.vanish) {
            return 1;
        }
        return 0;
    }

    public void setVanishInt(int value) {
        this.vanish = value == 1;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }
}
