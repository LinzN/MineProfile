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

import de.linzn.mineProfile.modies.InvGamemode;
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandGameMode implements CommandExecutor {

    private ThreadPoolExecutor executorServiceCommands;

    public CommandGameMode() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (args.length == 0) {
                sender.sendMessage(LanguageDB.gamemodeError);
            } else {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    if (sender.hasPermission("mineProfile.team.gamemode")) {
                        if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                            new InvGamemode(player, 0, true);
                        } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                            new InvGamemode(player, 1, true);
                        } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                            new InvGamemode(player, 2, true);
                        } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                            new InvGamemode(player, 3, true);
                        } else {
                            sender.sendMessage(LanguageDB.noGamemode);
                        }

                    } else {
                        sender.sendMessage(LanguageDB.noPermission);
                    }
                } else {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        sender.sendMessage(LanguageDB.notOnline);
                        return;
                    }
                    if (sender.hasPermission("mineProfile.team.gamemodeOther")) {
                        if (args[1].equalsIgnoreCase("0") || args[1].equalsIgnoreCase("survival")) {
                            new InvGamemode(player, 0, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Survival geändert!");
                        } else if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("creative")) {
                            new InvGamemode(player, 1, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Creative geändert!");
                        } else if (args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("adventure")) {
                            new InvGamemode(player, 2, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Adventure geändert!");
                        } else if (args[1].equalsIgnoreCase("3") || args[1].equalsIgnoreCase("spectator")) {
                            new InvGamemode(player, 3, true);
                            sender.sendMessage("§aGameMode von " + args[0] + " zu Spectator geändert!");
                        } else {
                            sender.sendMessage(LanguageDB.noGamemode);
                        }

                    } else {
                        sender.sendMessage(LanguageDB.noPermission);
                    }
                }
            }
        });
        return true;
    }

}
