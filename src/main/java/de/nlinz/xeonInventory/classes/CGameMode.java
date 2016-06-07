package de.nlinz.xeonInventory.classes;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

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

public class CGameMode {

    public CGameMode(final Player player, final int mode, final boolean showInfo) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(), new Runnable() {
            public void run() {
                if (mode == 0) {
                    player.setGameMode(GameMode.SURVIVAL);
                    if (showInfo)
                        player.sendMessage(I18n.translate("messages.changeGamemode", "SURVIVAL"));
                } else if (mode == 1) {
                    player.setGameMode(GameMode.CREATIVE);
                    if (showInfo)
                        player.sendMessage(I18n.translate("messages.changeGamemode", "CREATIVE"));
                } else if (mode == 2) {
                    player.setGameMode(GameMode.ADVENTURE);
                    if (showInfo)
                        player.sendMessage(I18n.translate("messages.changeGamemode", "ADVENTURE"));
                } else if (mode == 3) {
                    player.setGameMode(GameMode.SPECTATOR);
                    if (showInfo)
                        player.sendMessage(I18n.translate("messages.changeGamemode", "SPECTATOR"));
                }
            }
        });
        return;
    }

    public static int getGameMode(Player player) {
        int mode;
        switch (player.getGameMode()) {
            case SURVIVAL:
                mode = 0;
                break;
            case CREATIVE:
                mode = 1;
                break;
            case ADVENTURE:
                mode = 2;
                break;
            case SPECTATOR:
                mode = 3;
                break;
            default:
                mode = 0;
                break;

        }
        return mode;

    }

    public static String getGameModeInfo(Player player) {
        String mode;
        switch (player.getGameMode()) {
            case SURVIVAL:
                mode = "SURVIVAL";
                break;
            case CREATIVE:
                mode = "CREATIVE";
                break;
            case ADVENTURE:
                mode = "ADVENTURE";
                break;
            case SPECTATOR:
                mode = "SPECTATOR";
                break;
            default:
                mode = "SURVIVAL";
                break;

        }
        return mode;

    }

}
