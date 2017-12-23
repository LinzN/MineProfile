package de.linzn.xeonInventory.command;

import de.linzn.xeonInventory.classes.CWeather;
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

public class CommandWeather implements CommandExecutor {

    public ThreadPoolExecutor executorServiceCommands;

    public CommandWeather() {
        executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        executorServiceCommands.submit(() -> {
            if (sender.hasPermission("xeonInventory.team.weather")) {
                if (args.length == 0) {
                    sender.sendMessage(I18n.translate("messages.weatherError"));
                    sender.sendMessage(I18n.translate("messages.weatherAvailable"));
                } else if (args.length >= 1) {
                    Player player = (Player) sender;
                    new CWeather(player, args[0]);
                    sender.sendMessage(I18n.translate("messages.changeWeather", args[0]));
                }
            } else {
                sender.sendMessage(I18n.translate("messages.noPermission"));
            }
        });
        return true;
    }

}
