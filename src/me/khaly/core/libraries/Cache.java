package me.khaly.core.libraries;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.khaly.core.enchantment.ItemEnchantment;
import me.khaly.core.gui.object.Gui;
import me.khaly.core.user.User;

public class Cache {
	private Map<UUID, User> users;
	private Map<String, Gui> guis;
	private Map<String, ItemEnchantment> enchantments;
	
	public Cache() {
		this.users = new HashMap<>();
		this.guis = new HashMap<>();
		this.enchantments = new HashMap<>();
	}
	
	public Map<UUID, User> getUsers() {
		return users;
	}
	
	public Map<String, Gui> getGuis() {
		return guis;
	}
	
	public Map<String, ItemEnchantment> getEnchantments() {
		return this.enchantments;
	}
	
}
