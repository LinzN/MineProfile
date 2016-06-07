package de.nlinz.xeonInventory.task;

import de.nlinz.xeonInventory.InventoryPlugin;
import de.nlinz.xeonInventory.config.CookieConfig;
import de.nlinz.xeonInventory.config.I18n;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupLanguageTask implements Runnable {

    private InventoryPlugin plugin;
    private CookieConfig          config;

    public SetupLanguageTask(InventoryPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getCookieConfig();
    }

    @Override
    public void run() {
        if (!config.loaded) {
            plugin.getServer().getScheduler().runTaskLater(plugin, this, 5L);
        } else {
            plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    new I18n(plugin, config.locale);
                }
            });
        }
    }

}
