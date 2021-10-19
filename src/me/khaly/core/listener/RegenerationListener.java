package me.khaly.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserRegainManaEvent;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.user.User;
import me.khaly.core.util.MathUtils;

public class RegenerationListener implements Listener {
	
	private KhalyCore core;
	
	public RegenerationListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler
	public void onRegen(UserRegainManaEvent event) {
		Player player = event.getPlayer();
		User user = core.getUser(player);
		double amplifier = user.getAttribute(StatAttribute.MANA_REGEN).getValue();
		double result = MathUtils.increaseByPercentage(event.getAmount(), amplifier);
		
		event.setAmount(result);
	}
	
	@EventHandler
	public void regenHealthEvent(EntityRegainHealthEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		User user = core.getUser(player);
		double amplifier = user.getAttribute(StatAttribute.HEALTH_REGEN).getValue();
		double modified = MathUtils.increaseByPercentage(event.getAmount(), amplifier);
		
		event.setAmount(modified);
	}
	
}
