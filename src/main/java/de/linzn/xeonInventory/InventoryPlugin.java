package de.linzn.xeonInventory;

import de.linzn.xeonInventory.command.*;
import de.linzn.xeonInventory.config.CookieConfig;
import de.linzn.xeonInventory.core.CookieApi;
import de.linzn.xeonInventory.database.ConnectionManager;
import de.linzn.xeonInventory.database.DatabaseSetup;
import de.linzn.xeonInventory.listener.BukkitEvents;
import de.linzn.xeonInventory.task.SetupLanguageTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryPlugin extends JavaPlugin {
    private static InventoryPlugin inst;
    public PluginDescriptionFile pdf;
    private CookieConfig config;

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
            if (!CookieApi.isPlayerHashLoaded(p.getUniqueId())) {
                CookieApi.removeHashLoginLock(p.getUniqueId());
            } else {
                CookieApi.saveData(p, true, false);
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
            new Metrics(this);
        } else {
            getLogger().warning("Disable plugin...");
            setEnabled(false);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            CookieApi.onlogin(p);
        }

    }
}
