package de.nlinz.xeonInventory.classes;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.nlinz.xeonInventory.InventoryPlugin;
import de.nlinz.xeonInventory.config.I18n;
import de.nlinz.xeonInventory.utils.HashDB;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CVanishMode {

    public CVanishMode(final Player player, final int mode, final boolean showInfo) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                if (mode == 1) {

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p != player) {
                            if (!p.hasPermission("xeonInventory.team.vanish")) {
                                p.hidePlayer(player);
                            }
                        }
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

                    HashDB.vanish.remove(player.getUniqueId());
                    if (showInfo)
                        player.sendMessage(I18n.translate("messages.changeVanishmode", "DEAKTIVIERT"));
                }
            }
        });
        return;
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
        boolean isVanish;
        if (HashDB.vanish.contains(player.getUniqueId())) {
            isVanish = true;
        } else {
            isVanish = false;
        }
        return isVanish;

    }

    public static void setVanishedHashMapForPlayer(Player player) {
        if (!player.hasPermission("xeonInventory.team.vanish")) {
            Iterator<UUID> iterator = HashDB.vanish.iterator();
            while (iterator.hasNext()) {
                Player vp = Bukkit.getPlayer(iterator.next());
                if (vp != null) {
                    player.hidePlayer(vp);
                }
            }
        }
    }

    public static String getVanishModeInfo(Player player) {
        String mode;
        if (HashDB.vanish.contains(player.getUniqueId())) {
            mode = "On";
        } else {
            mode = "Off";
        }
        return mode;

    }
}
