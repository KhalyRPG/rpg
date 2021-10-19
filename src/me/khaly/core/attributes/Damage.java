package me.khaly.core.attributes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import me.khaly.core.KhalyCore;
import me.khaly.core.attributes.object.Attribute;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.user.User;
import me.khaly.core.util.ItemUtils;
import me.khaly.core.util.TextUtil;
import me.khaly.core.util.UserUtils;

public class Damage implements Attribute {
	
	private KhalyCore main;
	
	public Damage(KhalyCore main) {
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEvent(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getDamager();
		User user = main.getUser(player);
		
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item != null && item.getType() != Material.AIR && user != null) {
			if(!ItemUtils.canUseItem(main, player, item)) {
				player.sendMessage(TextUtil.color("&8[&c✖&8] &cNo cumples con los requisitos para usar este item."));
				event.setCancelled(true);
				return;
			}
		}
		
		if(user == null || ItemUtils.isType(item, ItemType.BOW)) {
			return;
		}
				
		double damage = UserUtils.calculateDamage(user, item, DamageType.MELEE);
		event.setDamage(damage);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onArrowHit(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player || event.getCause() != DamageCause.PROJECTILE || !(event.getDamager() instanceof Arrow)) {
			return;
		}
		Arrow damager = (Arrow)event.getDamager();
		if(!damager.hasMetadata("bow-damage")) {
			return;
		}
		double damage = damager.getMetadata("bow-damage").get(0).asDouble();
		event.setDamage(damage);
		spawnHologram(damage, event.getEntity().getLocation());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void damageHologram(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getDamager();
		if(player.hasMetadata("npc")) {
			return;
		}
		double damage = event.getDamage();
		
		spawnHologram(damage, event.getEntity().getLocation());
	}
	
	private void spawnHologram(double damage, Location entityLocation) {
		String[] colorDigits = new String[] {
				"f", "e", "6", "c", "c", "f", "c", "c", "c", "e", "c", "c", "c" 
		};
		StringBuilder colorizedDamage = new StringBuilder();
		char[] chars = String.valueOf((int)damage).toCharArray();
		
		for(int i = 0; i < chars.length; i++) {
			String digit = colorDigits[i];
			colorizedDamage.append(TextUtil.color("&" + digit + chars[i]));
		}
		
		double min = -0.9;
		double max = 0.9;
		double x = (Math.random() * (max - min) + min);
		double z = (Math.random() * (max - min) + min);
		Location location = entityLocation.clone().add(x, 1.7, z);
		Hologram holo = HologramsAPI.createHologram(main, location);
		holo.appendTextLine("✦ " + colorizedDamage.toString());
		
		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			
			@Override
			public void run() {
				holo.delete();
			}
			
		}, 18);
	}
	
	public enum DamageType {
		MELEE(StatAttribute.MELEE_DAMAGE),
		RANGED(StatAttribute.RANGED_DAMAGE),
		NONE(null);
		private StatAttribute attribute;
		
		DamageType(StatAttribute attribute) {
			this.attribute = attribute;
		}
		
		public StatAttribute getAttribute() {
			return attribute;
		}
	}
	
}
