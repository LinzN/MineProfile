package de.nlinz.xeonInventory.command;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.nlinz.xeonInventory.classes.CFlyMode;
import de.nlinz.xeonInventory.config.I18n;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CommandFly implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandFly() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(new Runnable() {
            public void run() {
                if (sender.hasPermission("xeonInventory.team.fly")) {
                    if (args.length == 0) {
                        Player player = (Player) sender;

                        if (player.getAllowFlight()) {
                            new CFlyMode(player, 0, true);
                            return;
                        }
                        if (!player.getAllowFlight()) {
                            new CFlyMode(player, 1, true);
                            return;
                        }
                    } else if (args.length == 1) {
                        Player player = (Player) sender;
                        if (args[0].equalsIgnoreCase("on")) {
                            new CFlyMode(player, 1, true);
                        } else if (args[0].equalsIgnoreCase("off")) {
                            new CFlyMode(player, 0, true);
                        } else {
                            sender.sendMessage(I18n.translate("messages.flyError"));
                        }

                    } else if (args.length > 1) {
                        Player player = Bukkit.getPlayer(args[0]);
                        if (player == null) {
                            sender.sendMessage(I18n.translate("messages.notOnline"));
                            return;
                        }
                        if (args[1].equalsIgnoreCase("on")) {
                            new CFlyMode(player, 1, true);
                            sender.sendMessage("§aFlyMode von " + args[0] + " zu aktiv geändert!");
                        } else if (args[1].equalsIgnoreCase("off")) {
                            new CFlyMode(player, 0, true);
                            sender.sendMessage("§aFlyMode von " + args[0] + " zu inaktiv geändert!");
                        } else {
                            sender.sendMessage(I18n.translate("messages.flyError"));
                        }
                    }

                } else {
                    sender.sendMessage(I18n.translate("messages.noPermission"));
                }

            }
        });
        return true;
    }

}
