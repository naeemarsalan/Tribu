package samp20.zombiesurvival;



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
		if (plugin.isRunning()) {
			scheduled = false;
			plugin.revivePlayers(false);
			int health;
			int total;
			int max = plugin.getAliveCount() * 2 + waveNumber / 2 + 1;
			total = waveNumber * 5;
			health = (int) (waveNumber * 0.5) + 4;
			plugin.getSpawnTimer().StartWave(total, max, health);
			plugin.getServer().broadcastMessage(String.format(Constants.BroadcastStartingWave,String.valueOf(waveNumber) , String.valueOf(total),String.valueOf(health)));
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
		if (!scheduled && plugin.isRunning()) {
			taskID = plugin.getServer().getScheduler()
					.scheduleSyncDelayedTask(plugin, this, delay);
			scheduled = true;
			plugin.getServer().broadcastMessage(String.format(Constants.BroadcastWave, String.valueOf(plugin.getWaveStarter().getWave()),String.valueOf(delay / 20)));
		}
	}

	public void cancelWave() {
		if (scheduled) {
			plugin.getServer().getScheduler().cancelTask(taskID);
			scheduled = false;
		}
	}

}
