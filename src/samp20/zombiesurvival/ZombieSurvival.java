package samp20.zombiesurvival;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import samp20.zombiesurvival.executors.CmdDspawn;
import samp20.zombiesurvival.executors.CmdIspawn;
import samp20.zombiesurvival.executors.CmdLevel;
import samp20.zombiesurvival.executors.CmdVote;
import samp20.zombiesurvival.executors.CmdZombieMode;
import samp20.zombiesurvival.executors.CmdZspawn;
import samp20.zombiesurvival.listeners.ZSurvivalBListener;
import samp20.zombiesurvival.listeners.ZSurvivalEListener;
import samp20.zombiesurvival.listeners.ZSurvivalPListener;


public class ZombieSurvival extends JavaPlugin {
	private ZSurvivalPListener playerListener;
	private ZSurvivalEListener entityListener;
	private ZSurvivalBListener blockListener;
	
	private LevelFileLoader levelLoader;
	private LevelSelector levelSelector;
	private ZSurvivalLevel level;
	
	private ZSurvivalSpawner spawner;
	private SpawnTimer spawnTimer;
	private WaveStarter waveStarter;
	
	private Logger log;
	private boolean isRunning;
	private int aliveCount;
	
	private HashMap<Player,PlayerStats> players;
	
	public void onDisable() {
		stopRunning();
		
		LogInfo("Stopping ZombieSurvival");
	}

	public void onEnable() {
		// TODO Auto-generated method stub
		log = Logger.getLogger("Minecraft");
		isRunning = false;
		aliveCount = 0;
		level = null;
		players = new HashMap<Player,PlayerStats>();
		
		levelLoader = new LevelFileLoader(this);
		levelSelector = new LevelSelector(this);
		//Create listeners
		playerListener = new ZSurvivalPListener(this);
		entityListener = new ZSurvivalEListener(this);
		blockListener = new ZSurvivalBListener(this);
		PluginManager pm = getServer().getPluginManager();
		
		playerListener.registerEvents(pm);
		entityListener.registerEvents(pm);
		blockListener.registerEvents(pm);
		
		spawner = new ZSurvivalSpawner(this);
		spawnTimer = new SpawnTimer(this);
		waveStarter = new WaveStarter(this);
		
		getCommand("level").setExecutor(new CmdLevel(this));
		getCommand("dspawn").setExecutor(new CmdDspawn(this));
		getCommand("zspawn").setExecutor(new CmdZspawn(this));
		getCommand("ispawn").setExecutor(new CmdIspawn(this));
		getCommand("vote").setExecutor(new CmdVote(this));
		getCommand("zombiemode").setExecutor(new CmdZombieMode(this));
	}
	
	public void stopRunning() {
		if(isRunning == true) {
		isRunning = false;
		getSpawnTimer().Stop();
		getWaveStarter().cancelWave();
		getSpawner().clearZombies();
		getLevelSelector().cancelVote();
		}
		
	}
	
	public void startRunning() {
		if(isRunning == false && getLevel() != null) {
			isRunning = true;
			
			//Make sure no data is lost if server decides to die
			//during a game and player forgot to /level save
			if(!getLevelLoader().saveLevel(getLevel())) {
				LogWarning("Unable to save level");
			}else{
				LogInfo("Level saved");
			}
			
			Set<Entry<Player,PlayerStats>> stats = players.entrySet();
			for(Entry<Player,PlayerStats> stat: stats) {
				stat.getValue().resetPoints();
				stat.getValue().resetMoney();
			}
			
			getWaveStarter().resetWave();
			revivePlayers(true);
			getWaveStarter().scheduleWave(Constants.NextRoundDelay);
		}
	}
	
	public boolean running() {
		return isRunning;
	}
	
	public void setLevel(ZSurvivalLevel level){
		this.level = level;
	}
	
	public ZSurvivalSpawner getSpawner() {
		return spawner;
	}
	
	public ZSurvivalLevel getLevel() {
		return level;
	}
	
	public SpawnTimer getSpawnTimer() {
		return spawnTimer;
	}
	
	public WaveStarter getWaveStarter() {
		return waveStarter;
	}
	
	public LevelFileLoader getLevelLoader() {
		return levelLoader;
	}
	
	public LevelSelector getLevelSelector() {
		return levelSelector;
	}
	
	
	
	public boolean isalive(Player player) {
		return players.get(player).isalive();
	}
	
	public void revivePlayers(boolean teleportAll) {
		Player[] players = getServer().getOnlinePlayers();
		aliveCount = 0;
		for(Player player: players) {
			revivePlayer(player);
			if(isRunning && level != null && (teleportAll || !isalive(player))) {
				player.teleport(level.getSpawn());
			}
		}
	}
	
	public void addPlayer(Player player) {
		PlayerStats stats = new PlayerStats(player);
		players.put(player, stats);
		if(getLevel() != null && isRunning) {
			player.teleport(level.getDeath());
			player.sendMessage(ChatColor.GREEN + "Game in progress, you will spawn next round");
		}
		
	}
	
	public void revivePlayer(Player player) {
		players.get(player).revive();
		player.setHealth(20);
		aliveCount++;
	}
	
	public void setDead(Player player) {
		if(isalive(player)) {
			aliveCount--;
			Set<Entry<Player,PlayerStats>> stats = players.entrySet();
			for(Entry<Player,PlayerStats> stat: stats) {
				stat.getValue().subtractPoints(50);
				stat.getValue().resetMoney();
				stat.getValue().msgStats();
			}
        }
		players.get(player).kill();
		if(getLevel() != null && isRunning) {
			checkAliveCount();
		}
	}
	
	public void removePlayer(Player player) {
		if(isalive(player)) {
			aliveCount--;
        }
		players.remove(player);
		checkAliveCount();
	}
	
	public PlayerStats getStats(Player player){
		return players.get(player);
	}
	
	public void checkAliveCount() {
		if(aliveCount == 0 && isRunning) {
			stopRunning();
			
			getServer().broadcastMessage(ChatColor.GREEN + "Zombies have prevailed!");
			getServer().broadcastMessage(ChatColor.GREEN + "You have reached wave " + ChatColor.LIGHT_PURPLE +
					String.valueOf(getWaveStarter().getWave()));
			getLevelSelector().startVote(Constants.VoteDelay);
		}
	}
	
	public int getAliveCount() {
		return aliveCount;
	}
	
	public void LogInfo(String message) {
		log.info(message);
	}
	
	public void LogWarning(String message) {
		log.warning(message);
	}
	
	public void LogSevere(String message) {
		log.severe(message);
	}

}
