package me.atog.anr.plugin.listener;

import me.atog.anr.plugin.object.CustomItemData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MakeDamage implements Listener {
	
	@EventHandler
	public void onEvent(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player))return;
		if(e.getEntity() instanceof Player)return;
		Player p = (Player)e.getDamager();
		ItemStack item = p.getInventory().getItemInMainHand();
		CustomItemData i = new CustomItemData(item);
		if(i.hasDamage()) {
			e.setDamage(i.getDamage());
		}
	}
}
