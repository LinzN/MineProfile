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

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.core.PlayerDataAPI;
import de.linzn.mineProfile.core.UtilsAPI;
import de.linzn.mineProfile.database.SQLInject;
import de.linzn.mineProfile.modies.VanishMode;
import de.linzn.mineProfile.utils.HashDB;
import org.bukkit.Bukkit;
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        event.getPlayer().setFallDistance(0L);
        if (!MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())){
            new VanishMode(event.getPlayer(), 1, false);
            PlayerDataAPI.loadProfile(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (!MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())){
            PlayerDataAPI.unloadProfile(event.getPlayer());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (HashDB.authLock.contains(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChange(InventoryInteractEvent event) {
        if (HashDB.authLock.contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractAt(PlayerInteractAtEntityEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractOther(PlayerInteractEntityEvent event) {
        if (HashDB.authLock.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAllDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (HashDB.authLock.contains(event.getEntity().getUniqueId())) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        if (MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getFrom().getName()) && !MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())){
            PlayerDataAPI.loadProfile(p);
        } else if (!MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getFrom().getName()) && MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            PlayerDataAPI.unloadProfile(p);
            Bukkit.getScheduler().runTaskLater(MineProfilePlugin.inst(), () -> p.getInventory().clear(), 20L);
        }

    }

    @EventHandler
    public void onCreativeClick(InventoryCreativeEvent event) {
        event.setCursor(UtilsAPI.setArtificiallyItem(event.getWhoClicked().getName(), event.getCursor()));
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
            if (UtilsAPI.isArtificiallyItem(item)) {
                UtilsAPI.setArtificiallyItem(event.getViewers().get(0).getName(), event.getInventory().getItem(0));
                break;
            }
        }
    }

}
