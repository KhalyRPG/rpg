package me.khaly.core.task;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.khaly.core.KhalyCore;
import me.khaly.core.builder.ActionbarBuilder;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.enums.Symbols;
import me.khaly.core.task.object.Task;
import me.khaly.core.user.User;
import me.khaly.core.user.mana.Mana;
import me.khaly.core.util.TextUtil;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;

public class ActionbarTask extends Task {
	
	private KhalyCore core;
	
	public ActionbarTask(KhalyCore core) {
		super(15, 20);
		this.core = core;
	}

	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			User user = core.getUser(p);
			if(user == null || (user.joinedAt() + 1158) > System.currentTimeMillis()) {
				return;
			}
			
			p.setSaturation(5);
			p.setFoodLevel(20);
			
			if(!user.hasProfile()) {
				return;
			}
			
			int health = (int)user.getAttribute(StatAttribute.HEALTH).getValue();
			Mana manaSystem = user.getManaSystem();
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
			p.setHealthScale(getHealthScale(health));
			if(manaSystem.getMana() > manaSystem.getMaxMana()) {
				manaSystem.setMana(manaSystem.getMaxMana());
			}
			
			if(user.getBukkitPlayer().getHealth() > health) {
				p.setHealth(health);
			}
			
			int defense = (int) user.getAttribute(StatAttribute.DEFENSE).getValue();
			int mana = (int) manaSystem.getMana();
			int maxMana = (int) manaSystem.getMaxMana();
			
			String healthText = (int)(user.getBukkitPlayer().getHealth()) +"/" + health;
			String manaText = mana + "/" + maxMana;
			String defenseText = defense > 0 ? "&a" + Symbols.DEFENSE + defense : "";
			
			ActionbarBuilder builder = new ActionbarBuilder(TextUtil.color("&c" + Symbols.HEALTH + healthText + " HP     " + defenseText + "     &b" + Symbols.MANA + manaText + " MP"));
			builder.send(p);
			((CraftPlayer)user.getBukkitPlayer()).getHandle().getDataWatcher().set(new DataWatcherObject<>(10, DataWatcherRegistry.b), 0);
		}
	}
	
	private int getHealthScale(int health) {
		double calculation = 0.0985;
		int max = 40;
		int min = 10;
		int result = (int)(calculation * health);
		
		if(result > max) {
			return max;
		} else if(result < min) {
			return min;
		}
		
		return result;
	}
	
}
