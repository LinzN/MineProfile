/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.modies;

import de.linzn.mineProfile.MineProfilePlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChangeWeather {

    public ChangeWeather(final Player player, final String weatherName) {

        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
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
                });
    }

}
