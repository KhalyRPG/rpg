package me.khaly.core.listener;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.enchantment.Enchant;
import me.khaly.core.enchantment.EnchantmentFunction;
import me.khaly.core.enchantment.ItemEnchantment;
import me.khaly.core.enums.ItemType;
import me.khaly.core.user.User;
import me.khaly.core.util.ItemUtils;

public class EnchantmentsListener implements Listener {
	
	private KhalyCore core;
	
	public EnchantmentsListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageByEntityEvent event) {
		if(!checkEvent(event.getDamager(), CheckType.HAND)) {
			return;
		}
		
		Player player = (Player) event.getDamager();
		User user = core.getUser(player);
		ItemStack hand = player.getInventory().getItemInMainHand();
		
		castEnchantments(user, hand, event, EntityDamageByEntityEvent.class, ItemType.WEAPON, ItemType.WAND);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onReceiveDamage(EntityDamageByEntityEvent event) {
		if(!checkEvent(event.getEntity(), CheckType.ARMOR)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		User user = core.getUser(player);
		
		for(ItemStack slot : player.getInventory().getArmorContents()) {
			if(slot == null || !ItemUtils.hasEnchantments(slot)) {
				continue;
			}
			castEnchantments(user, slot, event, EntityDamageByEntityEvent.class, ItemType.ARMOR);
		}
	}
	
	private void castEnchantments(User user, ItemStack item, Event event, Class<? extends Event> eventClass, ItemType...types) {
		List<Enchant> enchantments = ItemUtils.getEnchantments(item);
		
		for(Enchant enchant : enchantments) {
			ItemEnchantment enchantment = enchant.getEnchantment();
			// enchantment.getAllowedTypes().stream().toArray(ItemType[]::new)
			if(enchantment == null) {
				continue;
			}
			
			List<ItemType> allowedTypes = enchantment.getAllowedTypes();
			if(!isAllowed(enchantment.getAllowedTypes(), allowedTypes.toArray(new ItemType[allowedTypes.size()]))
					|| enchantment.getCastType().get() != eventClass) {
				continue;
			}
			
			EnchantmentFunction function = new EnchantmentFunction(enchant.getLevel(), event);
			enchantment.getFunction().accept(user, function);
			
			/*if(event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent functionEvent = (EntityDamageByEntityEvent) function.getEvent();
				result = new Object[] {
						functionEvent.isCancelled(),
						functionEvent.getDamage()
				};
			}*/
		}
	}
	
	private boolean checkEvent(Entity entity, CheckType type) {
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(player.hasMetadata("npc")) {
				return false;
			}
			switch(type) {
			case HAND:
				return ItemUtils.hasEnchantments(player.getInventory().getItemInMainHand());
			case ARMOR:
				boolean hasEnchants = false;
				
				for(ItemStack armorSlot : player.getInventory().getArmorContents()) {
					if(ItemUtils.hasEnchantments(armorSlot)) {
						hasEnchants = true;
						break;
					}
				}
				return hasEnchants;
			}
		}
		return false;
	}
	
	private boolean isAllowed(List<ItemType> list, ItemType...types) {
		boolean allow = false;
		for(ItemType type : types) {
			if(list.contains(type)) {
				allow = true;
				break;
			}
		}
		
		return allow;
	}
	
	private enum CheckType {
		HAND, ARMOR;
	}
	
}
