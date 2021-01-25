package me.atog.anr.plugin.object;

import me.atog.anr.plugin.types.Rarity;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public static String stripColor(String text) {
		return ChatColor.stripColor(text);
	}
	
	public static boolean isRarity(String text) {
		boolean x = false;
		for (Rarity r : Rarity.values()) {
			if(r.name().toUpperCase().equalsIgnoreCase(text.replace(" ", "_"))) {x=true;break;}
		}return x;
	}

	public static ItemMeta getEmptyItemMeta() {
		return new ItemStack(Material.BEDROCK, 1).getItemMeta();
	}
	
}
