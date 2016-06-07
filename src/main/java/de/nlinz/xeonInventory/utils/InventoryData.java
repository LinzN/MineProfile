package de.nlinz.xeonInventory.utils;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class InventoryData {
    private String                   playerName;
    private UUID                     playerUUID;
    private ItemStack[]              inventory;
    private ItemStack[]              armor;
    private ItemStack[]              enderchest;
    private Collection<PotionEffect> effects;
    private int                      level;
    private float                    exp;
    private double                   maxHealth;
    private double                   health;
    private int                      food;
    private GameMode                 gamemode;
    private int                      fireticks;
    private int                      slot;
    private boolean                  fly;
    private boolean                  vanish;

    public InventoryData(Player player) {
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId();
    }

    public void setInventoryContentFromString(String data) {
        this.inventory = SerializerFull.itemStackArrayFromBase64(data);
    }

    public void setInventoryContent(ItemStack[] data) {
        this.inventory = data;
    }

    public void setArmorContentFromString(String data) {
        this.armor = SerializerFull.itemStackArrayFromBase64(data);
    }

    public void setArmorContent(ItemStack[] data) {
        this.armor = data;
    }

    public void setEnderchestContentFromString(String data) {
        this.enderchest = SerializerFull.itemStackArrayFromBase64(data);
    }

    public void setEnderchestContent(ItemStack[] data) {
        this.enderchest = data;
    }

    public void setPotionEffectFromString(String data) {
        this.effects = SerializerFull.potionEffectsFromString(data);
    }

    public void setPotionEffect(Collection<PotionEffect> data) {
        this.effects = data;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setFood(int data) {
        this.food = data;
    }

    public void setGamemodeFromString(String gamemode) {
        this.gamemode = GameMode.valueOf(gamemode);
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public void setFireticks(int data) {
        this.fireticks = data;
    }

    public void setSlot(int data) {
        this.slot = data;
    }

    public void setFly(boolean value) {
        this.fly = value;
    }

    public void setFlyInt(int value) {
        if (value == 1) {
            this.fly = true;
        } else {
            this.fly = false;
        }
    }

    public void setVanish(boolean value) {
        this.vanish = value;
    }

    public void setVanishInt(int value) {
        if (value == 1) {
            this.vanish = true;
        } else {
            this.vanish = false;
        }
    }

    //Now get data

    public ItemStack[] getInventoryContent() {
        return this.inventory;
    }

    public String getInventoryContentToString() {
        return SerializerFull.itemStackArrayToBase64(this.inventory);
    }

    public ItemStack[] getArmorContent() {
        return this.armor;
    }

    public String getArmorContentToString() {
        return SerializerFull.itemStackArrayToBase64(this.armor);
    }

    public ItemStack[] getEnderchestContent() {
        return this.enderchest;
    }

    public String getEnderchestToString() {
        return SerializerFull.itemStackArrayToBase64(this.enderchest);
    }

    public Collection<PotionEffect> getPotionEffects() {
        return this.effects;
    }

    public String getPotionEffectsToString() {
        return SerializerFull.potionEffectsToString(this.effects);
    }

    public int getLevel() {
        return this.level;
    }

    public float getExp() {
        return this.exp;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public double getHealth() {
        return this.health;
    }

    public int getFood() {
        return this.food;
    }

    public GameMode getGameMode() {
        return this.gamemode;
    }

    public String getGameModeToString() {
        return this.gamemode.name();
    }

    public int getFireticks() {
        return this.fireticks;
    }

    public int getSlot() {
        return this.slot;
    }

    public boolean getFly() {
        return this.fly;
    }

    public int getFlyInt() {
        if (this.fly) {
            return 1;
        }
        return 0;
    }

    public boolean getVanish() {
        return this.vanish;
    }

    public int getVanishInt() {
        if (this.vanish) {
            return 1;
        }
        return 0;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }
}
