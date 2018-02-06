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
import de.linzn.mineProfile.database.SQLInject;
import de.linzn.mineProfile.utils.HashDB;
import org.bukkit.entity.Player;

public class InventoryLoad extends SQLInject {

    public InventoryLoad(Player player, boolean isCommand) {
        load(player, isCommand);
    }

    private void load(final Player player, final boolean isCommand) {
        MineProfilePlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(), () -> {
            int loopNumber = 0;
            boolean loaded = false;

            if (!SQLInject.hasInventory(player.getUniqueId())) {
                MineProfilePlugin.inst().getLogger().info("Create: " + player.getName());
                SQLInject.createInventory(player.getUniqueId());
                HashDB.authLock.remove(player.getUniqueId());
                player.sendMessage("§aDein Profil wurde erstellt.");
                return;
            }
            MineProfilePlugin.inst().getLogger().info("Load: " + player.getName());
            //PlayerDataAPI.preparePlayerData(player);

            while (loopNumber <= 10 && !loaded) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (PlayerDataAPI.debug()) {
                    MineProfilePlugin.inst().getLogger().info("Load loop " + loopNumber + " for: " + player.getName());
                }

                if (!SQLInject.isInventoryLocked(player.getUniqueId())) {
                    PlayerDataAPI.loadData(player);
                }

                if (!HashDB.authLock.contains(player.getUniqueId())) {

                    loaded = true;
                    player.sendMessage("§aDein Profil wurde geladen.");

                }
                loopNumber++;

            }
            if (isCommand) {
                PlayerDataAPI.loadData(player);
                player.sendMessage("§aDein Profil wurde nachgeladen.");

            }

            if (HashDB.authLock.contains(player.getUniqueId())) {
                if (PlayerDataAPI.debug()) {
                    MineProfilePlugin.inst().getLogger().severe("Failed to load data for: " + player.getName());
                }
                player.sendMessage("§4Ladefehler bei deinem Profil. Versuche mit /inv load");
            }
        });
    }

}
