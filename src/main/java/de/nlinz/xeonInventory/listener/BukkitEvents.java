package de.nlinz.xeonInventory.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.nlinz.xeonInventory.core.CookieApi;
import de.nlinz.xeonInventory.database.SQLInject;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

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
        if (!CookieApi.isPlayerHashLoadet(event.getDamager().getUniqueId()) || CookieApi.isHashGodlock(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChange(InventoryInteractEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId()) || CookieApi.isHashGodlock(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractAt(PlayerInteractAtEntityEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId()) || CookieApi.isHashGodlock(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractOther(PlayerInteractEntityEvent event) {
        if (!CookieApi.isPlayerHashLoadet(event.getPlayer().getUniqueId()) || CookieApi.isHashGodlock(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAllDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!CookieApi.isPlayerHashLoadet(event.getEntity().getUniqueId()) || CookieApi.isHashGodlock(event.getEntity().getUniqueId())) {
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

}
