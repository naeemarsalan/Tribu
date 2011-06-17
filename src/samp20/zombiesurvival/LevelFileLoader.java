package samp20.zombiesurvival;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

public class LevelFileLoader {

	private ZombieSurvival plugin;
	private Set<String> levels;
	
	public LevelFileLoader(ZombieSurvival instance) {
		plugin = instance;
		levels = new HashSet<String>();
		
		File dir = new File(Constants.levelFolder);
		
		levels.clear();
		File[] files = dir.listFiles();
		plugin.LogInfo(String.valueOf(files.length) + " levels found");
		if(files != null) {
			for(File file: files) {
				levels.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
			}
		}
		
	}
	
	public Set<String> getLevelList() {
		return levels;
	}

	public ZSurvivalLevel loadLevel(String name) {
		ZSurvivalLevel level = null;
		try {
			File file = new File(Constants.levelFolder + "/" + name  + ".lvl");
			if(!file.exists()) {
				return null;
			}
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			int version = in.readByte();
			if(version != Constants.LevelFileVersion) {
				fstream.close();
				plugin.LogSevere("World invalid file version");
				return null;
			}
			World world = plugin.getServer().getWorld(in.readUTF());
			if(world == null) {
				fstream.close();
				plugin.LogSevere("World doesn't exist");
				return null;
			}
			double sx,sy,sz; //spawn coords
			double dx,dy,dz; //Death coords
			float sYaw, dYaw;
			sx = in.readDouble();
			sy = in.readDouble();
			sz = in.readDouble();
			sYaw = in.readFloat();
			
			dx = in.readDouble();
			dy = in.readDouble();
			dz = in.readDouble();
			dYaw = in.readFloat();
			
			Location spawn = new Location(world, sx, sy, sz, sYaw, 0.0f);
			Location death = new Location(world, dx, dy, dz, dYaw, 0.0f);
			
			level = new ZSurvivalLevel(name,spawn);
			level.setDeath(death);
			
			int spawncount = in.readInt();
			
			Location pos;
			String spawnName;
			
			for(int i = 0;i<spawncount;i++) {
				sx = in.readDouble();
				sy = in.readDouble();
				sz = in.readDouble();
				sYaw = in.readFloat();
				spawnName = in.readUTF();
				pos = new Location(world, sx, sy, sz, sYaw, 0.0f);
				level.addZombieSpawn(pos, spawnName);
			}
		}catch (Exception e) {
			plugin.LogSevere("Exception: " + e.toString());
			
			level = null;
		}
		return level;
	}

	public ZSurvivalLevel newLevel(String name, Location spawn) {
		return new ZSurvivalLevel(name,spawn);
	}

	public boolean saveLevel(ZSurvivalLevel level) {
		if(level == null){
			return true; //Sorta successful since a save isn't really needed and nothing failed
		}
		
		if(!level.hasChanged()){
			return true; //No need to save since the level hasn't changed
		}
		
		FileOutputStream out;
		DataOutputStream o;
		try {
			out = new FileOutputStream(Constants.levelFolder + "/" + level.getName() + ".lvl",false);
			o = new DataOutputStream(out);
			Location spawn = level.getSpawn();
			Location death = level.getDeath();
			
			o.writeByte(Constants.LevelFileVersion);
			
			o.writeUTF(spawn.getWorld().getName());
			o.writeDouble(spawn.getX());
			o.writeDouble(spawn.getY());
			o.writeDouble(spawn.getZ());
			o.writeFloat(spawn.getYaw());
			
			o.writeDouble(death.getX());
			o.writeDouble(death.getY());
			o.writeDouble(death.getZ());
			o.writeFloat(death.getYaw());
			
			HashMap<String,Location> zombieSpawns = level.getSpawns();
			Set<Entry<String,Location>> set = zombieSpawns.entrySet();
			
			o.writeInt(set.size());
			for(Entry<String,Location> zspawn : set) {
				o.writeDouble(zspawn.getValue().getX());
				o.writeDouble(zspawn.getValue().getY());
				o.writeDouble(zspawn.getValue().getZ());
				o.writeFloat(zspawn.getValue().getYaw());
				o.writeUTF(zspawn.getKey());
			}
			o.flush();
			o.close();
			out.close();
		}catch(Exception e) {
			plugin.LogSevere(e.getMessage());
			return false;
		}
		levels.add(level.getName());
		return true;
	}

	public boolean deleteLevel(String name) {
		File file = new File(Constants.levelFolder + "/" + name  + ".lvl");
		if(file.exists()) {
			boolean result = file.delete();
			if(!result){
				plugin.LogWarning("IO error on file delete");
			}else{
				levels.remove(name);
			}
			return result;
		}
		return false;
	}
}
