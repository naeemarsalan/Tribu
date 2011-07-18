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
		votes = new HashMap<Player, Integer>();
		votingEnabled = false;
	}

	public void run() {
		taskID = -1;
		votingEnabled = false;
		int[] voteCounts = new int[2];
		Collection<Integer> nums = votes.values();
		for (int vote : nums) {
			voteCounts[vote - 1]++;
		}
		votes.clear();
		if (voteCounts[0] >= voteCounts[1]) {
			ChangeLevel(randomLevel1, null);
			plugin.getServer().broadcastMessage(String.format(Constants.BroadcastMapChosen,randomLevel1));
		} else {
			ChangeLevel(randomLevel2, null);
			plugin.getServer().broadcastMessage(String.format(Constants.BroadcastMapChosen,randomLevel2));
		}
		plugin.startRunning();
	}

	public void startVote(int duration) {
		String[] levels = plugin.getLevelLoader().getLevelList()
				.toArray(new String[0]);

		if (levels.length < 2) { // Skip voting since there's only one option
			plugin.startRunning();
			return;
		}
		taskID = plugin.getServer().getScheduler()
				.scheduleSyncDelayedTask(plugin, this, duration);
		votingEnabled = true;
		plugin.getServer().broadcastMessage(Constants.BroadcastMapVoteStarting);
		plugin.getServer().broadcastMessage(Constants.BroadcastType);

		do {
			randomLevel1 = levels[rnd.nextInt(levels.length)];
		} while (randomLevel1 == plugin.getLevel().getName());

		if (levels.length >= 3) {
			do {
				randomLevel2 = levels[rnd.nextInt(levels.length)];
			} while (randomLevel2 == plugin.getLevel().getName()
					|| randomLevel2 == randomLevel1);
		} else {
			randomLevel2 = plugin.getLevel().getName();
		}
		plugin.getServer().broadcastMessage(String.format(Constants.BroadcastSlashVoteForMap,'1',randomLevel1));
		plugin.getServer().broadcastMessage(String.format(Constants.BroadcastSlashVoteForMap,'2',randomLevel2));
		
		plugin.getServer().broadcastMessage(String.format(Constants.BroadcastVoteClosingInSeconds, String.valueOf(duration / 20)));
				
	}

	public void castVote(Player player, String vote) {
		if (votingEnabled) {
			int v = 0;

			try {
				v = Integer.parseInt(vote);
			} catch (Exception e) {
			}

			if (v > 2 || v < 1) {
				player.sendMessage(Constants.MessageInvalidVote);
				return;
			}

			votes.put(player, v);
			player.sendMessage(Constants.MessageThankyouForYourVote);
		} else {
			player.sendMessage(Constants.MessageYouCannotVoteAtThisTime);
		}
	}

	public void cancelVote() {
		if (taskID >= 0) {
			plugin.getServer().getScheduler().cancelTask(taskID);
		}
	}

	public void ChangeLevel(String name, Player player) {
		if (plugin.getLevel() != null) {
			if (plugin.getLevel().getName() == name) {
				if (player != null) {
					player.sendMessage(String.format(Constants.MessageLevelIsAlreadyTheCurrentLevel,name));
				} else {
					plugin.LogInfo(String.format(ChatColor.stripColor(Constants.MessageLevelIsAlreadyTheCurrentLevel),name));
				}
				return;
			}
		}

		cancelVote();
		boolean restart = false;
		if (plugin.isRunning()) {
			restart = true;
		}

		plugin.stopRunning();

		ZSurvivalLevel temp = plugin.getLevelLoader().loadLevel(name);

		if (!plugin.getLevelLoader().saveLevel(plugin.getLevel())) {
			if (player != null) {
				player.sendMessage(Constants.MessageUnableToSaveLevel);
			} else {
				plugin.LogWarning(ChatColor.stripColor(Constants.MessageUnableToSaveLevel));
			}
			return;
		}

		if (temp == null) {
			if (player != null) {
				player.sendMessage(Constants.MessageUnableToLoadLevel);
			} else {
				plugin.LogWarning(ChatColor.stripColor(Constants.MessageUnableToLoadLevel));
			}
			return;
		} else {
			if (player != null) {
				player.sendMessage(Constants.MessageLevelLoadedSuccessfully);
			} else {
				plugin.LogInfo(ChatColor.stripColor(Constants.MessageLevelLoadedSuccessfully));
			}
		}

		plugin.setLevel(temp);
		if (restart) {
			plugin.startRunning();
		}

	}

}
