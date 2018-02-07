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

import de.linzn.mineProfile.modies.ChangeWeather;
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandWeather implements CommandExecutor {

    private ThreadPoolExecutor executorServiceCommands;

    public CommandWeather() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("mineProfile.team.weather")) {
                if (args.length == 0) {
                    sender.sendMessage(LanguageDB.weatherError);
                    sender.sendMessage(LanguageDB.weatherAvailable);
                } else {
                    Player player = (Player) sender;
                    new ChangeWeather(player, args[0]);
                    sender.sendMessage(LanguageDB.changeWeather.replace("%s", args[0]));
                }
            } else {
                sender.sendMessage(LanguageDB.noPermission);
            }
        });
        return true;
    }

}
