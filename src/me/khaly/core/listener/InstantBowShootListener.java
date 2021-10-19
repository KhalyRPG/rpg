package me.khaly.core.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import me.khaly.core.KhalyCore;
import me.khaly.core.attributes.Damage.DamageType;
import me.khaly.core.enums.ClickAction;
import me.khaly.core.enums.ItemType;
import me.khaly.core.util.ItemUtils;
import me.khaly.core.util.UserUtils;

public class InstantBowShootListener implements Listener {
	
	private KhalyCore core;
	
	public InstantBowShootListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onShoot(PlayerInteractEvent event) {
		if(!event.hasItem() || !ClickAction.LEFT.isValidAction(event.getAction()) || !ItemUtils.isType(event.getItem(), ItemType.BOW) || event.getHand() != EquipmentSlot.HAND) {
			return;
		}
		Player player = event.getPlayer();
		ItemStack bow = event.getItem();
		if(!ItemUtils.canUseItem(core, player, bow))return;
		ItemStack hand = player.getInventory().getItemInMainHand();
		Vector direction = player.getLocation().getDirection().multiply(1.9);
		Arrow arrow = (Arrow) player.launchProjectile(Arrow.class, direction);
		arrow.setVelocity(direction);
		arrow.setShooter(player);
		arrow.setPickupStatus(PickupStatus.DISALLOWED);
		double damage = UserUtils.calculateDamage(core.getUser(player), hand, DamageType.RANGED);
		arrow.setMetadata("bow-damage", new FixedMetadataValue(core, damage));
	}
	
}
