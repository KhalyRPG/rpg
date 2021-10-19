package me.khaly.core.enums;

import org.bukkit.Material;

public enum LeatherArmor {
	HELMET(Material.LEATHER_HELMET), 
	CHESTPLATE(Material.LEATHER_CHESTPLATE), 
	LEGGINGS(Material.LEATHER_LEGGINGS),
	BOOTS(Material.LEATHER_BOOTS);

	private Material mat;
	private String name;

	LeatherArmor(Material mat) {
		this.mat = mat;
		this.name = this.name().toLowerCase();
	}

	public String getName() {
		return name;
	}

	public Material getMaterial() {
		return mat /* Material.valueOf("LEATHER_" + this.name()) */;
	}
}
