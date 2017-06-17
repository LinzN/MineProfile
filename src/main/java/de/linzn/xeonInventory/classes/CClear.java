package de.linzn.xeonInventory.classes;

import org.bukkit.entity.Player;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;

public class CClear {

	public CClear(final Player player) {
		InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
				new Runnable() {
					public void run() {
						player.getInventory().clear();
						player.sendMessage(I18n.translate("messages.clearInventory"));
					}
				});
		return;
	}

}
