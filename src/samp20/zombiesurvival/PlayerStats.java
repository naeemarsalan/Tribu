package samp20.zombiesurvival;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerStats {
	private Player player;
	private int money;
	private int points;
	private boolean alive;
	
	public PlayerStats(Player player) {
		this.player = player;
		alive = false;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void resetMoney() {
		money = 0;
	}
	
	public void subtractPoints(int val) {
		points -= val;
		if(points < 0){
			points = 0;
		}
	}
	
	public void msgStats() {
		player.sendMessage(ChatColor.GREEN + "Money: " +
				ChatColor.DARK_PURPLE + String.valueOf(money) +
				"$" + ChatColor.GREEN + " Points: " +
				ChatColor.RED + String.valueOf(points));
	}
	
	public void resetPoints() {
		points = 0;
	}
	
	public void kill() {
		alive = false;
	}
	
	public void revive() {
		alive = true;
	}
	
	public boolean isalive() {
		return alive;
	}
	
	public boolean subtractmoney(int amount) {
		if(money>= amount){
			money -= amount;
			return true;
		}
		return false;
	}
	
	public void addPoints(int amount) {
		points += amount;
	}
	
	public void addMoney(int amount) {
		money += amount;
	}

}
