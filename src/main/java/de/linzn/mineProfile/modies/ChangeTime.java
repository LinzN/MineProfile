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
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class ChangeTime {

    public ChangeTime(final Player player, final String timeName) {

        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    long time;
                    String name = timeName.toUpperCase();
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
                            name = "DAY";
                            break;
                    }
                    World world = player.getWorld();
                    world.setTime(time);
                    player.sendMessage(LanguageDB.changeTime.replace("%s", name));
                });
    }

}
