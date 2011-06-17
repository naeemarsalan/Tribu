package samp20.zombiesurvival.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.PluginManager;

import samp20.zombiesurvival.CleverMob;
import samp20.zombiesurvival.PlayerStats;
import samp20.zombiesurvival.ZombieSurvival;

public class ZSurvivalEListener extends EntityListener {
	private ZombieSurvival plugin;
	
	public ZSurvivalEListener(ZombieSurvival instance) {
		plugin = instance;
	}
	
	public void registerEvents(PluginManager pm) {
		pm.registerEvent(Event.Type.ENTITY_DEATH, this, Priority.Monitor, plugin);
		pm.registerEvent(Event.Type.CREATURE_SPAWN, this, Priority.Lowest, plugin);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Priority.High, plugin);
	}
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(!plugin.getSpawner().justSpawned()) {
			event.setCancelled(true);
		}
	}
	
	@Override 
	public void onEntityDamage(EntityDamageEvent dam) {
		if(dam.isCancelled()){
			return;
		}
		if(dam.getCause() == DamageCause.ENTITY_ATTACK) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) dam;
			if(event.getEntity() instanceof LivingEntity){
				
				if(plugin.getSpawner().isSpawned((LivingEntity) event.getEntity())){
					
					if(event.getDamager() instanceof Player) {
						CleverMob mob = plugin.getSpawner().getCleverMob((LivingEntity) event.getEntity());
						mob.setAttacker((Player) event.getDamager());
					}
					
				}
				
			}
		}
	}
	
	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player)event.getEntity();
				plugin.setDead(player);
				event.getDrops().clear();
			}else{
				LivingEntity zombie = (LivingEntity) event.getEntity();
				CleverMob mob = plugin.getSpawner().getCleverMob(zombie);
				if(mob != null) {
					Player player = mob.getLastAttacker();
					if(player != null && player.isOnline()) {
						PlayerStats stats = plugin.getStats(player);
						if(stats != null) {
							stats.addMoney(15);
							stats.addPoints(10);
							stats.msgStats();
						}else{
							mob.setAttacker(null);
						}
					}
				}
				plugin.getSpawner().despawnZombie(zombie,event.getDrops());
			}
		}
	}


}
