package me.khaly.core.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class RemoveArrowsListener implements Listener {
	
	@EventHandler
	public void removeArrows(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			
			if(!arrow.isOnGround()) {
				return;
			}
			
			arrow.remove();
		}
	}
	
}
