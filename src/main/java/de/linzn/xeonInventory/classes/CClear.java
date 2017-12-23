package de.linzn.xeonInventory.classes;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;
import org.bukkit.entity.Player;

public class CClear {

    public CClear(final Player player) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
                () -> {
                    player.getInventory().clear();
                    player.sendMessage(I18n.translate("messages.preparePlayerData"));
                });
    }

}
