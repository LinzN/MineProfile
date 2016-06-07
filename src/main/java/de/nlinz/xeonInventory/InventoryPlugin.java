package de.nlinz.xeonInventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import de.nlinz.xeonInventory.command.CommandClear;
import de.nlinz.xeonInventory.command.CommandFly;
import de.nlinz.xeonInventory.command.CommandGameMode;
import de.nlinz.xeonInventory.command.CommandGive;
import de.nlinz.xeonInventory.command.CommandInv;
import de.nlinz.xeonInventory.command.CommandTime;
import de.nlinz.xeonInventory.command.CommandVanish;
import de.nlinz.xeonInventory.command.CommandWeather;
import de.nlinz.xeonInventory.config.CookieConfig;
import de.nlinz.xeonInventory.core.CookieApi;
import de.nlinz.xeonInventory.database.ConnectionManager;
import de.nlinz.xeonInventory.database.DatabaseSetup;
import de.nlinz.xeonInventory.listener.BukkitEvents;
import de.nlinz.xeonInventory.task.SetupLanguageTask;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class InventoryPlugin extends JavaPlugin {
    private static InventoryPlugin inst;
    public PluginDescriptionFile         pdf;
    private CookieConfig                 config;

    public static InventoryPlugin inst() {
        return inst;
    }

    public CookieConfig getCookieConfig() {
        return config;
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Saving all players...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!CookieApi.isPlayerHashLoadet(p.getUniqueId())) {
                CookieApi.removeHashLoginLock(p.getUniqueId());
            } else {
                CookieApi.saveData(p, true, false);
            }
        }
        ConnectionManager.DEFAULT.shutdown();
    }

    @Override
    public void onEnable() {
        PluginDescriptionFile version = this.getDescription();
        pdf = version;
        inst = this;
        this.saveDefaultConfig();
        config = new CookieConfig(this);
        if (DatabaseSetup.create()) {
            getServer().getScheduler().runTaskAsynchronously(this, new SetupLanguageTask(this));
            getCommand("inv").setExecutor(new CommandInv());
            getCommand("fly").setExecutor(new CommandFly());
            getCommand("gm").setExecutor(new CommandGameMode());
            getCommand("vanish").setExecutor(new CommandVanish());
            getCommand("give").setExecutor(new CommandGive());
            getCommand("time").setExecutor(new CommandTime());
            getCommand("weather").setExecutor(new CommandWeather());
            getCommand("clear").setExecutor(new CommandClear());
            getServer().getPluginManager().registerEvents(new BukkitEvents(), this);
        } else {
            getLogger().warning("Disable plugin...");
            setEnabled(false);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            CookieApi.onlogin(p);
        }

    }
}
