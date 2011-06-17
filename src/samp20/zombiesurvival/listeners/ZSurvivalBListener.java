package samp20.zombiesurvival.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;

import samp20.zombiesurvival.ZombieSurvival;

public class ZSurvivalBListener extends BlockListener {
	private ZombieSurvival plugin;
	
	public ZSurvivalBListener(ZombieSurvival instance) {
		plugin = instance;
	}
	
	public void registerEvents(PluginManager pm) {
		pm.registerEvent(Event.Type.BLOCK_BREAK, this, Priority.Low, plugin);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this, Priority.Low, plugin);
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if(!event.getPlayer().isOp()) {
			event.setCancelled(true);
		}
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if(!event.getPlayer().isOp()) {
			event.setCancelled(true);
		}
	}

}
