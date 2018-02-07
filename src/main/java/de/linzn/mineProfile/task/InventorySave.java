/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.task;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.core.PlayerDataAPI;
import de.linzn.mineProfile.database.ProfileQuery;
import org.bukkit.entity.Player;

public class InventorySave extends ProfileQuery {

    public InventorySave(Player player, boolean logout) {
        save(player, logout);
    }

    private void save(Player player, boolean logout) {
        if (MineProfilePlugin.inst().getCookieConfig().debug) {
            if (logout) {
                player.sendMessage("§aDein Profil wurde gespeichert!");
                MineProfilePlugin.inst().getLogger().info("Save: " + player.getName());
            } else {
                MineProfilePlugin.inst().getLogger().info("Autosaving: " + player.getName());
            }
        }
        PlayerDataAPI.saveData(player, logout, true);
    }
}
