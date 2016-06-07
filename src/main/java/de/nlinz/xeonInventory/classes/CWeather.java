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

public class CWeather {

    public CWeather(final Player player, final String weatherName) {

        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                final World world = player.getWorld();
                switch (weatherName.toUpperCase()) {
                    case "SUN":
                        world.setThundering(false);
                        world.setStorm(false);
                        break;
                    case "RAIN":
                        world.setStorm(true);
                        break;
                    case "THUNDER":
                        world.setThundering(true);
                        break;
                    default:
                        world.setThundering(false);
                        world.setStorm(false);
                        break;
                }
            }
        });
        return;
    }

}
