package de.linzn.xeonInventory.classes;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;
import org.bukkit.entity.Player;

public class CFlyMode {

    public CFlyMode(final Player player, final int mode, final boolean showInfo) {
        switch (InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
                () -> {
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
                })) {
        }
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
        return player.getAllowFlight();

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
