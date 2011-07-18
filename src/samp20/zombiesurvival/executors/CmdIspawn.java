package samp20.zombiesurvival.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.Constants;
import samp20.zombiesurvival.ZombieSurvival;

public class CmdIspawn implements CommandExecutor {
	private ZombieSurvival plugin;

	public CmdIspawn(ZombieSurvival instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!sender.isOp()) {
			return true;
		}

		if (!(sender instanceof Player)) {
			plugin.LogWarning(Constants.WarningThisCommandCannotBeUsedFromTheConsole);
			return true;
		}
		Player player = (Player) sender;

		// Make sure a level is loaded
		if (plugin.getLevel() == null) {
			player.sendMessage(Constants.MessageNoLevelLoaded);
			player.sendMessage(Constants.MessageNoLevelLoaded2);
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("jump")) {

				player.teleport(plugin.getLevel().getSpawn());
				player.sendMessage(Constants.MessageTeleportedToInitialSpawn);
				return true;

			}
		} else {

			plugin.getLevel().setSpawn(player.getLocation());
			player.sendMessage(Constants.MessageInitialSpawnSet);
			return true;

		}

		return false;
	}

}
