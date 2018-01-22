package de.linzn.mineProfile.task;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.core.CookieApi;
import de.linzn.mineProfile.database.SQLInject;
import org.bukkit.entity.Player;

public class InventorySave extends SQLInject {

    public InventorySave(Player player, boolean logout) {
        save(player, logout);
    }

    public void save(Player player, boolean logout) {
        if (MineProfilePlugin.inst().getCookieConfig().debug) {
            if (logout) {
                CookieApi.log("Save: " + player.getName());
            } else {
                CookieApi.log("Autosaving: " + player.getName());
            }
        }
        CookieApi.saveData(player, logout, true);
    }
}
