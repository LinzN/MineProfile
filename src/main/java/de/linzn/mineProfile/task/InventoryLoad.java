package de.linzn.mineProfile.task;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.classes.CFlyMode;
import de.linzn.mineProfile.classes.CGameMode;
import de.linzn.mineProfile.classes.CVanishMode;
import de.linzn.mineProfile.config.I18n;
import de.linzn.mineProfile.core.CookieApi;
import de.linzn.mineProfile.database.SQLInject;
import org.bukkit.entity.Player;

public class InventoryLoad extends SQLInject {

    public InventoryLoad(Player player, boolean isCommand) {
        load(player, isCommand);
    }

    public void load(final Player player, final boolean isCommand) {
        MineProfilePlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineProfilePlugin.inst(), () -> {
            int loopNumber = 0;
            boolean loaded = false;

            if (!SQLInject.hasInventory(player.getUniqueId())) {
                CookieApi.log("Create: " + player.getName());
                SQLInject.createInventory(player.getUniqueId());
                CookieApi.removeHashLoginLock(player.getUniqueId());
                CookieApi.sendInfo(player, "§aDein Spielerprofil wurde geladen.");
                return;
            }
            CookieApi.log("Load: " + player.getName());
            //CookieApi.preparePlayerData(player);

            while (loopNumber <= 10 && !loaded) {
                try {
                    Thread.sleep(loopNumber * 150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (CookieApi.debug()) {
                    CookieApi.log("Load loop " + loopNumber + " for: " + player.getName());
                }

                if (!SQLInject.isInventoryLocked(player.getUniqueId())) {
                    CookieApi.loadData(player);

                }

                if (CookieApi.isPlayerHashLoaded(player.getUniqueId())) {

                    loaded = true;
                    CookieApi.sendInfo(player, "§aDein Spielerprofil wurde geladen.");

                }
                loopNumber++;

            }
            if (isCommand) {
                CookieApi.loadData(player);
                CookieApi.sendInfo(player, "§aDein Spielerprofil wurde geladen.");

            }

            if (!CookieApi.isPlayerHashLoaded(player.getUniqueId())) {
                if (CookieApi.debug()) {
                    CookieApi.errorlog("Failed to load data for: " + player.getName());
                }
                CookieApi.sendInfo(player, "§4Ladefehler bei deinem Profil. Versuche mit /inv load");
            }
        });
    }

}
