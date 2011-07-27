package graindcafe.tribu.executors;

import graindcafe.tribu.Constants;
import graindcafe.tribu.Tribu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CmdIspawn implements CommandExecutor {
	private Tribu plugin;

	public CmdIspawn(Tribu instance) {
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

				player.teleport(plugin.getLevel().getInitialSpawn());
				player.sendMessage(Constants.MessageTeleportedToInitialSpawn);
				return true;

			}
		} else {

			plugin.getLevel().setInitialSpawn(player.getLocation());
			player.sendMessage(Constants.MessageInitialSpawnSet);
			return true;

		}

		return false;
	}

}
