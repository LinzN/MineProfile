package de.linzn.xeonInventory.classes;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;
import de.linzn.xeonInventory.utils.HashDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.UUID;

public class CVanishMode {

    public CVanishMode(final Player player, final int mode, final boolean showInfo) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
                () -> {
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