package me.khaly.core.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.attributes.Damage.DamageType;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.user.User;
import me.khaly.core.util.ItemUtils.MaterialDamage;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class UserUtils {
	
	private UserUtils() {
		
	}
	/*
	public static double getEffectiveHealth(User player) {
		UserAttributes attributes = player.getAttributes();
		return attributes.getMaxHealth() * ((attributes.getDefense() + 100) / 100);
	}
	
	public static double getEffectiveHealth(KhalyCore core, Player player) {
		return UserUtils.getEffectiveHealth(core.getUser(player));
	}
	*/
	
	public static double reduceDamageByDefense(User user, double damage) {
		double defense = user.getAttribute(StatAttribute.DEFENSE).getValue();
		
		return damage * (1 - (-1.0 * Math.pow(1.01, -1.0 * (defense/* + (attr.getCursedEnergy() / 3)*/)) + 1));
	}
	
	public static double reduceDamageByDefense(KhalyCore core, Player player, double damage) {
		return reduceDamageByDefense(core.getUser(player), damage);
	}
	
	public static double calculateDamage(User player, ItemStack hand, DamageType type) {
		double strength = player.getAttribute(StatAttribute.STRENGTH).getValue();
		double energy = player.getAttribute(StatAttribute.CURSED_ENERGY).getValue();
		double damage = 1;
		ItemStack item = hand;
		if(ItemUtils.isCustomItem(item)) {
			NBTTagCompound tag = ItemUtils.getKhalyCompound(item);
			damage = tag.getDouble("damage");
		} else {
			if(item != null) {
				String typeName = item.getType().name();
				if(containsMaterial(typeName)) {
					damage = MaterialDamage.valueOf(typeName).getDamage();
				}
			}
		}
		
		double result = (1 + damage) * (1 + (strength / 50)) + (1 + (energy / 800));
		
		
		
		if(type.getAttribute() != null) {
			double percentage = player.getAttribute(type.getAttribute()).getValue();
			result = MathUtils.increaseByPercentage(result, percentage);
		}
		
		if(result <= 0) return 1;
		return result;
	}
	
	public static double calculateDamage(User player, ItemStack hand) {
		return calculateDamage(player, hand, DamageType.NONE);
	}
	
	private static boolean containsMaterial(String name) {
		boolean has = false;
		for(MaterialDamage materials : MaterialDamage.values()) {
			if(materials.name().equalsIgnoreCase(name)) {
				has = true;
				break;
			}
		}
		return has;
	}
	
	public static double calculateDamage(User player) {
		return calculateDamage(player, player.getBukkitPlayer().getInventory().getItemInMainHand());
	}
	
	public static double calculateDamage(KhalyCore core, Player player, ItemStack hand) {
		return calculateDamage(core.getUser(player), hand);
	}
	
	public static double calculateDamage(KhalyCore core, Player player) {
		return calculateDamage(core.getUser(player), player.getInventory().getItemInMainHand());
	}
}