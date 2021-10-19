package me.khaly.core.enums;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.khaly.core.api.events.UserShootEntityEvent;

public enum EnchantCastType {
	ON_DAMAGE(EntityDamageByEntityEvent.class),
	ON_SHOT(UserShootEntityEvent.class);
	
	private Class<? extends Event> class1;
	
	EnchantCastType(Class<? extends Event> class1) {
		this.class1 = class1;
	}
	
	public Class<? extends Event> get() {
		return class1;
	}
}
