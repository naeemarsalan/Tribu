package samp20.zombiesurvival.executors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.ZombieSurvival;

public class CmdZspawn implements CommandExecutor {
	private ZombieSurvival plugin;
	
	public CmdZspawn(ZombieSurvival instance) {
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
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("set")) {
				
				plugin.getLevel().addZombieSpawn(player.getLocation(), args[1]);
				player.sendMessage(ChatColor.GREEN + "Spawnpoint added");
				return true;
				
			}else if(args[0].equalsIgnoreCase("remove")) {
				
				plugin.getLevel().removeZombieSpawn(args[1]);
				player.sendMessage(ChatColor.GREEN + "Spawnpoint removed");
				return true;
				
			}else if(args[0].equalsIgnoreCase("jump")) {
				
				Location zspawn = plugin.getLevel().getZombieSpawn(args[1]);
				if(zspawn != null) {
					player.teleport(zspawn);
					player.sendMessage(ChatColor.GREEN + "Teleported to zombie spawn " +
						ChatColor.LIGHT_PURPLE + args[1]);
				}else{
					player.sendMessage(ChatColor.RED + "Invalid spawn name");
				}
				return true;
				
			}
		}else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
			
			plugin.getLevel().ListSpawns(player);
			return true;
		}
		
		return false;
	}

}
