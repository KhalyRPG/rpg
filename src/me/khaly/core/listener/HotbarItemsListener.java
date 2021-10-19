package me.khaly.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserJoinEvent;
import me.khaly.core.enums.ClickAction;
import me.khaly.core.util.ItemUtils;

public class HotbarItemsListener implements Listener {
	
	private KhalyCore core;
	
	public HotbarItemsListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler
	public void manageItem(UserJoinEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		ItemStack item = inventory.getItem(8);
		if(item == null || item.getAmount() > 1 || !ItemUtils.isMenuItem(item)) {
			inventory.setItem(8, core.getServices().getMenuItemStack());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(ItemUtils.isMenuItem(event.getItem()) && ClickAction.RIGHT.isValidAction(event.getAction())) {
			event.setCancelled(true);
			core.getServices().getGUI("menu").open(event.getPlayer());
		}
	}
	
	
}
