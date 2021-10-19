package me.khaly.core.util;

import org.bukkit.ChatColor;

public class TextUtil extends Util {
	
	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
}
