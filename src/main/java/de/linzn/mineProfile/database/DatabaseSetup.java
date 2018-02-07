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

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static boolean create() {
        return mysql();

    }

    public static boolean mysql() {
        String db = MineProfilePlugin.inst().getConfig().getString("sql.database");
        String port = MineProfilePlugin.inst().getConfig().getString("sql.port");
        String host = MineProfilePlugin.inst().getConfig().getString("sql.host");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
        String username = MineProfilePlugin.inst().getConfig().getString("sql.username");
        String password = MineProfilePlugin.inst().getConfig().getString("sql.password");
        ConnectionFactory factory = new ConnectionFactory(url, username, password);
        ConnectionManager manager = ConnectionManager.DEFAULT;
        ConnectionHandler handler = manager.getHandler("mineProfile", factory);

        try {
            Connection connection = handler.getConnection();
            String data = "CREATE TABLE IF NOT EXISTS inventoryData (uuid CHAR(36) NOT NULL, lockState TINYINT(1), time BIGINT, inventoryData TEXT, armorData TEXT, enderData TEXT, potionData TEXT, level INT, exp FLOAT, maxHealth DOUBLE, health DOUBLE, food INT, gamemodeData TEXT, fireticks INT, slot INT, fly TINYINT(1), vanishedUUID TINYINT(1), PRIMARY KEY (uuid));";
            String history = "CREATE TABLE IF NOT EXISTS inventoryHistory (id int NOT NULL AUTO_INCREMENT, uuid CHAR(36), timeStamp BIGINT, inventoryData TEXT, armorData TEXT, enderData TEXT, potionData TEXT, level INT, exp FLOAT, maxHealth DOUBLE, health DOUBLE, food INT, gamemodeData TEXT, fireticks INT, slot INT, fly TINYINT(1), vanishedUUID TINYINT(1), PRIMARY KEY (id));";
            Statement action = connection.createStatement();
            action.executeUpdate(data);
            action.executeUpdate(history);
            action.close();
            handler.release(connection);
            MineProfilePlugin.inst().getLogger().info("Database loaded!");
            return true;

        } catch (Exception e) {
            MineProfilePlugin.inst().getLogger().warning("Database error!");
            MineProfilePlugin.inst().getLogger().warning("============mineProfile-Error=============");
            MineProfilePlugin.inst().getLogger().warning("Unable to connect to database.");
            MineProfilePlugin.inst().getLogger().warning("Pls check you mysql connection in config.yml.");
            MineProfilePlugin.inst().getLogger().warning("============mineProfile-Error=============");
            if (MineProfilePlugin.inst().getConfig().getBoolean("sql.debugmode")) {
                e.printStackTrace();
            }
            return false;
        }

    }

}
