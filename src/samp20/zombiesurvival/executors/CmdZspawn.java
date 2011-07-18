package samp20.zombiesurvival.executors;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.Constants;
import samp20.zombiesurvival.ZombieSurvival;

public class CmdZspawn implements CommandExecutor {
	private ZombieSurvival plugin;

	public CmdZspawn(ZombieSurvival instance) {
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
			sender.sendMessage(Constants.MessageNoLevelLoaded);
			sender.sendMessage(Constants.MessageNoLevelLoaded2);
			return true;
		}
		
		if (args.length == 2) {
			//args[0]=args[0].toLowerCase();
			if (args[0].equalsIgnoreCase("set") ) {

				plugin.getLevel().addZombieSpawn(player.getLocation(), args[1]);
				player.sendMessage(Constants.MessageSpawnpointAdded);
				return true;

			} else if (args[0].equalsIgnoreCase("remove")) {

				plugin.getLevel().removeZombieSpawn(args[1]);
				player.sendMessage(Constants.MessageSpawnpointRemoved);
				return true;

			} else if (args[0].equalsIgnoreCase("jump")) {

				Location zspawn = plugin.getLevel().getZombieSpawn(args[1]);
				if (zspawn != null) {
					player.teleport(zspawn);
					player.sendMessage(String.format(Constants.MessageTeleportedToZombieSpawn,args[1]));
				} else {
					player.sendMessage(Constants.MessageInvalidSpawnName);
				}
				return true;

			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {

			plugin.getLevel().ListSpawns(player);
			return true;
		}

		return false;
	}

}
