package me.khaly.core.gui.object;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class Gui {
	
	private String id;
	
	public Gui(String id) {
		this.id = id;
	}
	
	public abstract Inventory get(Player player);
	
	public void open(Player player) {
		player.openInventory(get(player));
	}
	
	public String getID() {
		return id;
	}
}
