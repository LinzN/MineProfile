package de.nlinz.xeonInventory.classes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

public class CGive {

    public CGive(final Player player, final ItemStack value) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                player.getInventory().addItem(value);
                player.sendMessage(I18n.translate("messages.createItemstack", value.getType().name(), value.getData(), value.getAmount()));
            }
        });
        return;
    }

}
