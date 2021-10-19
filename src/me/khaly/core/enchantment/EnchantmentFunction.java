package me.khaly.core.enchantment;

import org.bukkit.event.Event;

public class EnchantmentFunction {
	private int level;
	private Event event;
	
	public EnchantmentFunction(int level, Event event) {
		this.level = level;
		this.event = event;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Event getEvent() {
		return event;
	}
}
