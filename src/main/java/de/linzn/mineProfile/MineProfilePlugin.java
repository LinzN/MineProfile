/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile;

import de.linzn.mineProfile.command.*;
import de.linzn.mineProfile.config.CookieConfig;
import de.linzn.mineProfile.core.PlayerDataAPI;
import de.linzn.mineProfile.database.ConnectionManager;
import de.linzn.mineProfile.database.DatabaseSetup;
import de.linzn.mineProfile.listener.BukkitEvents;
import de.linzn.mineProfile.task.SetupLanguageTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MineProfilePlugin extends JavaPlugin {
    private static MineProfilePlugin inst;
    public PluginDescriptionFile pdf;
    private CookieConfig config;

    public static MineProfilePlugin inst() {
        return inst;
    }

    public CookieConfig getCookieConfig() {
        return config;
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Saving all players...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!this.getCookieConfig().disabledWorlds.contains(p.getWorld().getName())) {
                /*
                if (HashDB.functionState.contains(p.getUniqueId())) {
                    if (HashDB.authLock.contains(p.getUniqueId())) {
                        HashDB.authLock.remove(p.getUniqueId());
                    } else {
                        PlayerDataAPI.saveData(p, true, false);
                    }
                    HashDB.functionState.remove(p.getUniqueId());
                } */
                PlayerDataAPI.unloadProfile(p, false);
            }
        }
        ConnectionManager.DEFAULT.shutdown();
    }

    @Override
    public void onEnable() {
        pdf = this.getDescription();
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
            if (!this.getCookieConfig().disabledWorlds.contains(p.getWorld().getName())) {
                PlayerDataAPI.loadProfile(p);
            }
        }

    }
}
