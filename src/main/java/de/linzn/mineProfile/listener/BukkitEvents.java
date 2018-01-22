/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.listener;

import de.linzn.mineProfile.core.CookieApi;
import de.linzn.mineProfile.database.SQLInject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class BukkitEvents extends SQLInject implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        CookieApi.onlogin(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        CookieApi.onLeave(event.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getDamager().getUniqueId())
                || CookieApi.isHashGodlock(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChange(InventoryInteractEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())
                || CookieApi.isHashGodlock(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractAt(PlayerInteractAtEntityEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())
                || CookieApi.isHashGodlock(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractOther(PlayerInteractEntityEvent event) {
        if (!CookieApi.isPlayerHashLoaded(event.getPlayer().getUniqueId())
                || CookieApi.isHashGodlock(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAllDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!CookieApi.isPlayerHashLoaded(event.getEntity().getUniqueId())
                    || CookieApi.isHashGodlock(event.getEntity().getUniqueId())) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        CookieApi.setHashGodlock(event.getPlayer().getUniqueId());
        CookieApi.startUnlockGod(p, p.getWorld());

    }

    @EventHandler
    public void onCreativeClick(InventoryCreativeEvent event) {

        event.setCursor(CookieApi.setArtificiallyItem(event.getWhoClicked().getName(), event.getCursor()));
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if ((event.getViewers().isEmpty())) {
            return;
        }
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = event.getInventory().getMatrix()).length;
        for (int i = 0; i < j; i++) {
            ItemStack item = arrayOfItemStack[i];
            if (CookieApi.isArtificiallyItem(item)) {

                CookieApi.setArtificiallyItem(event.getViewers().get(0).getName(), event.getInventory().getItem(0));
                break;
            }
        }
    }

}
