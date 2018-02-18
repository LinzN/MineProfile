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
                    if (!p.hasPermission("mineProfile.team.vanish")) {
                        p.hidePlayer(player);
                    }
                }
            }
            if (Bukkit.getPluginManager().getPlugin("dynmap") != null) {
                DynmapPlugin.plugin.setPlayerVisiblity(player, false);
            }
            HashDB.vanishedUUIDs.add(player.getUniqueId());
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
            HashDB.vanishedUUIDs.remove(player.getUniqueId());
            if (showInfo)
                player.sendMessage(LanguageDB.changeVanishmode.replace("%s", "DEAKTIVIERT"));
        }
        if (MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(player.getWorld().getName()) || MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(player.getWorld().getName())) {
            if (showInfo) {
                player.sendMessage(LanguageDB.warningDisabledworldVanish);
            }
        }
    }

    public static int getVanishMode(Player player) {
        int mode;
        if (HashDB.vanishedUUIDs.contains(player.getUniqueId())) {
            mode = 1;
        } else {
            mode = 0;
        }
        return mode;

    }

    public static boolean isInVanishMode(Player player) {
        return HashDB.vanishedUUIDs.contains(player.getUniqueId());

    }

    public static void setVanishedHashMapForPlayer(Player player) {
        if (!player.hasPermission("mineProfile.team.vanish")) {
            for (UUID vanishedUUID : HashDB.vanishedUUIDs) {
                Player vp = Bukkit.getPlayer(vanishedUUID);
                if (vp != null) {
                    player.hidePlayer(vp);
                }
            }
        }
    }

}
