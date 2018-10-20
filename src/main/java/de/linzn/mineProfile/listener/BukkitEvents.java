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
import de.linzn.mineProfile.database.ProfileQuery;
import de.linzn.mineProfile.modies.InvGamemode;
import de.linzn.mineProfile.modies.VanishMode;
import de.linzn.mineProfile.utils.HashDB;
import de.linzn.mineProfile.utils.LanguageDB;
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

public class BukkitEvents extends ProfileQuery implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setFallDistance(0L);
        if (MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* If the world is mark as disabled */
            Bukkit.getScheduler().runTaskLater(MineProfilePlugin.inst(), () -> player.getInventory().clear(), 10L);
        } else if (MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* If the world is mark as a creativ world */
            Bukkit.getScheduler().runTaskLater(MineProfilePlugin.inst(), () -> player.getInventory().clear(), 10L);
            new InvGamemode(event.getPlayer(), 1, false);
        } else {
            /* Default world load */
            new VanishMode(event.getPlayer(), 1, false);
            PlayerDataAPI.loadProfile(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* If the world is mark as disabled */
            MineProfilePlugin.inst().getLogger().info("No save on disabled world!");
        } else if (MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* If the world is mark as a creativ world */
            MineProfilePlugin.inst().getLogger().info("No save on creative world!");
        } else {
            /* Default world save */
            PlayerDataAPI.unloadProfile(event.getPlayer(), true);
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void ChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        if (MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getFrom().getName()) && !MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* Default world load after disabled world */
            PlayerDataAPI.loadProfile(p);
        } else if (MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(event.getFrom().getName()) && !MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* Default world load after creativ world  */
            PlayerDataAPI.loadProfile(p);
        } else if (!MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getFrom().getName()) && MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* Save data before enter disabled world */
            PlayerDataAPI.unloadProfile(p, true);
            Bukkit.getScheduler().runTaskLater(MineProfilePlugin.inst(), () -> p.getInventory().clear(), 20L);
        } else if (!MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(event.getFrom().getName()) && MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(event.getPlayer().getWorld().getName())) {
            /* Save data before enter creativ world */
            PlayerDataAPI.unloadProfile(p, true);
            Bukkit.getScheduler().runTaskLater(MineProfilePlugin.inst(), () -> {
                p.getInventory().clear();
                new InvGamemode(event.getPlayer(), 1, false);
            }, 10L);

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
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0].replace("/", "");
        Player player = event.getPlayer();
        if (MineProfilePlugin.inst().getCookieConfig().disabledWorlds.contains(player.getWorld().getName()) || MineProfilePlugin.inst().getCookieConfig().creativeWorlds.contains(player.getWorld().getName())){
            if (MineProfilePlugin.inst().getCookieConfig().disabledCommands.contains(command)){
                event.setCancelled(true);
                player.sendMessage(LanguageDB.warningCommandDisabled);
            }
        }

    }


    }
