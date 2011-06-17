package samp20.zombiesurvival;

import org.bukkit.ChatColor;

public class WaveStarter implements Runnable {
	private ZombieSurvival plugin;
	private int waveNumber;
	private int taskID;
	private boolean scheduled;
	
	public WaveStarter(ZombieSurvival instance) {
		plugin = instance;
		waveNumber = 1;
		scheduled = false;
	}
	
	public void run() {
		if(plugin.running()) {
			scheduled = false;
			plugin.revivePlayers(false);
			int health;
			int total;
			int max = plugin.getAliveCount()*2 + waveNumber/2 + 1;
			total = waveNumber*5;
			health = (int) (waveNumber*0.5) + 4;
			plugin.getSpawnTimer().StartWave(total, max, health);
			plugin.getServer().broadcastMessage(ChatColor.GREEN + "Starting wave "
					+ ChatColor.LIGHT_PURPLE + String.valueOf(waveNumber) + ChatColor.GREEN + ", "
					+ ChatColor.LIGHT_PURPLE + String.valueOf(total) + ChatColor.GREEN + " Zombies @ "
					+ ChatColor.LIGHT_PURPLE + String.valueOf(health) + ChatColor.GREEN + " health");
		}
	}
	
	public void incrementWave() {
		waveNumber++;
	}
	
	public void resetWave() {
		waveNumber = 1;
	}
	
	public int getWave() {
		return waveNumber;
	}
	
	public void scheduleWave(int delay) {
		if(!scheduled && plugin.running()) {
			taskID = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, delay);
			scheduled = true;
			plugin.getServer().broadcastMessage(ChatColor.GREEN + "Wave " +
					ChatColor.LIGHT_PURPLE + String.valueOf(plugin.getWaveStarter().getWave()) + ChatColor.GREEN + " starting in " +
					ChatColor.LIGHT_PURPLE + String.valueOf(delay/20) + ChatColor.GREEN +
					" seconds.");
		}
	}
	
	public void cancelWave() {
		if(scheduled) {
			plugin.getServer().getScheduler().cancelTask(taskID);
			scheduled = false;
		}
	}

}
