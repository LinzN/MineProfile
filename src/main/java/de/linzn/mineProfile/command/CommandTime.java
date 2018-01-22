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

import de.linzn.mineProfile.classes.CTime;
import de.linzn.mineProfile.config.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandTime implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandTime() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("mineProfile.team.time")) {
                if (args.length == 0) {
                    sender.sendMessage(I18n.translate("messages.timeError"));
                    sender.sendMessage(I18n.translate("messages.timeAvailable"));
                } else if (args.length >= 1) {
                    Player player = (Player) sender;
                    new CTime(player, args[0]);
                    sender.sendMessage(I18n.translate("messages.changeTime", args[0]));
                }
            } else {
                sender.sendMessage(I18n.translate("messages.noPermission"));
            }
        });
        return true;
    }

}
