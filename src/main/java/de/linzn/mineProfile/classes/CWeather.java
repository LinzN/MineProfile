package de.linzn.mineProfile.classes;

import de.linzn.mineProfile.MineProfilePlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CWeather {

    public CWeather(final Player player, final String weatherName) {

        MineProfilePlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(MineProfilePlugin.inst(),
                () -> {
                    final World world = player.getWorld();
                    switch (weatherName.toUpperCase()) {
                        case "SUN":
                            world.setThundering(false);
                            world.setStorm(false);
                            break;
                        case "RAIN":
                            world.setStorm(true);
                            break;
                        case "THUNDER":
                            world.setThundering(true);
                            break;
                        default:
                            world.setThundering(false);
                            world.setStorm(false);
                            break;
                    }
                });
    }

}
