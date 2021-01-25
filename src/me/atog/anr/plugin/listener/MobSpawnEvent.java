package me.atog.anr.plugin.listener;

import me.atog.anr.plugin.object.Util;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnEvent implements Listener {
	
	@EventHandler
	public void onEvent(EntitySpawnEvent e) {
		Entity ent = e.getEntity();
		ent.setCustomName(Util.color("&7[&8LVL 10&7] "+ent.getName()));
		ent.setSilent(true);
	}
	
}
