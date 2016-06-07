package de.nlinz.xeonInventory.task;

import org.bukkit.entity.Player;

import de.nlinz.xeonInventory.InventoryPlugin;
import de.nlinz.xeonInventory.core.CookieApi;
import de.nlinz.xeonInventory.database.SQLInject;

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
