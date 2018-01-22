package de.linzn.mineProfile.classes;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.config.I18n;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CGive {

    public CGive(final Player player, final ItemStack value) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    player.getInventory().addItem(value);
                    player.sendMessage(I18n.translate("messages.createItemstack", value.getType().name(),
                            value.getData(), value.getAmount()));
                });
    }

}
