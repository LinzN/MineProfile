package de.linzn.mineProfile.classes;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.config.I18n;
import org.bukkit.entity.Player;

public class CClear {

    public CClear(final Player player) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    player.getInventory().clear();
                    player.sendMessage(I18n.translate("messages.preparePlayerData"));
                });
    }

}
