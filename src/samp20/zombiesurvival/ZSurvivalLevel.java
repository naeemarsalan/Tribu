package samp20.zombiesurvival;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ZSurvivalLevel {
	private String name;
	private Location spawn;
	private Location death;
	private HashMap<String,Location> zombieSpawns;
	private Random rnd = new Random();
	private boolean changed; //For deciding whether the level needs saving again
	
	public ZSurvivalLevel(String name,Location spawn) {
		zombieSpawns = new HashMap<String,Location>();
		this.name = name;
		this.spawn = spawn;
		this.death = spawn;
		changed = false;
	}
	
	public boolean setDeath(Location loc) {
		if(loc.getWorld() == spawn.getWorld()) {
			death = loc;
			changed = true;
			return true;
		}
		return false;
	}
	
	public Location getSpawn() {
		return spawn;
	}
	
	public Location getDeath() {
		return death;
	}
	
	public boolean setSpawn(Location loc) {
		if(loc.getWorld() == spawn.getWorld()) {
			spawn = loc;
			changed = true;
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getZombieSpawn(String name) {
		return zombieSpawns.get(name);
	}
	
	public void addZombieSpawn(Location loc, String name) {
		zombieSpawns.put(name, loc);
		changed = true;
	}
	
	public void ListSpawns(Player player) {
		Set<String> names = zombieSpawns.keySet();
		String nameList = "";
		String separator = "";
		for(String name: names) {
			nameList += separator + name ;
			separator = ", ";
		}
		player.sendMessage(ChatColor.GREEN + nameList);
	}
	
	public Location getRandomSpawn() {
		if(zombieSpawns.size() == 0){
			return null;
		}
		
		Location[] spawns = zombieSpawns.values().toArray(new Location[0]);
		return spawns[rnd.nextInt(spawns.length)];
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setSaved() {
		changed = false;
	}
	
	public HashMap<String,Location> getSpawns() {
		return zombieSpawns;
	}
	
	public void removeZombieSpawn(String name) {
		zombieSpawns.remove(name);
		changed = true;
	}

}
