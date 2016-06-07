package de.nlinz.xeonInventory.config;

import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import de.nlinz.xeonInventory.InventoryPlugin;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CookieConfig {

    private FileConfiguration config;

    public String             locale       = "deDE";
    public boolean            debug        = false;
    public boolean            autosave     = false;
    public int                autosavetime = 60;
    public int                cleandata    = 60;
    public double             version      = 1.0;
    public boolean            loaded       = false;

    public CookieConfig(final InventoryPlugin plugin) {

        plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
            public void run() {
                plugin.saveDefaultConfig();

                config = plugin.getConfig();
                loadConfiguration();

                loaded = true;

                plugin.saveDefaultConfig();
            }
        });
    }

    private boolean existsPath(final String path) {
        return existsPath(path, "");
    }

    private boolean existsPath(final String pPath, final String path) {
        String pathPrefix = "";
        if (!path.isEmpty())
            pathPrefix = path + ".";
        if (config.isConfigurationSection(path)) {
            final Set<String> key = config.getConfigurationSection(path).getKeys(false);
            if (key.size() > 0) {
                final String[] paths = key.toArray(new String[0]);
                for (final String thePath : paths) {
                    if (existsPath(pPath, pathPrefix + thePath)) {
                        return true;
                    }
                }
            }
        } else {
            return pPath.equalsIgnoreCase(path);
        }
        return false;
    }

    private <T> T get(String path, T def) {
        try {
            if (!existsPath(path)) {
                config.set(path, def);
            }

            @SuppressWarnings("unchecked")
            T flag = (T) config.get(path);
            return flag;
        } catch (Exception e) {
            return def;
        }
    }

    private void loadConfiguration() {
        locale = get("Plugin.locale", "deDE");
        debug = get("Plugin.debug", false);
        autosave = get("Plugin.autosave", false);
        autosavetime = get("Plugin.autosavetime", 60);
        cleandata = get("Plugin.cleandata", 90);
        version = get("Config.version", 1.0);

    }
}
