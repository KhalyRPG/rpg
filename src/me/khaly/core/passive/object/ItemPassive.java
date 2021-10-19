package me.khaly.core.passive.object;

import java.util.function.BiConsumer;

import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.PassiveType;
import me.khaly.core.misc.PassiveHandle;
import me.khaly.core.user.User;

public abstract class ItemPassive {
	private String name;
	private String key;
	private int cooldown;
	private int manaCost;
	private PassiveType passiveType;
	private ItemType type;
	
	public ItemPassive(String key, String name, int cooldown, int manaCost, PassiveType passiveType, ItemType type) {
		this.key = key;
		this.name = name;
		this.cooldown = cooldown;
		this.manaCost = manaCost;
		this.passiveType = passiveType;
		this.type = type;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getDisplayName() {
		return name;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public int getManaCost() {
		return manaCost;
	}
	
	public PassiveType getPassiveType() {
		return passiveType;
	}
	
	public ItemType getTypeCanUse() {
		return type;
	}
	
	public abstract BiConsumer<User, PassiveHandle<?>> onCast();
	
	public void execute(User user, PassiveHandle<?> handle) {
		onCast().accept(user, handle);
	}
	
}