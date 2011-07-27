package graindcafe.tribu;

import org.bukkit.Location;
import org.bukkit.event.Event;

public class SpawnControlToggleSign extends SpawnControlSign {
	protected boolean state;

	public SpawnControlToggleSign(Tribu plugin, Location pos, String[] Lines) {
		super(plugin, pos, Lines);
		state = Lines[2].equalsIgnoreCase("on") || Lines[2].equals("1"); 
	}

	@Override
	public void raiseEvent(Event e) {
		
		if (pos.getBlock().isBlockPowered()) {
			if (state) {
				plugin.getLevel().activateZombieSpawn(ZombieSpawn);

			} else {
				plugin.getLevel().desactivateZombieSpawn(ZombieSpawn);

			}
		}
	}

}
