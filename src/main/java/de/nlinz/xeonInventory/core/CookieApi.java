package de.nlinz.xeonInventory.core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import de.nlinz.xeonInventory.InventoryPlugin;
import de.nlinz.xeonInventory.classes.CFlyMode;
import de.nlinz.xeonInventory.classes.CVanishMode;
import de.nlinz.xeonInventory.config.I18n;
import de.nlinz.xeonInventory.database.SQLInject;
import de.nlinz.xeonInventory.task.InventoryLoad;
import de.nlinz.xeonInventory.task.InventorySave;
import de.nlinz.xeonInventory.utils.HashDB;
import de.nlinz.xeonInventory.utils.InventoryData;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CookieApi {

    public static void clearInventory(final Player player) {
        InventoryPlugin.inst().getServer().getScheduler().runTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                player.getInventory().setLeggings(new ItemStack(Material.AIR));
                player.getInventory().setBoots(new ItemStack(Material.AIR));
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.setExp(0);
                player.setLevel(0);
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
            }
        });

    }

    public static boolean debug() {
        if (InventoryPlugin.inst().getCookieConfig().debug) {
            return true;
        }
        return false;
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

    public static boolean isPlayerHashLoadet(UUID uuid) {
        if (!HashDB.loginLock.contains(uuid)) {
            return true;
        }
        return false;

    }

    public static void removeHashLoginLock(UUID uuid) {
        HashDB.loginLock.remove(uuid);
    }

    public static void addHashLoginLock(UUID uuid) {
        HashDB.loginLock.add(uuid);
    }

    public static boolean isHashGodlock(UUID uuid) {
        if (HashDB.godLock.contains(uuid)) {
            return true;
        }
        return false;
    }

    public static void setHashGodlock(UUID uuid) {
        HashDB.godLock.add(uuid);
    }

    public static void unsetHashGodlock(UUID uuid) {
        HashDB.godLock.remove(uuid);
    }

    public static boolean isHashTaskID(UUID uuid) {
        if (HashDB.taskID.containsKey(uuid)) {
            return true;
        }
        return false;

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
        if (HashDB.historyID.containsKey(uuid)) {
            return true;
        }
        return false;

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

        InventoryPlugin.inst().getServer().getScheduler().runTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                player.getInventory().setContents(data.getInventoryContent());
                if (player.getInventory().getContents() != data.getInventoryContent())
                    player.getInventory().setContents(data.getInventoryContent());

                player.getInventory().setArmorContents(data.getArmorContent());
                if (player.getInventory().getArmorContents() != data.getArmorContent())
                    player.getInventory().setArmorContents(data.getArmorContent());
                player.getEnderChest().setContents(data.getEnderchestContent());
                if (player.getEnderChest().getContents() != data.getEnderchestContent())
                    player.getEnderChest().setContents(data.getEnderchestContent());

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
                new CFlyMode(player, data.getFlyInt(), false);
                new CVanishMode(player, data.getVanishInt(), false);
            }
        });

    }

    public static void addHistory(final Player player) {
        InventoryPlugin.inst().getServer().getScheduler().runTaskAsynchronously(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                SQLInject.createHistory(player.getUniqueId());
            }
        });

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
        data.setFlyInt(CFlyMode.getFlyMode(player));
        data.setVanishInt(CVanishMode.getVanishMode(player));
        final InventoryData savedData = data;
        if (newTask) {
            InventoryPlugin.inst().getServer().getScheduler().runTaskAsynchronously(InventoryPlugin.inst(), new Runnable() {
                public void run() {
                    SQLInject.saveInventory(savedData, logout);
                }
            });

        } else {
            SQLInject.saveInventory(savedData, logout);
        }
    }

    public static void startPlayerSavingScheduler(final Player p) {
        final long time = 20L * InventoryPlugin.inst().getCookieConfig().autosavetime;
        final int tid = InventoryPlugin.inst().getServer().getScheduler().scheduleSyncRepeatingTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                if (!CookieApi.isPlayerHashLoadet(p.getUniqueId())) {
                    p.sendMessage(I18n.translate("messages.unsuccessLoaded"));
                } else {
                    new InventorySave(p, false);
                }
            }
        }, 240L, time);

        CookieApi.setHashTaskID(p.getUniqueId(), tid);
    }

    public static void stopPlayerSavingScheduler(Player p) {
        if (CookieApi.isHashTaskID(p.getUniqueId())) {
            int tid = CookieApi.getHashTaskID(p.getUniqueId());
            InventoryPlugin.inst().getServer().getScheduler().cancelTask(tid);
            CookieApi.unsetHashTaskID(p.getUniqueId());
        }

    }

    public static void startPlayerHistoryScheduler(final Player p) {
        final long time = 6000L;
        final int tid = InventoryPlugin.inst().getServer().getScheduler().scheduleSyncRepeatingTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                if (CookieApi.isPlayerHashLoadet(p.getUniqueId())) {
                    addHistory(p);
                }
            }
        }, 300L, time);

        CookieApi.setHistoryTaskID(p.getUniqueId(), tid);
    }

    public static void stopPlayerHistoryScheduler(Player p) {
        if (CookieApi.isHistoryTaskID(p.getUniqueId())) {
            int tid = CookieApi.getHistoryTaskID(p.getUniqueId());
            InventoryPlugin.inst().getServer().getScheduler().cancelTask(tid);
            CookieApi.unsetHistoryTaskID(p.getUniqueId());
        }

    }

    public static void startUnlockGod(final Player p, World w) {
        final String world = w.getName();
        Bukkit.getScheduler().runTaskLaterAsynchronously(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                if (p.isOnline() && world.contains(p.getWorld().getName())) {
                    CookieApi.unsetHashGodlock(p.getUniqueId());
                }
            }
        }, 100L);
    }

    public static void onlogin(Player player) {
        CookieApi.addHashLoginLock(player.getUniqueId());
        CookieApi.setHashGodlock(player.getUniqueId());
        new InventoryLoad(player, false);
        if (InventoryPlugin.inst().getCookieConfig().autosave) {
            startPlayerSavingScheduler(player);
        }
        startPlayerHistoryScheduler(player);
        CVanishMode.setVanishedHashMapForPlayer(player);
        startUnlockGod(player, player.getWorld());
    }

    public static void onLeave(Player player) {
        if (!CookieApi.isPlayerHashLoadet(player.getUniqueId())) {
            CookieApi.removeHashLoginLock(player.getUniqueId());
        } else {
            new InventorySave(player, true);
        }
        if (InventoryPlugin.inst().getCookieConfig().autosave) {
            stopPlayerSavingScheduler(player);
        }
        stopPlayerHistoryScheduler(player);

        if (CookieApi.isHashGodlock(player.getUniqueId())) {
            CookieApi.unsetHashGodlock(player.getUniqueId());
        }

    }

}
