/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.task;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.config.CookieConfig;
import de.linzn.mineProfile.config.I18n;

public class SetupLanguageTask implements Runnable {

    private MineProfilePlugin plugin;
    private CookieConfig config;

    public SetupLanguageTask(MineProfilePlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getCookieConfig();
    }

    @Override
    public void run() {
        if (!config.loaded) {
            plugin.getServer().getScheduler().runTaskLater(plugin, this, 5L);
        } else {
            plugin.getServer().getScheduler().runTask(plugin, () -> new I18n(plugin, config.locale));
        }
    }

}
