package graindcafe.tribu;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ShopSign extends TribuSign {

	public Material Item = null;
	public int Cost = 0;

	public ShopSign(Tribu plugin) {
		super(plugin);
	}

	public ShopSign(Tribu plugin, Location pos, String item, int Cost) {
		super(plugin, pos);
		Item = Material.getMaterial(item);
		this.Cost = Cost;
	}

	public ShopSign(Tribu plugin, Location pos, String[] signLines) {
		super(plugin, pos);

		Item = Material.getMaterial(signLines[1].toUpperCase() + "_" + signLines[2].toUpperCase());
		// If the item is inexistent, let's try with
		// only the second line
		if (Item == null)
			Item = Material.getMaterial(signLines[1].toUpperCase());
		// Still no ? With the third one, so
		if (Item == null)
			Item = Material.getMaterial(signLines[2].toUpperCase());
		Cost = Integer.parseInt(signLines[3]);

	}

	@Override
	public boolean isUsedEvent(Event e) {
		return e instanceof PlayerInteractEvent;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void raiseEvent(Event e) {
		Player p = ((PlayerInteractEvent) e).getPlayer();
		PlayerStats stats = plugin.getStats(p);
		if (stats.subtractmoney(Cost)) {
			if (Item != null) {
				// The item is correct
				ItemStack is = new ItemStack(Item);
				is.setAmount(1);
				HashMap<Integer, ItemStack> failed = p.getInventory().addItem(is);

				p.updateInventory();
				if (failed.size() > 0) {
					// maybe the inventory is full
					p.sendMessage(Constants.MessageUnableToGiveYouThatItem);
					stats.addMoney(Cost);
				} else {
					// Alright
					p.sendMessage(String.format(Constants.MessagePurchaseSuccessfulMoney, String.valueOf(stats.getMoney())));
				}
			} else
				p.sendMessage(Constants.MessageUnknownItem);

		} else {
			p.sendMessage(Constants.MessageYouDontHaveEnoughMoney);
		}

	}

}
