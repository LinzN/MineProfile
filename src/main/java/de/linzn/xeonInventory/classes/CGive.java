package de.linzn.xeonInventory.classes;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CGive {

    public CGive(final Player player, final ItemStack value) {
        InventoryPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(InventoryPlugin.inst(),
                () -> {
                    player.getInventory().addItem(value);
                    player.sendMessage(I18n.translate("messages.createItemstack", value.getType().name(),
                            value.getData(), value.getAmount()));
                });
    }

}
