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
import org.bukkit.entity.Player;

public class CClear {

    public CClear(final Player player) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    player.getInventory().clear();
                    player.sendMessage(I18n.translate("messages.preparePlayerData"));
                });
    }

}
