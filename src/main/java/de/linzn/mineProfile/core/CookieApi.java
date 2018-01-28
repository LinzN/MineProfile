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
import de.linzn.mineProfile.modies.FlyMode;
import de.linzn.mineProfile.modies.VanishMode;
import de.linzn.mineProfile.config.I18n;
import de.linzn.mineProfile.database.SQLInject;
import de.linzn.mineProfile.task.InventoryLoad;
import de.linzn.mineProfile.task.InventorySave;
import de.linzn.mineProfile.utils.HashDB;
import de.linzn.mineProfile.utils.InventoryData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CookieApi {


    public static boolean debug() {
        return MineProfilePlugin.inst().getCookieConfig().debug;
    }

    public static void log(String text) {
        Bukkit.getLogger().info(text);
    }

    public static void errorlog(String text) {
        Bukkit.getLogger().warning(text);
    }

    public static void sendInfo(Player player, String text) {
        player.sendMessage(text);
    }

    public static boolean isPlayerHashLoaded(UUID uuid) {
        return !HashDB.loginLock.contains(uuid);

    }

    public static void removeHashLoginLock(UUID uuid) {
        HashDB.loginLock.remove(uuid);
    }

    public static void addHashLoginLock(UUID uuid) {
        HashDB.loginLock.add(uuid);
    }

    public static boolean isHashGodlock(UUID uuid) {
        return HashDB.godLock.contains(uuid);
    }

    public static void setHashGodlock(UUID uuid) {
        HashDB.godLock.add(uuid);
    }

    public static void unsetHashGodlock(UUID uuid) {
        HashDB.godLock.remove(uuid);
    }

    public static boolean isHashTaskID(UUID uuid) {
        return HashDB.taskID.containsKey(uuid);

    }

    public static int getHashTaskID(UUID uuid) {
        return HashDB.taskID.get(uuid);

    }

    public static void setHashTaskID(UUID uuid, int id) {
        HashDB.taskID.put(uuid, id);
    }

    public static void unsetHashTaskID(UUID uuid) {
        HashDB.taskID.remove(uuid);
    }

    public static boolean isHistoryTaskID(UUID uuid) {
        return HashDB.historyID.containsKey(uuid);

    }

    public static int getHistoryTaskID(UUID uuid) {
        return HashDB.historyID.get(uuid);

    }

    public static void setHistoryTaskID(UUID uuid, int id) {
        HashDB.historyID.put(uuid, id);
    }

    public static void unsetHistoryTaskID(UUID uuid) {
        HashDB.historyID.remove(uuid);
    }

    public static void setData(final Player player, final InventoryData data) {

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
            player.setFireTicks(data.getFireticks());
            player.getInventory().setHeldItemSlot(data.getSlot());
            new FlyMode(player, data.getFlyInt(), false);
            new VanishMode(player, data.getVanishInt(), false);
        });

    }

    public static void addHistory(final Player player) {
        MineProfilePlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(), () -> SQLInject.createHistory(player.getUniqueId()));

    }

    public static void loadData(final Player player) {
        InventoryData data = SQLInject.loadInventory(player);
        setData(player, data);
        SQLInject.lockInventory(player.getUniqueId());
        removeHashLoginLock(player.getUniqueId());
    }

    public static void saveData(final Player player, final boolean logout, final boolean newTask) {
        InventoryData data = new InventoryData(player);
        data.setInventoryContent(player.getInventory().getContents());
        data.setArmorContent(player.getInventory().getArmorContents());
        data.setEnderchestContent(player.getEnderChest().getContents());
        data.setPotionEffect(player.getActivePotionEffects());
        data.setLevel(player.getLevel());
        data.setExp(player.getExp());
        data.setMaxHealth(player.getMaxHealth());
        data.setHealth(player.getHealth());
        data.setFood(player.getFoodLevel());
        data.setGamemode(player.getGameMode());
        data.setFireticks(player.getFireTicks());
        data.setSlot(player.getInventory().getHeldItemSlot());
        data.setFlyInt(FlyMode.getFlyMode(player));
        data.setVanishInt(VanishMode.getVanishMode(player));
        final InventoryData savedData = data;
        if (newTask) {
            MineProfilePlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(),
                    () -> SQLInject.saveInventory(savedData, logout));

        } else {
            SQLInject.saveInventory(savedData, logout);
        }
    }

    public static void startPlayerSavingScheduler(final Player p) {
        final long time = 20L * MineProfilePlugin.inst().getCookieConfig().autosavetime;
        final int tid = MineProfilePlugin.inst().getServer().getScheduler()
                .scheduleSyncRepeatingTask(MineProfilePlugin.inst(), () -> {
                    if (!CookieApi.isPlayerHashLoaded(p.getUniqueId())) {
                        p.sendMessage(I18n.translate("messages.unsuccessLoaded"));
                    } else {
                        new InventorySave(p, false);
                    }
                }, 240L, time);

        CookieApi.setHashTaskID(p.getUniqueId(), tid);
    }

    public static void stopPlayerSavingScheduler(Player p) {
        if (CookieApi.isHashTaskID(p.getUniqueId())) {
            int tid = CookieApi.getHashTaskID(p.getUniqueId());
            MineProfilePlugin.inst().getServer().getScheduler().cancelTask(tid);
            CookieApi.unsetHashTaskID(p.getUniqueId());
        }

    }

    public static void startPlayerHistoryScheduler(final Player p) {
        final long time = 6000L;
        final int tid = MineProfilePlugin.inst().getServer().getScheduler()
                .scheduleSyncRepeatingTask(MineProfilePlugin.inst(), () -> {
                    if (CookieApi.isPlayerHashLoaded(p.getUniqueId())) {
                        addHistory(p);
                    }
                }, 300L, time);

        CookieApi.setHistoryTaskID(p.getUniqueId(), tid);
    }

    public static void stopPlayerHistoryScheduler(Player p) {
        if (CookieApi.isHistoryTaskID(p.getUniqueId())) {
            int tid = CookieApi.getHistoryTaskID(p.getUniqueId());
            MineProfilePlugin.inst().getServer().getScheduler().cancelTask(tid);
            CookieApi.unsetHistoryTaskID(p.getUniqueId());
        }

    }

    public static void startUnlockGod(final Player p, World w) {
        final String world = w.getName();
        Bukkit.getScheduler().runTaskLaterAsynchronously(MineProfilePlugin.inst(), () -> {
            if (p.isOnline() && world.contains(p.getWorld().getName())) {
                CookieApi.unsetHashGodlock(p.getUniqueId());
            }
        }, 100L);
    }

    public static void onlogin(Player player) {
        CookieApi.addHashLoginLock(player.getUniqueId());
        CookieApi.setHashGodlock(player.getUniqueId());
        new InventoryLoad(player, false);
        if (MineProfilePlugin.inst().getCookieConfig().autosave) {
            startPlayerSavingScheduler(player);
        }
        startPlayerHistoryScheduler(player);
        VanishMode.setVanishedHashMapForPlayer(player);
        startUnlockGod(player, player.getWorld());
    }

    public static void onLeave(Player player) {
        if (!CookieApi.isPlayerHashLoaded(player.getUniqueId())) {
            CookieApi.removeHashLoginLock(player.getUniqueId());
        } else {
            new InventorySave(player, true);
        }
        if (MineProfilePlugin.inst().getCookieConfig().autosave) {
            stopPlayerSavingScheduler(player);
        }
        stopPlayerHistoryScheduler(player);

        if (CookieApi.isHashGodlock(player.getUniqueId())) {
            CookieApi.unsetHashGodlock(player.getUniqueId());
        }

    }

    public static boolean isArtificiallyItem(ItemStack item) {
        if ((item != null) && (item.hasItemMeta())) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                for (String s : meta.getLore()) {
                    if (s.startsWith("Artificially")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack setArtificiallyItem(String player, ItemStack item) {
        if ((item != null) && (item.getType() != Material.AIR) && (item.getType() != Material.BOOK_AND_QUILL)
                && (!isArtificiallyItem(item))) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (meta.hasLore()) {
                lore = meta.getLore();
            }
            lore.add(0, "Artificially");
            lore.add(1, player);
            lore.add(2, new SimpleDateFormat("dd.MM.yyyy '-' HH:mm").format(new Date()));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

}
