package me.khaly.core.api.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.khaly.core.user.User;

public class UserShootEntityEvent extends Event implements Cancellable {
	
	private boolean cancelled;
	private User user;
	private Arrow arrow;
	private Entity entity;
	private double damage;
	
	public UserShootEntityEvent(User user, Arrow arrow, Entity entity, double damage) {
		this.user = user;
		this.arrow = arrow;
		this.entity = entity;
		this.damage = damage;
		
		this.cancelled = false;
	}
	
	public User getUser() {
		return user;
	}
	
	public Arrow getArrow() {
		return arrow;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double arg0) {
		this.damage = arg0;
	}
	
	private static HandlerList handlers = new HandlerList();
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}
}
