package samp20.zombiesurvival.executors;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.Constants;
import samp20.zombiesurvival.ZombieSurvival;

public class CmdDspawn implements CommandExecutor {
	private ZombieSurvival plugin;

	public CmdDspawn(ZombieSurvival instance) {
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

				player.teleport(plugin.getLevel().getDeath());
				player.sendMessage(Constants.MessageTeleportedToDeathSpawn);
				return true;

			}
		} else {

			plugin.getLevel().setDeath(player.getLocation());
			player.sendMessage(Constants.MessageDeathSpawnSet);
			return true;

		}

		return false;
	}

}
