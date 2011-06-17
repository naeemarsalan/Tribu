package samp20.zombiesurvival.executors;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.ZombieSurvival;

public class CmdLevel implements CommandExecutor {
	private ZombieSurvival plugin;
	
	public CmdLevel(ZombieSurvival instance) {
		plugin = instance;
	}
	
    //description: level creation/switching commands
    //usage: /level (( new | load | delete ) <name> ) | save | list
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.isOp()){
			return true;
		}
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("new")) {
				
				if(!(sender instanceof Player)) {
					plugin.LogWarning("This command cannot be used from the console");
					return true;
				}
				Player player = (Player) sender;
				
				if(!plugin.getLevelLoader().saveLevel(plugin.getLevel())){
					player.sendMessage(ChatColor.RED + "Unable to save current level");
					return true;
				}
				
				plugin.setLevel(plugin.getLevelLoader().newLevel(args[1], player.getLocation()));
				player.sendMessage(ChatColor.GREEN + "Level " + ChatColor.LIGHT_PURPLE +
						args[1] + ChatColor.GREEN + " created");
				
				
				return true;
				
			}else if(args[0].equalsIgnoreCase("load")) {
				
				Player p = (sender instanceof Player)?(Player) sender:null;
				
				plugin.getLevelSelector().ChangeLevel(args[1], p);
				return true;
				
			}else if(args[0].equalsIgnoreCase("delete")) {
				
				if(!plugin.getLevelLoader().deleteLevel(args[1])) {
					if(sender instanceof Player) {
						sender.sendMessage(ChatColor.RED + "Unable to delete level");
					}else{
						sender.sendMessage("Unable to delete level");
					}
				}else{
					if(sender instanceof Player) {
						sender.sendMessage(ChatColor.GREEN + "Level deleted successfully");
					}else{
						sender.sendMessage("Level deleted successfully");
					}
				}
				return true;
				
			}
		}else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("save")) {
				
				if(!plugin.getLevelLoader().saveLevel(plugin.getLevel())){
					if(sender instanceof Player) {
						sender.sendMessage(ChatColor.GREEN + "Unable to save current level");
					}else{
						sender.sendMessage("Unable to save current level");
					}
				}else{
					if(sender instanceof Player) {
						sender.sendMessage(ChatColor.GREEN + "Level save successful");
					}else{
						sender.sendMessage("Level save successful");
					}
				}
				return true;
			}else if(args[0].equalsIgnoreCase("list")) {
				Set<String> levels = plugin.getLevelLoader().getLevelList();
				String msg = "";
				String separator = "";
				for(String level: levels) {
					msg += separator + level;
					separator = ", ";
				}
				if(sender instanceof Player) {
					sender.sendMessage(ChatColor.GREEN + "Levels :" + msg);
				}else{
					sender.sendMessage("Levels :" + msg);
				}
				return true;
			}
		}
		
		return false;
	}

}
