package de.linzn.mineProfile.classes;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.config.I18n;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CGameMode {

    public CGameMode(final Player player, final int mode, final boolean showInfo) {
        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    if (mode == 0) {
                        player.setGameMode(GameMode.SURVIVAL);
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeGamemode", "SURVIVAL"));
                    } else if (mode == 1) {
                        player.setGameMode(GameMode.CREATIVE);
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeGamemode", "CREATIVE"));
                    } else if (mode == 2) {
                        player.setGameMode(GameMode.ADVENTURE);
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeGamemode", "ADVENTURE"));
                    } else if (mode == 3) {
                        player.setGameMode(GameMode.SPECTATOR);
                        if (showInfo)
                            player.sendMessage(I18n.translate("messages.changeGamemode", "SPECTATOR"));
                    }
                });
    }

    public static int getGameMode(Player player) {
        int mode;
        switch (player.getGameMode()) {
            case SURVIVAL:
                mode = 0;
                break;
            case CREATIVE:
                mode = 1;
                break;
            case ADVENTURE:
                mode = 2;
                break;
            case SPECTATOR:
                mode = 3;
                break;
            default:
                mode = 0;
                break;

        }
        return mode;

    }


}
