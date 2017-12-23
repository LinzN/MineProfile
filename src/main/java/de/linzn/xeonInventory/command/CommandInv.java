package de.linzn.xeonInventory.command;

import de.linzn.xeonInventory.InventoryPlugin;
import de.linzn.xeonInventory.config.I18n;
import de.linzn.xeonInventory.core.CookieApi;
import de.linzn.xeonInventory.task.InventoryLoad;
import org.bukkit.ChatColor;
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

public class CommandInv implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandInv() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (args.length == 0) {
                help(sender);
            } else if (args.length > 0) {
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

    public void load(final CommandSender sender, final String[] args) {
        Player player = (Player) sender;
        if (sender.hasPermission("xeonInventory.cmd.load")) {
            if (CookieApi.isPlayerHashLoaded(player.getUniqueId())) {
                sender.sendMessage(I18n.translate("messages.alreadyLoaded"));
                return;

            }
            new InventoryLoad(player, true);

        } else {
            sender.sendMessage(I18n.translate("messages.noPermission"));
        }
        return;
    }

    public void version(final CommandSender sender, final String[] args) {
        sender.sendMessage(ChatColor.GREEN + "xeonInventory version: " + ChatColor.LIGHT_PURPLE
                + InventoryPlugin.inst().pdf.getVersion());
        return;
    }

    private void help(CommandSender sender) {
        if (sender.hasPermission("xeonInventory.cmd.help")) {
            sender.sendMessage(I18n.translate("interfaceHelp1.title1"));
            sender.sendMessage(I18n.translate("interfaceHelp1.title2"));
        } else {
            sender.sendMessage(I18n.translate("messages.noPermission"));
        }
    }

}
