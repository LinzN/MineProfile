/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */
package de.linzn.mineProfile.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtilsAPI {

    public static boolean isArtificiallyItem(ItemStack item) {
        if ((item != null) && (item.hasItemMeta())) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                for (String s : meta.getLore()) {
                    if (s.startsWith("Artificially")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack setArtificiallyItem(String player, ItemStack item) {
        if ((item != null) && (item.getType() != Material.AIR) && (item.getType() != Material.BOOK_AND_QUILL)
                && (!isArtificiallyItem(item))) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (meta.hasLore()) {
                lore = meta.getLore();
            }
            lore.add(0, "Artificially");
            lore.add(1, player);
            lore.add(2, new SimpleDateFormat("dd.MM.yyyy '-' HH:mm").format(new Date()));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

}
