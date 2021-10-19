package me.khaly.core.task;

import org.bukkit.Bukkit;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserRegainManaEvent;
import me.khaly.core.rpg.classes.object.RPGClass;
import me.khaly.core.task.object.Task;
import me.khaly.core.user.User;
import me.khaly.core.user.mana.Mana;

public class ManaRegenerationTask extends Task {
	
	private KhalyCore core;
	
	public ManaRegenerationTask(KhalyCore core) {
		super(25, 37);
		this.core = core;
	}

	@Override
	public void run() {
		for(User user : core.getServices().getCache().getUsers().values()) {
			if(!user.getBukkitPlayer().isOnline())return;
			//UserAttributes attr = user.getAttributes();
			Mana manaSystem = user.getManaSystem();
			double defaultRegen = 5;
			
			if(manaSystem.getMana() >= manaSystem.getMaxMana() || !user.hasProfile()) {
				return;
			}
			RPGClass clazz = user.getProfile().getRPGClass();
			
			UserRegainManaEvent event = new UserRegainManaEvent(user.getBukkitPlayer(), defaultRegen + clazz.getManaRegen());
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return;
			}
			manaSystem.addMana(event.getAmount());
		}
	}

}
