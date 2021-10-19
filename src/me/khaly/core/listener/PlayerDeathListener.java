package me.khaly.core.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserDeathEvent;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.user.User;
import me.khaly.core.util.TextUtil;

public class PlayerDeathListener implements Listener {

	private KhalyCore main;
	
	public PlayerDeathListener(KhalyCore main) {
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeathAlternative(EntityDamageEvent e) {
		if (((((Damageable) e.getEntity()).getHealth() - e.getFinalDamage()) <= 0) && e.getEntity() instanceof Player && !e.getEntity().hasMetadata("npc")) {
			e.setCancelled(true);
			UserDeathEvent event = new UserDeathEvent((Player)e.getEntity(), null, e.getCause());
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeathAlternativeByEntity(EntityDamageByEntityEvent event) {
		if (((((Damageable) event.getEntity()).getHealth() - event.getFinalDamage()) <= 0) && event.getEntity() instanceof Player && !event.getEntity().hasMetadata("npc")) {
			event.setCancelled(true);
			UserDeathEvent call = new UserDeathEvent((Player)event.getEntity(), event.getDamager(), event.getCause());
			Bukkit.getPluginManager().callEvent(call);
		}
	}
		
	@EventHandler
	public void onCustomDeath(UserDeathEvent event) {
		User user = main.getUser(event.getPlayer());
		Player p = event.getPlayer();
		p.setHealth((int) user.getAttribute(StatAttribute.HEALTH).getValue());
		p.getActivePotionEffects().clear();
		if(p.getFireTicks() > 0)p.setFireTicks(0);
		
		p.getActivePotionEffects().forEach(effect -> {
			p.removePotionEffect(effect.getType());
		});
		
		p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1.5F);
		
		List<Player> players = p.getWorld().getPlayers();
		if(event.getDamager() != null) {
			if(event.getDamager() instanceof Player) {
				players.forEach(player -> player.sendMessage(TextUtil.color("&7"+player.getName() + " ha sido asesinado por "+((Player)event.getDamager()).getName())));
			} else {
				EntityType type = event.getDamager().getType();
				String parsedId = ((type.name().charAt(0)+"").toUpperCase()+type.name().substring(1).toLowerCase()).replace('_', ' ');
				players.forEach(player -> player.sendMessage(TextUtil.color("&7"+player.getName() + " ha sido asesinado por "+(event.getDamager().getCustomName() == null ? parsedId : event.getDamager().getCustomName()))));
			}
		} else {
			StringBuilder cause = new StringBuilder();
			switch(event.getCause()) {
			case BLOCK_EXPLOSION:
				cause.append("ha explotado.");
				break;
			case CUSTOM:
				cause.append("murió de formas desconocidas.");
				break;
			case DROWNING:
				cause.append("murió ahogado.");
				break;
			case FALL:
				cause.append("cayó desde muy alto.");
				break;
			case FIRE:
				cause.append("murió incinerado.");
				break;
			case FIRE_TICK:
				cause.append("murió incinerado.");
				break;
			case FLY_INTO_WALL:
				cause.append("impactó contra una pared.");
				break;
			case LIGHTNING:
				cause.append("ha sido impactado por un rayo.");
				break;
			case POISON:
				cause.append("murió envenenado.");
				break;
			case SUFFOCATION:
				cause.append("murió sofocado.");
				break;
			default:
				cause.append("murió.");
				break;
			}
			players.forEach(player -> player.sendMessage(TextUtil.color("&7"+player.getName()+" "+cause.toString())));
		}
		
		if(main.getConfig().contains("respawnLocations")) {
			ConfigurationSection section = main.getConfig().getConfigurationSection("respawnLocations");
			Location lastLocation = null;
			for(String path : section.getKeys(true)) {
				ConfigurationSection sec = section.getConfigurationSection(path);
				Location loc = new Location(main.getServer().getWorld(sec.getString("world")), sec.getDouble("x"), sec.getDouble("y"), sec.getDouble("z"), sec.getInt("yaw"), sec.getInt("pitch"));
				if(lastLocation == null) {
					lastLocation = loc;
					continue;
				}
				if(p.getLocation().distance(loc) < p.getLocation().distance(lastLocation)) {
					lastLocation = loc;
				}
			}
			if(lastLocation == null)lastLocation = p.getWorld().getSpawnLocation();
			p.teleport(lastLocation);
		} else {
			p.teleport(p.getWorld().getSpawnLocation());
		}
	}
	
}
