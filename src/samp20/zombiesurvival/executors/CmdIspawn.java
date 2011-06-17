package samp20.zombiesurvival.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.ZombieSurvival;

public class CmdIspawn implements CommandExecutor {
	private ZombieSurvival plugin;
	
	public CmdIspawn(ZombieSurvival instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.isOp()){
			return true;
		}
		
		if(!(sender instanceof Player)) {
			plugin.LogWarning("This command cannot be used from the console");
			return true;
		}
		Player player = (Player) sender;

		//Make sure a level is loaded
		if(plugin.getLevel() == null) {
			player.sendMessage("No level loaded, type '/level load' to load one,");
			player.sendMessage("or '/level create' to create a new one,");
			return true;
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("jump")) {
				
				player.teleport(plugin.getLevel().getSpawn());
				player.sendMessage(ChatColor.GREEN + "Teleported to initial spawn");
				return true;
				
			}
		}else{
			
			plugin.getLevel().setSpawn(player.getLocation());
			player.sendMessage("Initial spawn set.");
			return true;
			
		}
		
		return false;
	}

}
