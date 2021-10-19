package me.khaly.core.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.khaly.core.KhalyCore;
import me.khaly.core.attributes.object.Attribute;
import me.khaly.core.user.User;
import me.khaly.core.util.UserUtils;

public class Defense implements Attribute {
	
	private KhalyCore main;
	
	public Defense(KhalyCore main) {
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEvent(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		User user = main.getUser(player);
		
		if(user == null) {
			return;
		}
		
		double modification = UserUtils.reduceDamageByDefense(user, event.getFinalDamage());
		
		event.setDamage(modification);
	}
	
}
