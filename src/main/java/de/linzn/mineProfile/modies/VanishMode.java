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

import de.linzn.mineProfile.utils.HashDB;
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dynmap.bukkit.DynmapPlugin;

import java.util.UUID;

public class VanishMode {

    public VanishMode(final Player player, final int mode, final boolean showInfo) {
        if (mode == 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p != player) {
                    if (!p.hasPermission("mineProfile.team.vanishedUUID")) {
                        p.hidePlayer(player);
                    }
                }
            }
            if (Bukkit.getPluginManager().getPlugin("dynmap") != null) {
                DynmapPlugin.plugin.setPlayerVisiblity(player, false);
            }
            HashDB.vanishedUUID.add(player.getUniqueId());
            if (showInfo)
                player.sendMessage(LanguageDB.changeVanishmode.replace("%s", "AKTIVIERT"));
        } else if (mode == 0) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p != player) {
                    p.showPlayer(player);
                }
            }

            if (Bukkit.getPluginManager().getPlugin("dynmap") != null) {
                DynmapPlugin.plugin.setPlayerVisiblity(player, true);
            }
            HashDB.vanishedUUID.remove(player.getUniqueId());
            if (showInfo)
                player.sendMessage(LanguageDB.changeVanishmode.replace("%s", "DEAKTIVIERT"));
        }
    }

    public static int getVanishMode(Player player) {
        int mode;
        if (HashDB.vanishedUUID.contains(player.getUniqueId())) {
            mode = 1;
        } else {
            mode = 0;
        }
        return mode;

    }

    public static boolean isInVanishMode(Player player) {
        return HashDB.vanishedUUID.contains(player.getUniqueId());

    }

    public static void setVanishedHashMapForPlayer(Player player) {
        if (!player.hasPermission("mineProfile.team.vanishedUUID")) {
            for (UUID aVanishedUUID : HashDB.vanishedUUID) {
                Player vp = Bukkit.getPlayer(aVanishedUUID);
                if (vp != null) {
                    player.hidePlayer(vp);
                }
            }
        }
    }

}
