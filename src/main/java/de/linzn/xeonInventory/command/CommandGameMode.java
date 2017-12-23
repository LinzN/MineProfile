package de.linzn.xeonInventory.command;

import de.linzn.xeonInventory.classes.CGameMode;
import de.linzn.xeonInventory.config.I18n;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 */

public class CommandGameMode implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandGameMode() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (args.length == 0) {
                sender.sendMessage(I18n.translate("messages.gamemodeError"));
            } else if (args.length > 0) {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    if (sender.hasPermission("xeonInventory.team.gamemode")) {
                        if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                            new CGameMode(player, 0, true);
                        } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                            new CGameMode(player, 1, true);
                        } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                            new CGameMode(player, 2, true);
                        } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                            new CGameMode(player, 3, true);
                        } else {
                            sender.sendMessage(I18n.translate("messages.noGamemode"));
                        }

                    } else {
                        sender.sendMessage(I18n.translate("messages.noPermission"));
                    }
                } else if (args.length > 1) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        sender.sendMessage(I18n.translate("messages.notOnline"));
                        return;
                    }
                    if (sender.hasPermission("xeonInventory.team.gamemodeOther")) {
                        if (args[1].equalsIgnoreCase("0") || args[1].equalsIgnoreCase("survival")) {
                            new CGameMode(player, 0, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Survival geändert!");
                        } else if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("creative")) {
                            new CGameMode(player, 1, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Creative geändert!");
                        } else if (args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("adventure")) {
                            new CGameMode(player, 2, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Adventure geändert!");
                        } else if (args[1].equalsIgnoreCase("3") || args[1].equalsIgnoreCase("spectator")) {
                            new CGameMode(player, 3, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Spectator geändert!");
                        } else {
                            sender.sendMessage(I18n.translate("messages.noGamemode"));
                        }

                    } else {
                        sender.sendMessage(I18n.translate("messages.noPermission"));
                    }
                }
            }
        });
        return true;
    }

}
