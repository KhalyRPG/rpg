package me.khaly.core.rpg.classes.object;

import java.util.function.BiPredicate;

import org.bukkit.event.Event;

import me.khaly.core.user.User;

public abstract class RPGClassAbility {
	
	private String id;
	private String displayName;
	private BiPredicate<User, Event> predicate;
	private Class<? extends Event> eventClass;
	private BiPredicate<User, Event> action;
	
	public RPGClassAbility(String id, String displayName, BiPredicate<User, Event> predicate, Class<? extends Event> eventClass) {
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
	
	protected void setAction(BiPredicate<User, Event> action) {
		this.action = action;
	}
	
}
