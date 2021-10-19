package me.khaly.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserJoinEvent;
import me.khaly.core.user.User;

public class PlayerJoinListener implements Listener {
	
	private KhalyCore core;
	
	public PlayerJoinListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void manageUser(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		User user = User.createUser(player);
		player.getInventory().clear();
		core.getServices().getCache().getUsers().put(player.getUniqueId(), user);
		player.setHealthScale(2);
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
		Bukkit.getScheduler().runTaskLater(core, () -> {
			core.getServices().getGUI("profile").open(player);
		}, 20);
		
	}
	
	@EventHandler
	public void joinMessage(UserJoinEvent event) {
		Player player = event.getPlayer();
		StringBuilder string = new StringBuilder("§7[§a+§7] ");
		String prefix = PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
		if(!prefix.isEmpty()) {
			string.append(prefix + " ");
		}
		string.append(player.getName());
		Bukkit.broadcastMessage(string.toString());
	}
	
}
