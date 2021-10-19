package me.khaly.core.listener;

import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.khaly.core.KhalyCore;
import me.khaly.core.user.User;

public class PlayerQuitListener implements Listener {
	
	private KhalyCore core;
	
	public PlayerQuitListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void handleQuit(PlayerQuitEvent e) {
		UUID playerUUID = e.getPlayer().getUniqueId();
		Map<UUID, User> cache = core.getServices().getCache().getUsers();
		User user = cache.get(playerUUID);
		
		if(user.hasProfile()) {
			user.getProfile().save();
		}
		
		cache.remove(playerUUID);
	}
	
}
