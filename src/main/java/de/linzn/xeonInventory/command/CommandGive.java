package de.linzn.xeonInventory.command;

import de.linzn.xeonInventory.classes.CGive;
import de.linzn.xeonInventory.config.I18n;
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

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 */

public class CommandGive implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandGive() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("xeonInventory.team.give")) {
                if (args.length < 1) {
                    sender.sendMessage(I18n.translate("messages.giveError"));
                } else if (args.length >= 1) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        sender.sendMessage(I18n.translate("messages.notOnline"));
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
                                } catch (NumberFormatException ex) {
                                }
                            }
                        }

                        ItemStack stack = new ItemStack(material, amount, data);
                        new CGive(player, stack);

                    } else {
                        sender.sendMessage(I18n.translate("messages.noItem"));
                    }
                }
            } else {
                sender.sendMessage(I18n.translate("messages.noPermission"));
            }
        });
        return true;
    }

}
