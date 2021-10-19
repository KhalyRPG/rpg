package me.khaly.core.misc;

import org.bukkit.event.Event;

public class PassiveHandle<T extends Event> {
	private double damage;
	private boolean cancelled;
	private Event event;
		
	public PassiveHandle(double damage, Event event) {
		this.damage = damage;
		this.event = (Event) event;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	@SuppressWarnings("unchecked")
	public T getEvent() {
		return (T) event;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}