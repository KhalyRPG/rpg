package me.khaly.core.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class UserDeathEvent extends Event {
	private Player p;
	private Entity damager;
	private DamageCause cause;
	
	public UserDeathEvent(Player p, Entity damager, DamageCause cause) {
		this.p = p;
		this.damager = damager;
		this.cause = cause;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public Entity getDamager() {
		return damager;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	private static HandlerList handlers = new HandlerList();
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}