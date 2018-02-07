/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.core;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.database.ProfileQuery;
import de.linzn.mineProfile.modies.FlyMode;
import de.linzn.mineProfile.modies.VanishMode;
import de.linzn.mineProfile.task.InventoryLoad;
import de.linzn.mineProfile.task.InventorySave;
import de.linzn.mineProfile.utils.HashDB;
import de.linzn.mineProfile.utils.MinePlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerDataAPI {


    public static boolean debug() {
        return MineProfilePlugin.inst().getCookieConfig().debug;
    }


    private static void setData(final Player player, final MinePlayerProfile data) {

        MineProfilePlugin.inst().getServer().getScheduler().runTask(MineProfilePlugin.inst(), () -> {
            player.getInventory().setContents(data.getInventoryContent());
            if (player.getInventory().getContents() != data.getInventoryContent())
                player.getInventory().setContents(data.getInventoryContent());

            player.getInventory().setArmorContents(data.getArmorContent());
            if (player.getInventory().getArmorContents() != data.getArmorContent())
                player.getInventory().setArmorContents(data.getArmorContent());
            player.getEnderChest().setContents(data.getEnderchestContent());
            if (player.getEnderChest().getContents() != data.getEnderchestContent())
                player.getEnderChest().setContents(data.getEnderchestContent());

            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }

            for (PotionEffect effect : data.getPotionEffects()) {
                player.addPotionEffect(effect);
            }
            player.setLevel(data.getLevel());
            player.setExp(data.getExp());
            player.setMaxHealth(data.getMaxHealth());
            player.setHealth(data.getHealth());
            player.setFoodLevel(data.getFood());
            player.setGameMode(data.getGameMode());
            player.setFireTicks(data.getFireTicks());
            player.getInventory().setHeldItemSlot(data.getSlot());
            new FlyMode(player, data.getFlyInt(), false);
            new VanishMode(player, data.getVanishInt(), false);
        });

    }

    public static void loadData(final Player player) {
        long startTime = System.currentTimeMillis();
        MinePlayerProfile data = ProfileQuery.loadProfile(player);
        setData(player, data);
        ProfileQuery.blockProfile(player.getUniqueId());
        HashDB.authLock.remove(player.getUniqueId());
        MineProfilePlugin.inst().getLogger().info("Load speed: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void saveData(final Player player, final boolean logout, final boolean newTask) {
        long startTime = System.currentTimeMillis();
        MinePlayerProfile data = new MinePlayerProfile(player);
        data.setInventoryContent(player.getInventory().getContents());
        data.setArmorContent(player.getInventory().getArmorContents());
        data.setEnderchestContent(player.getEnderChest().getContents());
        data.setPotionEffects(player.getActivePotionEffects());
        data.setLevel(player.getLevel());
        data.setExp(player.getExp());
        data.setMaxHealth(player.getMaxHealth());
        data.setHealth(player.getHealth());
        data.setFood(player.getFoodLevel());
        data.setGameMode(player.getGameMode());
        data.setFireTicks(player.getFireTicks());
        data.setSlot(player.getInventory().getHeldItemSlot());
        data.setFlyInt(FlyMode.getFlyMode(player));
        data.setVanishInt(VanishMode.getVanishMode(player));
        final MinePlayerProfile savedData = data;
        if (newTask) {
            MineProfilePlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(),
                    () -> ProfileQuery.saveProfile(savedData, logout, startTime));

        } else {
            ProfileQuery.saveProfile(savedData, logout, startTime);
        }
    }

    private static void startPlayerSavingScheduler(final Player p) {
        final long time = 20L * MineProfilePlugin.inst().getCookieConfig().autosavetime;
        final int tid = MineProfilePlugin.inst().getServer().getScheduler()
                .scheduleSyncRepeatingTask(MineProfilePlugin.inst(), () -> {
                    if (HashDB.authLock.contains(p.getUniqueId())) {
                        p.sendMessage("§4Dein Profil ist nicht geladen! Versuche mit /inv load");
                    } else {
                        new InventorySave(p, false, true);
                    }
                }, 240L, time);

        HashDB.bukkitTaskId.put(p.getUniqueId(), tid);
    }

    private static void stopPlayerSavingScheduler(Player p) {
        if (HashDB.bukkitTaskId.containsKey(p.getUniqueId())) {
            int tid = HashDB.bukkitTaskId.get(p.getUniqueId());
            MineProfilePlugin.inst().getServer().getScheduler().cancelTask(tid);
            HashDB.bukkitTaskId.remove(p.getUniqueId());
        }

    }


    public static void loadProfile(Player player) {
        HashDB.authLock.add(player.getUniqueId());
        HashDB.functionState.add(player.getUniqueId());
        new InventoryLoad(player, false);
        if (MineProfilePlugin.inst().getCookieConfig().autosave) {
            startPlayerSavingScheduler(player);
        }
        VanishMode.setVanishedHashMapForPlayer(player);
    }

    public static void unloadProfile(Player player, boolean newThread) {
        if (HashDB.functionState.contains(player.getUniqueId())) {
            if (HashDB.authLock.contains(player.getUniqueId())) {
                HashDB.authLock.remove(player.getUniqueId());
            } else {
                new InventorySave(player, true, newThread);
            }
            if (MineProfilePlugin.inst().getCookieConfig().autosave) {
                stopPlayerSavingScheduler(player);
            }
            HashDB.functionState.remove(player.getUniqueId());
        }

    }

}
