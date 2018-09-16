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

import de.linzn.mineLib.actionBar.MineActionBar;
import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.core.PlayerDataAPI;
import de.linzn.mineProfile.database.ProfileQuery;
import de.linzn.mineProfile.modies.VanishMode;
import de.linzn.mineProfile.utils.HashDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryLoad extends ProfileQuery {

    public InventoryLoad(Player player, boolean isCommand) {
        load(player, isCommand);
    }

    private void load(final Player player, final boolean isCommand) {
        MineProfilePlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(), () -> {
            int loopNumber = 0;
            boolean loaded = false;

            if (!ProfileQuery.hasProfile(player.getUniqueId())) {
                MineProfilePlugin.inst().getLogger().info("Create: " + player.getName());
                ProfileQuery.createProfile(player.getUniqueId());
                HashDB.authLock.remove(player.getUniqueId());
                Bukkit.getScheduler().runTask(MineProfilePlugin.inst(), () -> {
                    new VanishMode(player, 0, false);
                    player.sendMessage("§aDein Profil wurde erstellt.");
                });
                return;
            }
            MineProfilePlugin.inst().getLogger().info("Load: " + player.getName());

            while (loopNumber <= 15 && !loaded) {
                int sleepTime = 100;
                if (loopNumber > 10){
                    sleepTime = 100 * (loopNumber - 9);
                }
                try {
                        Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (PlayerDataAPI.debug()) {
                    MineProfilePlugin.inst().getLogger().info("Load try " + loopNumber + ": " + player.getName() + " (" +sleepTime+"ms)");
                }

                if (!ProfileQuery.isProfileBlocked(player.getUniqueId())) {
                    PlayerDataAPI.loadData(player);
                }

                if (!HashDB.authLock.contains(player.getUniqueId())) {
                    loaded = true;
                    new MineActionBar("§9§lDein Profil wurde geladen.").send(player);
                }
                loopNumber++;

            }
            if (isCommand && !loaded) {
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
