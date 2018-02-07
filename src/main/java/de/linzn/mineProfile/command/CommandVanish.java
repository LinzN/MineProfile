/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.command;

import de.linzn.mineProfile.MineProfilePlugin;
import de.linzn.mineProfile.modies.VanishMode;
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandVanish implements CommandExecutor {

    private ThreadPoolExecutor executorServiceCommands;

    public CommandVanish() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("mineProfile.team.vanish")) {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    if (VanishMode.isInVanishMode(player)) {
                        Bukkit.getScheduler().runTask(MineProfilePlugin.inst(), () -> new VanishMode(player, 0, true));

                    } else {
                        Bukkit.getScheduler().runTask(MineProfilePlugin.inst(), () -> new VanishMode(player, 1, true));
                    }
                } else {
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("on")) {
                        Bukkit.getScheduler().runTask(MineProfilePlugin.inst(), () -> new VanishMode(player, 1, true));
                    } else if (args[0].equalsIgnoreCase("off")) {
                        Bukkit.getScheduler().runTask(MineProfilePlugin.inst(), () -> new VanishMode(player, 0, true));
                    } else {
                        sender.sendMessage(LanguageDB.vanishError);
                    }

                }
            } else {
                sender.sendMessage(LanguageDB.noPermission);
            }
        });
        return true;
    }

}
