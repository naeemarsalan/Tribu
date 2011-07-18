package samp20.zombiesurvival.executors;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.Constants;
import samp20.zombiesurvival.ZombieSurvival;

public class CmdLevel implements CommandExecutor {
	private ZombieSurvival plugin;

	public CmdLevel(ZombieSurvival instance) {
		plugin = instance;
	}

	// description: level creation/switching commands
	// usage: /level (( new | load | delete ) <name> ) | save | list

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!sender.isOp()) {
			return true;
		}
		if (args.length > 0) {
			//args[0] = args[0].trim().toLowerCase();
			
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("new")) {

					if (!(sender instanceof Player)) {
						plugin.LogWarning(Constants.WarningThisCommandCannotBeUsedFromTheConsole);
						return true;
					}
					Player player = (Player) sender;

					if (!plugin.getLevelLoader().saveLevel(plugin.getLevel())) {
						player.sendMessage(Constants.MessageUnableToSaveCurrentLevel);
						return true;
					}

					plugin.setLevel(plugin.getLevelLoader().newLevel(args[1],
							player.getLocation()));
					player.sendMessage(String.format(
							Constants.MessageLevelCreated, args[1]));

					return true;

				} else if (args[0].equalsIgnoreCase("load")) {

					Player p = (sender instanceof Player) ? (Player) sender
							: null;

					plugin.getLevelSelector().ChangeLevel(args[1], p);
					return true;

				} else if (args[0].equalsIgnoreCase("delete") ) {

					if (!plugin.getLevelLoader().deleteLevel(args[1])) {
						if (sender instanceof Player) {
							sender.sendMessage(Constants.MessageUnableToDeleteLevel);
						} else {
							sender.sendMessage(ChatColor
									.stripColor(Constants.MessageUnableToDeleteLevel));
						}
					} else {
						if (sender instanceof Player) {
							sender.sendMessage(Constants.MessageLevelDeleted);
						} else {
							sender.sendMessage(ChatColor
									.stripColor(Constants.MessageLevelDeleted));
						}
					}
					return true;

				}
			} else {
				if (args[0].equalsIgnoreCase("save")) {

					if (!plugin.getLevelLoader().saveLevel(plugin.getLevel())) {
						if (sender instanceof Player) {
							sender.sendMessage(Constants.MessageUnableToSaveCurrentLevel);
						} else {
							sender.sendMessage(ChatColor
									.stripColor(Constants.MessageUnableToSaveCurrentLevel));
						}
					} else {
						if (sender instanceof Player) {
							sender.sendMessage(Constants.MessageLevelSaveSuccessful);
						} else {
							sender.sendMessage(ChatColor
									.stripColor(Constants.MessageLevelSaveSuccessful));
						}
					}
					return true;
				} else if (args[0].equalsIgnoreCase("list")) {
					Set<String> levels = plugin.getLevelLoader().getLevelList();
					String msg = "";
					String separator = "";
					for (String level : levels) {
						msg += separator + level;
						separator = ", ";
					}
					if (sender instanceof Player) {
						sender.sendMessage(String.format(
								Constants.MessageLevels, msg));
					} else {
						sender.sendMessage(String.format(
								ChatColor.stripColor(Constants.MessageLevels),
								msg));
					}
					return true;
				}
			}
		}
		return false;
	}

}
