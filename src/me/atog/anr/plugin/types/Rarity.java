package me.atog.anr.plugin.types;

import me.atog.anr.plugin.RPGMain;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Rarity {
	COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHIC, SPECIAL, VERY_SPECIAL;
	
	public ChatColor getRarityColor() {
		ChatColor color = null;
		FileConfiguration config = RPGMain.getInstance().getConfig();
		for(String v : config.getConfigurationSection("Items.Rarity-Color").getKeys(false)) {
			if(v.equalsIgnoreCase(this.name())) {
				color = ChatColor.valueOf(config.getString("Items.Rarity-Color."+v));
			}
		}
		if(color == null)return ChatColor.DARK_GRAY;
		return color;
	}
	
	public String getColoredName() {
		return getRarityColor()+this.name();
	}
}
