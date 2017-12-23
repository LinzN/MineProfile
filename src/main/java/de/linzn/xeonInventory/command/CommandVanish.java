package de.linzn.xeonInventory.command;

import de.linzn.xeonInventory.classes.CVanishMode;
import de.linzn.xeonInventory.config.I18n;
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

public class CommandVanish implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandVanish() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("xeonInventory.team.vanish")) {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    if (CVanishMode.isInVanishMode(player)) {
                        new CVanishMode(player, 0, true);

                    } else {
                        new CVanishMode(player, 1, true);
                    }
                } else if (args.length > 0) {
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("on")) {
                        new CVanishMode(player, 1, true);
                    } else if (args[0].equalsIgnoreCase("off")) {
                        new CVanishMode(player, 0, true);
                    } else {
                        sender.sendMessage(I18n.translate("messages.vanishError"));
                    }

                }
            } else {
                sender.sendMessage(I18n.translate("messages.noPermission"));
            }
        });
        return true;
    }

}
