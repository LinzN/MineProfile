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
import org.bukkit.inventory.ItemStack;


public class CGive {

    public CGive(final Player player, final ItemStack value) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    player.getInventory().addItem(value);
                    player.sendMessage(I18n.translate("messages.createItemstack", value.getType().name(),
                            value.getData(), value.getAmount()));
                });
    }

}
