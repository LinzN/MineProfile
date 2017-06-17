package de.linzn.xeonInventory.task;

import org.bukkit.entity.Player;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.classes.CFlyMode;
import de.linzn.xeonInventory.classes.CGameMode;
import de.linzn.xeonInventory.classes.CVanishMode;
import de.linzn.xeonInventory.config.I18n;
import de.linzn.xeonInventory.core.CookieApi;
import de.linzn.xeonInventory.database.SQLInject;

public class InventoryLoad extends SQLInject {

	public InventoryLoad(Player player, boolean isCommand) {
		load(player, isCommand);
	}

	public void load(final Player player, final boolean isCommand) {
		InventoryPlugin.inst().getServer().getScheduler().runTaskAsynchronously(InventoryPlugin.inst(), new Runnable() {
			public void run() {
				int loopNumber = 0;
				boolean loaded = false;

				if (!SQLInject.hasInventory(player.getUniqueId())) {
					CookieApi.log("Create: " + player.getName());
					SQLInject.createInventory(player.getUniqueId());
					CookieApi.removeHashLoginLock(player.getUniqueId());
					CookieApi.sendInfo(player, I18n.translate("messages.successLoaded"));
					CookieApi.sendInfo(player, I18n.translate("messages.loginStats", CGameMode.getGameModeInfo(player),
							CFlyMode.getFlyModeInfo(player), CVanishMode.getVanishModeInfo(player)));
					return;
				}
				CookieApi.log("Load: " + player.getName());
				// CookieApi.clearInventory(this.player);

				while (loopNumber <= 10 && loaded == false) {
					try {
						Thread.sleep(loopNumber * 150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (CookieApi.debug()) {
						CookieApi.log("Load loop " + loopNumber + " for: " + player.getName());
					}

					if (!SQLInject.isInventoryLocked(player.getUniqueId())) {
						CookieApi.loadData(player);

					}

					if (CookieApi.isPlayerHashLoadet(player.getUniqueId())) {

						loaded = true;
						CookieApi.sendInfo(player, I18n.translate("messages.successLoaded"));
						CookieApi.sendInfo(player,
								I18n.translate("messages.loginStats", CGameMode.getGameModeInfo(player),
										CFlyMode.getFlyModeInfo(player), CVanishMode.getVanishModeInfo(player)));

					}
					loopNumber++;

				}
				if (isCommand) {
					CookieApi.loadData(player);
					CookieApi.sendInfo(player, I18n.translate("messages.successLoaded"));
					CookieApi.sendInfo(player, I18n.translate("messages.loginStats", CGameMode.getGameModeInfo(player),
							CFlyMode.getFlyModeInfo(player), CVanishMode.getVanishModeInfo(player)));

				}

				if (!CookieApi.isPlayerHashLoadet(player.getUniqueId())) {
					if (CookieApi.debug()) {
						CookieApi.errorlog("Failed to load data for: " + player.getName());
					}
					CookieApi.sendInfo(player, I18n.translate("messages.unsuccessLoaded"));
				}
			}
		});
	}

}
