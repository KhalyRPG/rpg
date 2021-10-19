package me.khaly.core.api.events;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.khaly.core.user.User;

public class UserJoinEvent extends PlayerEvent {
	
	private static final HandlerList handlers = new HandlerList();
	
	private User user;
	
	public UserJoinEvent(User user) {
		super(user.getBukkitPlayer());
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	@Override
	public final HandlerList getHandlers(){
		return handlers;
	}
	
}
