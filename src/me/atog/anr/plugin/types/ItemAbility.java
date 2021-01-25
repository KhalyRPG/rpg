package me.atog.anr.plugin.types;

import me.atog.anr.plugin.RPGMain;
import org.bukkit.configuration.file.FileConfiguration;

public enum ItemAbility {
	FIRESHOT, DEEPER, DESZURDIZADOR;
	
	public String getDescription(/*ItemAbility a*/) {
		FileConfiguration file = RPGMain.getInstance().getConfig();
		String description = null;
		for(String v : file.getConfigurationSection("Ability").getKeys(false)) {
			if(v.equalsIgnoreCase(this.name())) {
				description = file.getString("Ability."+v+".Description");
			}
		}
		if(description == null)return "Habilidad que hace algo!";
		return description;
	}

	public int getCost(/*ItemAbility ability*/) {
		FileConfiguration file = RPGMain.getInstance().getConfig();
		int cost = 0;
		for(String v : file.getConfigurationSection("Ability").getKeys(false)) {
			if(v.equalsIgnoreCase(this.name())) {
				cost = file.getInt("Ability."+v+".Mana-Cost");
			}
		}
		return cost;
	}
	public String getName() {
		FileConfiguration file = RPGMain.getInstance().getConfig();
		String name = null;
		for(String v : file.getConfigurationSection("Ability").getKeys(false)) {
			if(v.equalsIgnoreCase(this.name())) {
				name = file.getString("Ability."+v+".Display-Name");
			}
		}
		if(name == null)return "Amazing name!";
		return name;
	}
}
