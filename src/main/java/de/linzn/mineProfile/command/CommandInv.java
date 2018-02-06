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
import de.linzn.mineProfile.config.I18n;
import de.linzn.mineProfile.task.InventoryLoad;
import de.linzn.mineProfile.utils.HashDB;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandInv implements CommandExecutor {

    private ThreadPoolExecutor executorServiceCommands;

    public CommandInv() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (args.length == 0) {
                help(sender);
            } else {
                if (args[0].equalsIgnoreCase("load")) {
                    load(sender, args);
                } else if (args[0].equalsIgnoreCase("version")) {
                    version(sender, args);

                } else {
                    help(sender);
                }
            }
        });
        return true;
    }

    private void load(final CommandSender sender, final String[] args) {
        Player player = (Player) sender;
        if (sender.hasPermission("mineProfile.cmd.load")) {
            if (!HashDB.authLock.contains(player.getUniqueId())) {
                sender.sendMessage(I18n.translate("messages.alreadyLoaded"));
                return;

            }
            new InventoryLoad(player, true);

        } else {
            sender.sendMessage(I18n.translate("messages.noPermission"));
        }
        return;
    }

    private void version(final CommandSender sender, final String[] args) {
        sender.sendMessage(ChatColor.GREEN + "mineProfile version: " + ChatColor.LIGHT_PURPLE
                + MineProfilePlugin.inst().pdf.getVersion());
    }

    private void help(CommandSender sender) {
        if (sender.hasPermission("mineProfile.cmd.help")) {
            sender.sendMessage(I18n.translate("interfaceHelp1.title1"));
            sender.sendMessage(I18n.translate("interfaceHelp1.title2"));
        } else {
            sender.sendMessage(I18n.translate("messages.noPermission"));
        }
    }

}
