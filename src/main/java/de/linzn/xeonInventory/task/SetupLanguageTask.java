package de.linzn.xeonInventory.task;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.CookieConfig;
import de.linzn.xeonInventory.config.I18n;

public class SetupLanguageTask implements Runnable {

	private InventoryPlugin plugin;
	private CookieConfig config;

	public SetupLanguageTask(InventoryPlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.getCookieConfig();
	}

	@Override
	public void run() {
		if (!config.loaded) {
			plugin.getServer().getScheduler().runTaskLater(plugin, this, 5L);
		} else {
			plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
				@Override
				public void run() {
					new I18n(plugin, config.locale);
				}
			});
		}
	}

}
