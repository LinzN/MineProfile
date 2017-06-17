package de.linzn.xeonInventory.database;

import java.sql.Connection;
import java.sql.Statement;

import de.linzn.xeonInventory.InventoryPlugin;

public class DatabaseSetup {
	public static boolean create() {
		return mysql();

	}

	public static boolean mysql() {
		String db = InventoryPlugin.inst().getConfig().getString("sql.database");
		String port = InventoryPlugin.inst().getConfig().getString("sql.port");
		String host = InventoryPlugin.inst().getConfig().getString("sql.host");
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = InventoryPlugin.inst().getConfig().getString("sql.username");
		String password = InventoryPlugin.inst().getConfig().getString("sql.password");
		ConnectionFactory factory = new ConnectionFactory(url, username, password);
		ConnectionManager manager = ConnectionManager.DEFAULT;
		ConnectionHandler handler = manager.getHandler("xeonInventory", factory);

		try {
			Connection connection = handler.getConnection();
			String data = "CREATE TABLE IF NOT EXISTS inventoryData (uuid CHAR(36) NOT NULL, lockState TINYINT(1), time BIGINT, inventoryData TEXT, armorData TEXT, enderData TEXT, potionData TEXT, level INT, exp FLOAT, maxHealth DOUBLE, health DOUBLE, food INT, gamemodeData TEXT, fireticks INT, slot INT, fly TINYINT(1), vanish TINYINT(1), PRIMARY KEY (uuid));";
			String history = "CREATE TABLE IF NOT EXISTS inventoryHistory (id int NOT NULL AUTO_INCREMENT, uuid CHAR(36), timeStamp BIGINT, inventoryData TEXT, armorData TEXT, enderData TEXT, potionData TEXT, level INT, exp FLOAT, maxHealth DOUBLE, health DOUBLE, food INT, gamemodeData TEXT, fireticks INT, slot INT, fly TINYINT(1), vanish TINYINT(1), PRIMARY KEY (id));";
			Statement action = connection.createStatement();
			action.executeUpdate(data);
			action.executeUpdate(history);
			action.close();
			handler.release(connection);
			InventoryPlugin.inst().getLogger().info("Database loaded!");
			return true;

		} catch (Exception e) {
			InventoryPlugin.inst().getLogger().warning("Database error!");
			InventoryPlugin.inst().getLogger().warning("============xeonInventory-Error=============");
			InventoryPlugin.inst().getLogger().warning("Unable to connect to database.");
			InventoryPlugin.inst().getLogger().warning("Pls check you mysql connection in config.yml.");
			InventoryPlugin.inst().getLogger().warning("============xeonInventory-Error=============");
			if (InventoryPlugin.inst().getConfig().getBoolean("sql.debugmode")) {
				e.printStackTrace();
			}
			return false;
		}

	}

}
