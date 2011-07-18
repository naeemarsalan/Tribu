package samp20.zombiesurvival;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ZSurvivalSpawner {
	private final ZombieSurvival plugin;
	private HashMap<LivingEntity, CleverMob> zombies = new HashMap<LivingEntity, CleverMob>();

	private int totalSpawned;
	private int maxSpawn;
	private boolean finished;
	private int health;
	private boolean justspawned;

	public ZSurvivalSpawner(ZombieSurvival instance) {
		plugin = instance;
		totalSpawned = 0;
		maxSpawn = 5;
		finished = false;
		health = 10;
	}

	public void SpawnZombie() {
		if (zombies.size() >= maxSpawn || finished == true) {
			return;
		}

		Location pos = plugin.getLevel().getRandomSpawn();
		if (pos == null) {
			return;
		}

		// Surrounded with justspawned so that the zombie isn't
		// removed in the entity spawn listener
		justspawned = true;
		LivingEntity zombie = pos.getWorld().spawnCreature(pos,
				CreatureType.ZOMBIE);
		justspawned = false;

		zombies.put(zombie, new CleverMob(zombie));
		zombie.setHealth(health);
		totalSpawned++;

	}

	public boolean justSpawned() {
		return justspawned;
	}

	public boolean isSpawned(LivingEntity ent) {
		return zombies.containsKey(ent);
	}

	public int getTotal() {
		return totalSpawned;
	}

	public CleverMob getCleverMob(LivingEntity mob) {
		return zombies.get(mob);
	}

	public void resetTotal() {
		totalSpawned = 0;
		finished = false;
	}

	public void setMaxSpawn(int count) {
		maxSpawn = count;
	}

	public void enableFinishCallback() {
		finished = true;
	}

	public void setHealth(int value) {
		health = value;
	}

	public void despawnZombie(LivingEntity zombie, List<ItemStack> drops) {
		if (zombies.containsKey(zombie)) {
			zombies.remove(zombie);
			drops.clear();
			if (zombies.size() == 0 && finished == true) {
				plugin.getServer().broadcastMessage(Constants.BroadcastWaveComplete);
				plugin.getWaveStarter().incrementWave();
				plugin.getWaveStarter().scheduleWave(Constants.NextRoundDelay);
			}
		}
	}

	public void clearZombies() {
		for (LivingEntity zombie : zombies.keySet()) {
			zombie.remove();
		}
		resetTotal();
		zombies.clear();
	}

}
