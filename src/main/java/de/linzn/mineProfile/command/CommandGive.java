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

import de.linzn.mineProfile.modies.InvGive;
import de.linzn.mineProfile.utils.LanguageDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandGive implements CommandExecutor {

    private ThreadPoolExecutor executorServiceCommands;

    public CommandGive() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("mineProfile.team.give")) {
                if (args.length < 1) {
                    sender.sendMessage(LanguageDB.giveError);
                } else {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        sender.sendMessage(LanguageDB.notOnline);
                        return;
                    }
                    Material material = Material.matchMaterial(args[1]);

                    if (material == null) {
                        material = Bukkit.getUnsafe().getMaterialFromInternalName(args[1]);
                    }

                    if (material != null) {
                        int amount = 1;
                        short data = 0;

                        if (args.length >= 3) {
                            amount = Integer.parseInt(args[2]);

                            if (args.length >= 4) {
                                try {
                                    data = Short.parseShort(args[3]);
                                } catch (NumberFormatException ignored) {
                                }
                            }
                        }

                        ItemStack stack = new ItemStack(material, amount, data);
                        new InvGive(player, stack);

                    } else {
                        sender.sendMessage(LanguageDB.noItem);
                    }
                }
            } else {
                sender.sendMessage(LanguageDB.noPermission);
            }
        });
        return true;
    }

}
