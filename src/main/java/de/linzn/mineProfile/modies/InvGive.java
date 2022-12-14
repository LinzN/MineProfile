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
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class InvGive {

    public InvGive(final Player player, final ItemStack value) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    player.getInventory().addItem(value);
                    player.sendMessage(LanguageDB.createItemstack.replace("%s1", value.getType().name()).replace("%s2", "" +
                            value.getData()).replace("%s3", "" + value.getAmount()));
                });
    }

}
