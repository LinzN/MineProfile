package de.nlinz.xeonInventory.classes;

import org.bukkit.World;
import org.bukkit.entity.Player;

import de.nlinz.xeonInventory.InventoryPlugin;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CTime {

    public CTime(final Player player, final String timeName) {

        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                long time = 0;
                switch (timeName.toUpperCase()) {
                    case "DAY":
                        time = 4284;
                        break;
                    case "NIGHT":
                        time = 18000;
                        break;
                    case "MORNING":
                        time = 22550;
                        break;
                    case "EVENING":
                        time = 11615;
                        break;
                    default:
                        time = 1000;
                        break;
                }
                World world = player.getWorld();
                world.setTime(time);
            }
        });
        return;
    }

}
