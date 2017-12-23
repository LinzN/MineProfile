package de.linzn.xeonInventory.task;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.core.CookieApi;
import de.linzn.xeonInventory.database.SQLInject;
import org.bukkit.entity.Player;

public class InventorySave extends SQLInject {

    public InventorySave(Player player, boolean logout) {
        save(player, logout);
    }

    public void save(Player player, boolean logout) {
        if (InventoryPlugin.inst().getCookieConfig().debug) {
            if (logout) {
                CookieApi.log("Save: " + player.getName());
            } else {
                CookieApi.log("Autosaving: " + player.getName());
            }
        }
        CookieApi.saveData(player, logout, true);
    }
}
