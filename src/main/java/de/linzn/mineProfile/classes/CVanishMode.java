/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.classes;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.config.I18n;
import de.linzn.mineProfile.utils.HashDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dynmap.bukkit.DynmapPlugin;

import java.util.Iterator;
import java.util.UUID;

public class CVanishMode {

    public CVanishMode(final Player player, final int mode, final boolean showInfo) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    if (mode == 1) {

                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p != player) {
                                if (!p.hasPermission("mineProfile.team.vanish")) {
                                    p.hidePlayer(player);
                                }
                            }
                        }
                        if (Bukkit.getPluginManager().getPlugin("dynmap") != null) {
                            DynmapPlugin.plugin.setPlayerVisiblity(player, false);
                        }
                        HashDB.vanish.add(player.getUniqueId());
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeVanishmode", "AKTIVIERT"));
                    } else if (mode == 0) {

                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p != player) {
                                p.showPlayer(player);
                            }
                        }

                        if (Bukkit.getPluginManager().getPlugin("dynmap") != null) {
                            DynmapPlugin.plugin.setPlayerVisiblity(player, true);
                        }
                        HashDB.vanish.remove(player.getUniqueId());
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeVanishmode", "DEAKTIVIERT"));
                    }
                });
    }

    public static int getVanishMode(Player player) {
        int mode;
        if (HashDB.vanish.contains(player.getUniqueId())) {
            mode = 1;
        } else {
            mode = 0;
        }
        return mode;

    }

    public static boolean isInVanishMode(Player player) {
        return HashDB.vanish.contains(player.getUniqueId());

    }

    public static void setVanishedHashMapForPlayer(Player player) {
        if (!player.hasPermission("mineProfile.team.vanish")) {
            Iterator<UUID> iterator = HashDB.vanish.iterator();
            while (iterator.hasNext()) {
                Player vp = Bukkit.getPlayer(iterator.next());
                if (vp != null) {
                    player.hidePlayer(vp);
                }
            }
        }
    }

}
