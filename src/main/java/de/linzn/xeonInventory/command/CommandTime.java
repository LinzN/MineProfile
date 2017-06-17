package de.linzn.xeonInventory.command;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.linzn.xeonInventory.classes.CTime;
import de.linzn.xeonInventory.config.I18n;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2015</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CommandTime implements CommandExecutor {

	public ThreadPoolExecutor executorServiceCommands;

	public CommandTime() {
		executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		executorServiceCommands.submit(new Runnable() {
			public void run() {
				if (sender.hasPermission("xeonInventory.team.time")) {
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
			}
		});
		return true;
	}

}
