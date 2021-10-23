package me.khaly.core.rpg.classes.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiPredicate;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.khaly.core.user.User;

public abstract class RPGClassAbility {
	
	private String id;
	private String displayName;
	private BiPredicate<User, Event> predicate;
	private Class<? extends Event> eventClass;
	private BiPredicate<User, Event> action;
	private Map<UUID, Long> cooldowns;
	
	public RPGClassAbility(String id, String displayName, BiPredicate<User, Event> predicate, Class<? extends Event> eventClass) {
		this.cooldowns = new HashMap<>();
		this.id = id;
		this.displayName = displayName;
		this.predicate = predicate;
		this.eventClass = eventClass;
		this.action = (user, event) -> {
			return true;
		};
	}
	
	public String getId() {
		return id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public BiPredicate<User, Event> getPredicate() {
		return predicate;
	}
	
	public Class<? extends Event> getEventClass() {
		return eventClass;
	}
	
	public BiPredicate<User, Event> getAction() {
		return action;
	}
	
	public Map<UUID, Long> getCooldowns() {
		return Collections.unmodifiableMap(cooldowns);
	}
	
	public boolean isOnCooldown(UUID uuid) {
		return (cooldowns.containsKey(uuid) && cooldowns.get(uuid) > System.currentTimeMillis());
	}
	
	protected Map<UUID, Long> getModifiableCooldowns() {
		return cooldowns;
	}
	
	protected void setAction(BiPredicate<User, Event> action) {
		this.action = action;
	}
	
	protected void addCooldown(UUID uuid, double seconds) {
		this.cooldowns.put(uuid, (long)(System.currentTimeMillis() + (seconds * 1000)));
	}
	
	protected void addCooldown(Player player, double seconds) {
		this.addCooldown(player.getUniqueId(), seconds);
	}
	
	protected void addCooldown(User user, double seconds) {
		this.addCooldown(user.getBukkitPlayer(), seconds);
	}
	
}
