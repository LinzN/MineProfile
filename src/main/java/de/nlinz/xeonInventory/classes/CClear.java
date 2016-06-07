package de.nlinz.xeonInventory.classes;

import org.bukkit.entity.Player;

import de.nlinz.xeonInventory.InventoryPlugin;
import de.nlinz.xeonInventory.config.I18n;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CClear {

    public CClear(final Player player) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                player.getInventory().clear();
                player.sendMessage(I18n.translate("messages.clearInventory"));
            }
        });
        return;
    }

}
