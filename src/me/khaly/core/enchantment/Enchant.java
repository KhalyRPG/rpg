package me.khaly.core.enchantment;

import me.khaly.core.KhalyCore;
import me.khaly.core.libraries.Cache;

public class Enchant {
	private String enchantment;
	private int level;
	
	public Enchant(String id, int level) {
		this.enchantment = id;
		this.level = level;
	}
	
	public ItemEnchantment getEnchantment() {
		Cache cache = KhalyCore.getInstance().getServices().getCache();
		return cache.getEnchantments().get(enchantment);
	}
	
	public String getEnchantmentID() {
		return enchantment;
	}
	
	public int getLevel() {
		return level;
	}
}
