/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.database;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.utils.MinePlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class ProfileQuery {

    public static MinePlayerProfile loadProfile(Player player) {
        MinePlayerProfile data = null;
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {

            String playerUUID = player.getUniqueId().toString();
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT inventoryData, armorData, enderData, potionData, level, exp, maxHealth, health, food, gamemodeData, fireticks, slot, fly, vanish FROM inventoryData WHERE uuid = '"
                            + playerUUID + "';");
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                data = new MinePlayerProfile(player);
                data.setInventoryContent(result.getString(1));
                data.setArmorContent(result.getString(2));
                data.setEnderchestContent(result.getString(3));
                data.setPotionEffects(result.getString(4));
                data.setLevel(result.getInt(5));
                data.setExp(result.getFloat(6));
                data.setMaxHealth(result.getDouble(7));
                data.setHealth(result.getDouble(8));
                data.setFood(result.getInt(9));
                data.setGameMode(result.getString(10));
                data.setFireTicks(result.getInt(11));
                data.setSlot(result.getInt(12));
                data.setFly(result.getBoolean(13));
                data.setVanish(result.getBoolean(14));
            }
            result.close();
            sql.close();
            manager.release("mineProfile", conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveProfile(MinePlayerProfile data, Boolean unlock, long startTime, boolean newThread) {
        UUID playerUUID = data.getPlayerUUID();

        String invData = data.getInventoryContentSerialized();
        String armorData = data.getArmorContentSerialized();
        String endData = data.getEnderchestContentSerialized();
        String potionData = data.getPotionEffectsSerialized();

        int level = data.getLevel();
        float exp = data.getExp();
        double maxHealth = data.getMaxHealth();
        double health = data.getHealth();
        int hunger = data.getFood();
        String gameModeData = data.getGameModeToString();
        int fireTicks = data.getFireTicks();
        int slot = data.getSlot();
        Date now = new Date();
        int fly = data.getFlyInt();
        int vanish = data.getVanishInt();

        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {

            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement("UPDATE inventoryData SET lockState = ?, time = ?, inventoryData = ?, armorData = ?, enderData = ?, potionData = ?, level = ?, exp = ?, maxHealth = ?, health = ?, food = ?, gamemodeData = ?, fireticks = ?, slot = ?, fly = ?, vanish = ? WHERE uuid = ?;");
            /* Set data */
            if (unlock) {
                sql.setInt(1, 0);
            } else {
                sql.setInt(1, 1);
            }
            sql.setLong(2, now.getTime());
            sql.setString(3, invData);
            sql.setString(4, armorData);
            sql.setString(5, endData);
            sql.setString(6, potionData);
            sql.setInt(7, level);
            sql.setFloat(8, exp);
            sql.setDouble(9, maxHealth);
            sql.setDouble(10, health);
            sql.setInt(11, hunger);
            sql.setString(12, gameModeData);
            sql.setInt(13, fireTicks);
            sql.setInt(14, slot);
            sql.setInt(15, fly);
            sql.setInt(16, vanish);
            /* Where statement */
            sql.setString(17, playerUUID.toString());
            /* Execute query*/
            sql.executeUpdate();
            sql.close();
            manager.release("mineProfile", conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MineProfilePlugin.inst().getLogger().info("Save speed: " + (System.currentTimeMillis() - startTime) + "ms");
        if (newThread) {
            Bukkit.getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(), () -> createBackup(data));
        } else {
            createBackup(data);
        }
    }

    public static void blockProfile(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "UPDATE inventoryData SET lockState = '" + 1 + "' WHERE uuid = '" + uuid.toString() + "';");
            sql.executeUpdate();
            sql.close();
            manager.release("mineProfile", conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createProfile(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "INSERT INTO inventoryData (uuid, lockState) VALUES ('" + uuid.toString() + "', '" + 1 + "');");
            sql.executeUpdate();
            sql.close();
            manager.release("mineProfile", conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasProfile(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn
                    .prepareStatement("SELECT uuid FROM inventoryData WHERE uuid = '" + uuid.toString() + "';");
            ResultSet result = sql.executeQuery();
            boolean exist = result.next();
            result.close();
            sql.close();
            manager.release("mineProfile", conn);
            return exist;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isProfileBlocked(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn
                    .prepareStatement("SELECT lockState FROM inventoryData WHERE uuid = '" + uuid.toString() + "';");
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                int dataLock = result.getInt(1);
                return dataLock != 0;
            }
            result.close();
            sql.close();
            manager.release("mineProfile", conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    private static void createBackup(MinePlayerProfile data) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            UUID uuid = data.getPlayerUUID();
            String invData = data.getInventoryContentSerialized();
            String armorData = data.getArmorContentSerialized();
            String endData = data.getEnderchestContentSerialized();
            String potionData = data.getPotionEffectsSerialized();
            int level = data.getLevel();
            float exp = data.getExp();
            double maxHealth = data.getMaxHealth();
            double health = data.getHealth();
            int hunger = data.getFood();
            String gameModeData = data.getGameModeToString();
            int fireTicks = data.getFireTicks();
            int slot = data.getSlot();
            Date now = new Date();
            int fly = data.getFlyInt();
            int vanish = data.getVanishInt();

            PreparedStatement sql1 = conn.prepareStatement(
                    "INSERT INTO inventoryHistory (uuid, timeStamp, inventoryData, armorData, enderData,  potionData, level, exp, maxHealth, health, food, gamemodeData, fireticks, slot, fly, vanish) VALUES ('"
                            + uuid + "', '" + now.getTime() + "', ?, ?, ?, '" + potionData + "', '" + level + "', '" + exp + "', '" + maxHealth + "', '" + health
                            + "', '" + hunger + "', '" + gameModeData + "', '" + fireTicks + "', '" + slot + "', '"
                            + fly + "', '" + vanish + "');");
            sql1.setString(1, invData);
            sql1.setString(2, armorData);
            sql1.setString(3, endData);
            sql1.executeUpdate();
            sql1.close();
            manager.release("mineProfile", conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
