package samp20.zombiesurvival.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import samp20.zombiesurvival.ZombieSurvival;

public class CmdZombieMode implements CommandExecutor {
	private ZombieSurvival plugin;
	
	public CmdZombieMode(ZombieSurvival instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.isOp()){
			return true;
		}
		
		if(args.length != 1) {
			return false;
		}
		
		//Make sure a level is loaded
		if(plugin.getLevel() == null) {
			sender.sendMessage("No level loaded, type '/level load' to load one,");
			sender.sendMessage("or '/level create' to create a new one,");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("enable")){
			plugin.startRunning();
			sender.sendMessage("Zombie Mode enabled!");
			return true;
		}else if(args[0].equalsIgnoreCase("disable")){
			plugin.stopRunning();
			sender.sendMessage("Zombie Mode disabled!");
			return true;
		}
		
		return false;
	}

}
