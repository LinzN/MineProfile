package de.linzn.xeonInventory.classes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;


public class CGive {

	public CGive(final Player player, final ItemStack value) {
		InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
				new Runnable() {
					public void run() {
						player.getInventory().addItem(value);
						player.sendMessage(I18n.translate("messages.createItemstack", value.getType().name(),
								value.getData(), value.getAmount()));
					}
				});
		return;
	}

}
