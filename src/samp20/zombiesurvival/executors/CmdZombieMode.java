package samp20.zombiesurvival.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import samp20.zombiesurvival.Constants;
import samp20.zombiesurvival.ZombieSurvival;

public class CmdZombieMode implements CommandExecutor {
	private ZombieSurvival plugin;

	public CmdZombieMode(ZombieSurvival instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!sender.isOp()) {
			return true;
		}

		if (args.length == 0) {
			return false;
		}
		// Make sure a level is loaded
		if (plugin.getLevel() == null) {
			sender.sendMessage(Constants.MessageNoLevelLoaded);
			sender.sendMessage(Constants.MessageNoLevelLoaded2);
			return true;
		}
		if (args[0].equalsIgnoreCase("enable")) {
			plugin.startRunning();
			sender.sendMessage(Constants.MessageZombieModeEnabled);
			return true;
		} else // (args[0].equalsIgnoreCase("disable"))
		{
			plugin.stopRunning();
			sender.sendMessage(Constants.MessageZombieModeDisabled);
			return true;
		}

		//return false;
	}

}
