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
