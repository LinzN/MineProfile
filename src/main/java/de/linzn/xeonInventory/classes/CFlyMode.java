package de.linzn.xeonInventory.classes;

import org.bukkit.entity.Player;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;

public class CFlyMode {

	public CFlyMode(final Player player, final int mode, final boolean showInfo) {
		InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
				new Runnable() {
					public void run() {
						if (mode == 0) {
							player.setAllowFlight(false);
							if (showInfo)
								player.sendMessage(I18n.translate("messages.changeFlymode", "DEAKTIVIERT"));
							return;
						}
						if (mode == 1) {
							player.setAllowFlight(true);
							if (showInfo)
								player.sendMessage(I18n.translate("messages.changeFlymode", "AKTIVIERT"));
							return;
						}
					}
				});
		return;
	}

	public static int getFlyMode(Player player) {
		int mode;
		if (player.getAllowFlight()) {
			mode = 1;
		} else {
			mode = 0;
		}
		return mode;

	}

	public static boolean isInFlyMode(Player player) {
		boolean mode;
		if (player.getAllowFlight()) {
			mode = true;
		} else {
			mode = false;
		}
		return mode;

	}

	public static String getFlyModeInfo(Player player) {
		String mode;
		if (player.getAllowFlight()) {
			mode = "On";
		} else {
			mode = "Off";
		}
		return mode;

	}

}
