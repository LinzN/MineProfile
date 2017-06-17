package de.linzn.xeonInventory.command;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.linzn.xeonInventory.classes.CClear;
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

public class CommandClear implements CommandExecutor {

	public ThreadPoolExecutor executorServiceCommands;

	public CommandClear() {
		executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		executorServiceCommands.submit(new Runnable() {
			public void run() {
				if (sender.hasPermission("xeonInventory.team.clear")) {
					if (args.length == 0) {
						Player player = (Player) sender;
						new CClear(player);
					} else if (args.length >= 1) {
						Player player = Bukkit.getPlayer(args[0]);
						if (player == null) {
							sender.sendMessage(I18n.translate("messages.notOnline"));
							return;
						}
						new CClear(player);
					}
				} else {
					sender.sendMessage(I18n.translate("messages.noPermission"));
				}
			}
		});
		return true;
	}

}
