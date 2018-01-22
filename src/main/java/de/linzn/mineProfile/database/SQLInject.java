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

import de.linzn.mineProfile.utils.InventoryData;
import de.linzn.mineProfile.utils.InventoryHistory;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class SQLInject {

    public static InventoryData loadInventory(Player player) {
        InventoryData data = null;
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {

            String playerUUID = player.getUniqueId().toString();
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT inventoryData, armorData, enderData, potionData, level, exp, maxHealth, health, food, gamemodeData, fireticks, slot, fly, vanish FROM inventoryData WHERE uuid = '"
                            + playerUUID + "';");
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                data = new InventoryData(player);
                data.setInventoryContentFromString(result.getString(1));
                data.setArmorContentFromString(result.getString(2));
                data.setEnderchestContentFromString(result.getString(3));
                data.setPotionEffectFromString(result.getString(4));
                data.setLevel(result.getInt(5));
                data.setExp(result.getFloat(6));
                data.setMaxHealth(result.getDouble(7));
                data.setHealth(result.getDouble(8));
                data.setFood(result.getInt(9));
                data.setGamemodeFromString(result.getString(10));
                data.setFireticks(result.getInt(11));
                data.setSlot(result.getInt(12));
                data.setFly(result.getBoolean(13));
                data.setVanish(result.getBoolean(14));
            }
            result.close();
            sql.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void saveInventory(InventoryData data, Boolean unlock) {
        UUID uuid = data.getPlayerUUID();
        String invData = data.getInventoryContentToString();
        String armorData = data.getArmorContentToString();
        String endData = data.getEnderchestToString();
        String potionData = data.getPotionEffectsToString();
        int level = data.getLevel();
        float exp = data.getExp();
        double maxHealth = data.getMaxHealth();
        double health = data.getHealth();
        int hunger = data.getFood();
        String gamemodeData = data.getGameModeToString();
        int fireticks = data.getFireticks();
        int slot = data.getSlot();
        Date now = new Date();
        int fly = data.getFlyInt();
        int vanish = data.getVanishInt();

        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {

            Connection conn = manager.getConnection("mineProfile");
            if (unlock) {
                PreparedStatement sql = conn.prepareStatement("UPDATE inventoryData SET lockState = '" + 0
                        + "', time = '" + now.getTime() + "', inventoryData = '" + invData + "', armorData = '"
                        + armorData + "', enderData = '" + endData + "', potionData = '" + potionData + "', level = '"
                        + level + "', exp = '" + exp + "', maxHealth = '" + maxHealth + "', health = '" + health
                        + "', food = '" + hunger + "', gamemodeData = '" + gamemodeData + "', fireticks = '" + fireticks
                        + "', slot = '" + slot + "', fly = '" + fly + "', vanish = '" + vanish + "' " + "WHERE uuid = '"
                        + uuid.toString() + "';");
                sql.executeUpdate();
                sql.close();

            } else {
                PreparedStatement sql = conn.prepareStatement(
                        "UPDATE inventoryData SET time = '" + now.getTime() + "', inventoryData = '" + invData
                                + "', armorData = '" + armorData + "', enderData = '" + endData + "', potionData = '"
                                + potionData + "', level = '" + level + "', exp = '" + exp + "', maxHealth = '"
                                + maxHealth + "', health = '" + health + "', food = '" + hunger + "', gamemodeData = '"
                                + gamemodeData + "', fireticks = '" + fireticks + "', slot = '" + slot + "', fly = '"
                                + fly + "', vanish = '" + vanish + "' " + "WHERE uuid = '" + uuid.toString() + "';");
                sql.executeUpdate();
                sql.close();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void lockInventory(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "UPDATE inventoryData SET lockState = '" + 1 + "' WHERE uuid = '" + uuid.toString() + "';");
            sql.executeUpdate();
            sql.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unlockInventory(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {

            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "UPDATE inventoryData SET lockState = '" + 0 + "' WHERE uuid = '" + uuid.toString() + "';");
            sql.executeUpdate();
            sql.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createInventory(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "INSERT INTO inventoryData (uuid, lockState) VALUES ('" + uuid.toString() + "', '" + 1 + "');");
            sql.executeUpdate();
            sql.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasInventory(UUID uuid) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn
                    .prepareStatement("SELECT uuid FROM inventoryData WHERE uuid = '" + uuid.toString() + "';");
            ResultSet result = sql.executeQuery();
            boolean exist = result.next();
            result.close();
            sql.close();
            conn.close();
            return exist;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isInventoryLocked(UUID uuid) {
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
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static InventoryHistory getHistoryByID(int id) {
        InventoryHistory data = null;
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT inventoryData, armorData, enderData, potionData, level, exp, maxHealth, health, food, gamemodeData, fireticks, slot, fly, vanish FROM inventoryHistory WHERE id = '"
                            + id + "';");
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                data = new InventoryHistory();
                data.setInventoryContentFromString(result.getString(1));
                data.setArmorContentFromString(result.getString(2));
                data.setEnderchestContentFromString(result.getString(3));
                data.setPotionEffectFromString(result.getString(4));
                data.setLevel(result.getInt(5));
                data.setExp(result.getFloat(6));
                data.setMaxHealth(result.getDouble(7));
                data.setHealth(result.getDouble(8));
                data.setFood(result.getInt(9));
                data.setGamemodeFromString(result.getString(10));
                data.setFireticks(result.getInt(11));
                data.setSlot(result.getInt(12));
                data.setFly(result.getBoolean(13));
                data.setVanish(result.getBoolean(14));
            }
            result.close();
            sql.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void createHistory(UUID playerUUID) {
        ConnectionManager manager = ConnectionManager.DEFAULT;
        try {
            InventoryHistory data = null;
            Connection conn = manager.getConnection("mineProfile");
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT inventoryData, armorData, enderData, potionData, level, exp, maxHealth, health, food, gamemodeData, fireticks, slot, fly, vanish FROM inventoryData WHERE uuid = '"
                            + playerUUID + "';");
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                data = new InventoryHistory();
                data.setUUID(playerUUID);
                data.setInventoryContentFromString(result.getString(1));
                data.setArmorContentFromString(result.getString(2));
                data.setEnderchestContentFromString(result.getString(3));
                data.setPotionEffectFromString(result.getString(4));
                data.setLevel(result.getInt(5));
                data.setExp(result.getFloat(6));
                data.setMaxHealth(result.getDouble(7));
                data.setHealth(result.getDouble(8));
                data.setFood(result.getInt(9));
                data.setGamemodeFromString(result.getString(10));
                data.setFireticks(result.getInt(11));
                data.setSlot(result.getInt(12));
                data.setFly(result.getBoolean(13));
                data.setVanish(result.getBoolean(14));
            }

            UUID uuid = data.getUUID();
            String invData = data.getInventoryContentToString();
            String armorData = data.getArmorContentToString();
            String endData = data.getEnderchestToString();
            String potionData = data.getPotionEffectsToString();
            int level = data.getLevel();
            float exp = data.getExp();
            double maxHealth = data.getMaxHealth();
            double health = data.getHealth();
            int hunger = data.getFood();
            String gamemodeData = data.getGameModeToString();
            int fireticks = data.getFireticks();
            int slot = data.getSlot();
            Date now = new Date();
            int fly = data.getFlyInt();
            int vanish = data.getVanishInt();

            PreparedStatement sql1 = conn.prepareStatement(
                    "INSERT INTO inventoryHistory (uuid, timeStamp, inventoryData, armorData, enderData, potionData, level, exp, maxHealth, health, food, gamemodeData, fireticks, slot, fly, vanish) VALUES ('"
                            + uuid + "', '" + now.getTime() + "', '" + invData + "', '" + armorData + "', '" + endData
                            + "', '" + potionData + "', '" + level + "', '" + exp + "', '" + maxHealth + "', '" + health
                            + "', '" + hunger + "', '" + gamemodeData + "', '" + fireticks + "', '" + slot + "', '"
                            + fly + "', '" + vanish + "');");
            sql1.executeUpdate();
            sql1.close();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
