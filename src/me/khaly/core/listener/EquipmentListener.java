package me.khaly.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.ArmorEquipEvent;
import me.khaly.core.api.events.ArmorEquipEvent.EquipMethod;
import me.khaly.core.enums.ArmorType;
import me.khaly.core.enums.ClickAction;
import me.khaly.core.enums.ItemType;
import me.khaly.core.util.ItemUtils;
import me.khaly.core.util.TextUtil;

public class EquipmentListener implements Listener {
	
	private KhalyCore core;
	
	public EquipmentListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void equipSkull(PlayerInteractEvent event) {
		if(ClickAction.RIGHT.isValidAction(event.getAction())) {
			Player player = event.getPlayer();
			if(event.getItem() == null || event.getItem().getType() == Material.AIR)return;
			ItemStack item = event.getItem();
			if(!player.getInventory().getItemInMainHand().isSimilar(item))return;
			if(!ItemUtils.isCustomItem(item))return;
			ItemStack helmet = player.getInventory().getHelmet();
			
			if(helmet == null && ItemUtils.isType(item, ItemType.ARMOR) && (item.getType() == Material.SKULL_ITEM || item.getType() == Material.SKULL)) {
				ArmorEquipEvent call = new ArmorEquipEvent(player, EquipMethod.HOTBAR, ArmorType.HELMET, helmet, item);
				Bukkit.getPluginManager().callEvent(call);
				if(call.isCancelled())return;
				player.getInventory().setHelmet(item);
				player.getInventory().setItemInMainHand(null);
			}
		}
	}
	
	@EventHandler
	public void onEquip(ArmorEquipEvent event) {
		if(event.getNewArmorPiece() != null && !ItemUtils.canUseItem(core, event.getPlayer(), event.getNewArmorPiece())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(TextUtil.color("&8[&c✖&8] &cNo cumples con los requisitos para usar este item."));
		}
	}
	
}
