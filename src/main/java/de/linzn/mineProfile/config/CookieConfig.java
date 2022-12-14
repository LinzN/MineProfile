/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.config;

import de.linzn.mineProfile.MineProfilePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CookieConfig {

    public String locale = "deDE";
    public boolean debug = false;
    public boolean autosave = false;
    public int autosavetime = 60;
    public int cleandata = 60;
    public double version = 1.0;
    public boolean loaded = false;
    public List<String> disabledWorlds;
    public List<String> creativeWorlds;
    public List<String> disabledCommands;
    private FileConfiguration config;

    public CookieConfig(final MineProfilePlugin plugin) {
        plugin.saveDefaultConfig();

        config = plugin.getConfig();
        loadConfiguration();

        loaded = true;

        plugin.saveDefaultConfig();
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
        ArrayList<String> wList = new ArrayList<>();
        wList.add("testWorld");
        disabledWorlds = get("disabledWorlds", wList);

        ArrayList<String> creative = new ArrayList<>();
        creative.add("creativtest");
        creativeWorlds = get("creativeWorlds", creative);

        ArrayList<String> commands = new ArrayList<>();
        commands.add("mobarena");
        commands.add("ma");
        commands.add("minegames");
        commands.add("mg");
        commands.add("sg");
        disabledCommands = get("disabledCommands", commands);

    }
}
