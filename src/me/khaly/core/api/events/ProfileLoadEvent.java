package me.khaly.core.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.khaly.core.user.profile.Profile;

public class ProfileLoadEvent extends PlayerEvent {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Profile profile;
	
	public ProfileLoadEvent(Player player, Profile profile) {
		super(player);
		this.profile = profile;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public String getProfileName() {
		return getProfile().getName();
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	@Override
	public final HandlerList getHandlers(){
		return handlers;
	}
	
}
