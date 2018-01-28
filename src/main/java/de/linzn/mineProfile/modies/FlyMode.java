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
import de.linzn.mineProfile.config.I18n;
import org.bukkit.entity.Player;

public class FlyMode {

    public FlyMode(final Player player, final int mode, final boolean showInfo) {
        switch (MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    if (mode == 0) {
                        player.setAllowFlight(false);
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeFlymode", "DEAKTIVIERT"));
                        return;
                    }
                    if (mode == 1) {
                        player.setAllowFlight(true);
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeFlymode", "AKTIVIERT"));
                        return;
                    }
                })) {
        }
    }

    public static int getFlyMode(Player player) {
        int mode;
        if (player.getAllowFlight()) {
            mode = 1;
        } else {
            mode = 0;
        }
        return mode;

    }

    public static boolean isInFlyMode(Player player) {
        return player.getAllowFlight();

    }

}
