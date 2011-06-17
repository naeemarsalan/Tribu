package samp20.zombiesurvival.listeners;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import samp20.zombiesurvival.PlayerStats;
import samp20.zombiesurvival.ZombieSurvival;

public class ZSurvivalPListener extends PlayerListener{
	private final ZombieSurvival plugin;
	
	public ZSurvivalPListener(ZombieSurvival instance) {
		plugin = instance;
	}
	
	public void registerEvents(PluginManager pm) {
		pm.registerEvent(Event.Type.PLAYER_JOIN, this, Priority.Normal, plugin);
		pm.registerEvent(Event.Type.PLAYER_QUIT, this, Priority.Normal, plugin);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this, Priority.Normal, plugin);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, this, Priority.Normal, plugin);
	}

	@Override
    public void onPlayerJoin(PlayerEvent event) {
		plugin.addPlayer(event.getPlayer());
    }

    @Override
    public void onPlayerQuit(PlayerEvent event) {
        plugin.removePlayer(event.getPlayer());
    }
    
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	plugin.setDead(event.getPlayer());
    	if(plugin.getLevel() != null) {
    		event.setRespawnLocation(plugin.getLevel().getDeath());
    	}
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if(!event.isCancelled() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		
	    	Block block = event.getClickedBlock();
	    	if(block != null) {
	    		
		    	if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
		    		Sign sign = (Sign) block.getState();
		    		if(sign.getLine(0).trim().equalsIgnoreCase("buy")) {
		    			int price = Integer.parseInt(sign.getLine(3));
		    			PlayerStats stats = plugin.getStats(event.getPlayer());
		    			
		    			if(stats!=null) {
		    				
			    			if(stats.subtractmoney(price)) {
			    				ItemStack is = new ItemStack(Material.getMaterial(sign.getLine(1) + "_" + sign.getLine(2)));
			    				is.setAmount(1);
			    				HashMap<Integer,ItemStack> failed = event.getPlayer().getInventory().addItem(is);
			    				event.getPlayer().updateInventory();
			    				if(failed.size() > 0) {
			    					event.getPlayer().sendMessage("Unable to give you that item...");
			    					stats.addMoney(price);
			    				}else{
			    				event.getPlayer().sendMessage(ChatColor.GREEN + "Purchase successful. Money: " +
										ChatColor.DARK_PURPLE + String.valueOf(stats.getMoney()) +
										"$");
			    				}
			    			}else{
			    				event.getPlayer().sendMessage(ChatColor.DARK_RED + "You don't have enough money for that!");
			    			}
			    			
		    			}
		    		}
		    	}
	    	}
    	}
    }
}
