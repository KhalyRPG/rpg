package me.khaly.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.enums.ClickAction;
import me.khaly.core.enums.ItemCooldown;
import me.khaly.core.enums.ItemType;
import me.khaly.core.util.ItemUtils;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class HitCooldownListener implements Listener {
	
	private KhalyCore core;
	
	public HitCooldownListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler
	public void manageCooldowns(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		if(!event.hasItem() || !ClickAction.LEFT.isValidAction(action))return;
		ItemStack item = event.getItem();
		if(!isType(item, ItemType.WEAPON, ItemType.WAND) || !ItemUtils.canUseItem(core, player, item))return;
		NBTTagCompound comp = ItemUtils.getKhalyCompound(item);
		if(comp.hasKey("cooldown")) {
			int cooldown = (int) ItemCooldown.valueOf(comp.getString("cooldown")).getTime();
			
			player.setCooldown(item.getType(), cooldown * 20);
		}
		
	}
	
	private boolean isType(ItemStack item, ItemType... blacklist) {
		boolean trusted = false;
		for(ItemType type : blacklist) {
			if(ItemUtils.isType(item, type))
				trusted = true;
		}
		return trusted;
	}
	
}
