package me.khaly.core.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.khaly.core.enums.ExperienceCause;
import me.khaly.core.user.User;

public class UserReceiveExperienceEvent extends Event {
	
	private static HandlerList handlers = new HandlerList();
	private User user;
	private double exp;
	private ExperienceCause cause;
	
	public UserReceiveExperienceEvent(User user, double exp, ExperienceCause cause) {
		this.user = user;
		this.exp = exp;
		this.cause = cause;
	}
	
	public User getUser() {
		return user;
	}
	
	public double getAmount() {
		return exp;
	}
	
	public ExperienceCause getCause() {
		return cause;
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
}
