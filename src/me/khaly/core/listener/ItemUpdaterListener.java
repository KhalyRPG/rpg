package me.khaly.core.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserJoinEvent;

public class ItemUpdaterListener implements Listener {
	
	private KhalyCore core;
	
	public ItemUpdaterListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		ItemStack item = event.getItem().getItemStack();
		ItemStack replaced = core.getServices().getItemUpdater().updateItem((event.getEntity() instanceof Player) ? (Player)event.getEntity() : null, item);
		
		event.getItem().setItemStack(replaced);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event) {
		if(!event.hasItem() || event.getHand() != EquipmentSlot.HAND)return;
		ItemStack replaced = core.getServices().getItemUpdater().updateItem(event.getPlayer(), event.getItem());
		event.getPlayer().getInventory().setItemInMainHand(replaced);
	}
	
	@EventHandler
	public void onJoin(UserJoinEvent event) {
		Player player = event.getPlayer();
		for(int i=0;i<player.getInventory().getSize();i++) {
			ItemStack item = player.getInventory().getItem(i);
			if(item == null || item.getType() == Material.AIR)return;
			ItemStack replaced = core.getServices().getItemUpdater().updateItem(player, item);
			player.getInventory().setItem(i, replaced);
		}
	}
}