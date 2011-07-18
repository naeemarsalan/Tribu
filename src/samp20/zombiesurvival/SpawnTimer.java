package samp20.zombiesurvival;

public class SpawnTimer implements Runnable {

	private ZombieSurvival plugin;
	private int taskID;
	private int totalSpawn;

	SpawnTimer(ZombieSurvival instance) {
		plugin = instance;
		taskID = -1;
	}

	public void run() {
		if (plugin.getSpawner().getTotal() < totalSpawn && plugin.isRunning()
				&& plugin.getAliveCount() > 0) {
			plugin.getSpawner().SpawnZombie();
		} else {
			Stop();
		}

	}

	public void StartWave(int total, int max, int health) {
		if (plugin.isRunning()) {
			totalSpawn = total;
			plugin.getSpawner().setMaxSpawn(max);
			plugin.getSpawner().resetTotal();
			plugin.getSpawner().setHealth(health);
			Start();
		}
	}

	public void Start() {
		taskID = plugin
				.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(plugin, this, Constants.SpawnDelay,
						Constants.SpawnDelay);
	}

	public void Stop() {
		if (taskID > 0) {
			plugin.getServer().getScheduler().cancelTask(taskID);
			plugin.getSpawner().enableFinishCallback();
			taskID = -1;
		}
	}

}
