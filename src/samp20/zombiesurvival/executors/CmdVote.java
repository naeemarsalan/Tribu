package samp20.zombiesurvival.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import samp20.zombiesurvival.ZombieSurvival;

public class CmdVote implements CommandExecutor {
	private ZombieSurvival plugin;
	
	public CmdVote(ZombieSurvival instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			plugin.LogWarning("This command cannot be used from the console");
			return true;
		}
		Player player = (Player) sender;
		
		if(args.length == 1) {
			plugin.getLevelSelector().castVote(player,args[0]);
			return true;
		}
		
		return false;
	}

}
