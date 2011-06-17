package samp20.zombiesurvival;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LevelSelector implements Runnable {
	private ZombieSurvival plugin;
	private int taskID;
	private Random rnd;
	private String randomLevel1;
	private String randomLevel2;
	private HashMap<Player, Integer> votes;
	private boolean votingEnabled;
	
	public LevelSelector(ZombieSurvival instance) {
		plugin = instance;
		taskID = -1;
		rnd = new Random();
		votes = new HashMap<Player,Integer>();
		votingEnabled = false;
	}
	
	public void run() {
		taskID = -1;
		votingEnabled = false;
		int[] voteCounts = new int[2];
		Collection<Integer> nums = votes.values();
		for(int vote: nums) {
			voteCounts[vote-1]++;
		}
		votes.clear();
		if(voteCounts[0] >= voteCounts[1]) {
			ChangeLevel(randomLevel1, null);
			plugin.getServer().broadcastMessage(ChatColor.GREEN + "Map " + ChatColor.LIGHT_PURPLE + randomLevel1 +
					ChatColor.GREEN + " has been chosen");
		}else{
			ChangeLevel(randomLevel2, null);
			plugin.getServer().broadcastMessage(ChatColor.GREEN + "Map " + ChatColor.LIGHT_PURPLE + randomLevel2 +
					ChatColor.GREEN + " has been chosen");
		}
		plugin.startRunning();
	}
	
	public void startVote(int duration) {		
		String[] levels = plugin.getLevelLoader().getLevelList().toArray(new String[0]);
		
		if(levels.length < 2) { //Skip voting since there's only one option
			plugin.startRunning();
			return;
		}
		taskID = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, duration);
		votingEnabled = true;
		plugin.getServer().broadcastMessage(ChatColor.GREEN + "Map vote starting,");
		plugin.getServer().broadcastMessage(ChatColor.GREEN + "Type ");

		do {
			randomLevel1 = levels[rnd.nextInt(levels.length)];
			}while(randomLevel1 == plugin.getLevel().getName());
		
		if(levels.length >= 3) {
			do {
				randomLevel2 = levels[rnd.nextInt(levels.length)];
				}while(randomLevel2 == plugin.getLevel().getName() || randomLevel2 == randomLevel1);
		}else{
			randomLevel2 = plugin.getLevel().getName();
		}
		plugin.getServer().broadcastMessage(ChatColor.GOLD + "'/vote 1'" + ChatColor.GREEN + " for map " + ChatColor.LIGHT_PURPLE + randomLevel1);
		plugin.getServer().broadcastMessage(ChatColor.GOLD + "'/vote 2'" + ChatColor.GREEN + " for map " + ChatColor.LIGHT_PURPLE +  randomLevel2);
		plugin.getServer().broadcastMessage(ChatColor.GREEN + "Vote closing in " + ChatColor.LIGHT_PURPLE +  String.valueOf(duration/20) +
				ChatColor.GREEN + " seconds" );
	}
	
	public void castVote(Player player,String vote) {
		if(votingEnabled) {
			int v = 0;
			
			try {
				v = Integer.parseInt(vote);
			}catch(Exception e){}
			
			if(v > 2 || v < 1){
				player.sendMessage(ChatColor.RED + "Invalid vote");
				return;
			}

			votes.put(player, v);
			player.sendMessage(ChatColor.GREEN + "Thankyou for your vote");
		}else{
			player.sendMessage(ChatColor.RED + "You cannot vote at this time");
		}
	}
	
	public void cancelVote() {
		if(taskID >= 0) {
			plugin.getServer().getScheduler().cancelTask(taskID);
		}
	}
	
	public void ChangeLevel(String name, Player player) {
		if(plugin.getLevel() != null) {
			if(plugin.getLevel().getName() == name) {
				if(player != null){
					player.sendMessage(ChatColor.RED + "Level " + name + " is already the current level");
				}else{
					plugin.LogInfo("Level " + name + " is already the current level");
				}
				return;
			}
		}
		
		cancelVote();
		boolean restart = false;
		if(plugin.running()){
			restart = true;
		}
		
		plugin.stopRunning();

		
		ZSurvivalLevel temp = plugin.getLevelLoader().loadLevel(name);
		
		if(!plugin.getLevelLoader().saveLevel(plugin.getLevel())) {
			if(player != null){
				player.sendMessage(ChatColor.RED + "Unable to save level, try again later");
			}else{
				plugin.LogWarning("Unable to save level, try again later");
			}
			return;
		}
		
		if(temp == null) {
			if(player != null){
				player.sendMessage(ChatColor.RED + "Unable to load level");
			}else{
				plugin.LogWarning("Unable to load level");
			}
			return;
		}else{
			if(player != null){
				player.sendMessage(ChatColor.GREEN + "Level loaded successfully");
			}else{
				plugin.LogInfo("Level loaded successfully");
			}
		}
		
		plugin.setLevel(temp);
		if(restart) {
			plugin.startRunning();
		}
		
	}

}
