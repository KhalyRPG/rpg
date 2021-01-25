package me.atog.anr.plugin.command;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.atog.anr.plugin.RPGMain;
import me.atog.anr.plugin.object.CustomItemCreator;
import me.atog.anr.plugin.object.CustomItemDataConstructor;
import me.atog.anr.plugin.object.Util;
import me.atog.anr.plugin.types.ItemAbility;
import me.atog.anr.plugin.types.ItemAbilityAction;
import me.atog.anr.plugin.types.Phase;
import me.atog.anr.plugin.types.Rarity;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
@Command(names = {"mmorpg", "atogmmorpg"})
public class MainCommand implements CommandClass, Listener {
    private RPGMain pl;
	public MainCommand(RPGMain pl) {
		this.pl = pl;
	}
	
	@Command(names = "")
	public boolean mainCommand(@Sender CommandSender p) {
		p.sendMessage(Util.color("&cCorrect usage: mmorpg help"));
		return true;
	}
	
	@Command(names = "help")
	public boolean helpSubCommand(@Sender CommandSender p) {
		p.sendMessage("nao nao");
		return true;
	}
	
	@Command(names = "create")
	public boolean customItemCreatorSubCommand(@Sender Player p) {
		pl.getPlayersInEditor().put(p, new CustomItemDataConstructor(Phase.ITEM_NAME));
		p.sendMessage(Util.color("&eBien! ha empezado el procedimiento, responde las preguntas que se te van dando directamente en el chat."));
		p.sendMessage(Util.color("&7Como se llamará tu item?"));
		return true;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void questions(AsyncPlayerChatEvent e) {
		if(!pl.getPlayersInEditor().containsKey(e.getPlayer()))return;
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(e.getMessage().equalsIgnoreCase("cancelar")) {
			pl.getPlayersInEditor().remove(e.getPlayer());
			p.sendMessage(Util.color("&2Se ha cancelado el setup."));
			return;
		}
		
		CustomItemDataConstructor data = pl.getPlayersInEditor().get(p);
		if(data.getPhase() == Phase.ITEM_NAME) {
			data.setName(Util.stripColor(e.getMessage()));
			data.changePhase(Phase.ITEM_DESCRIPTION);
			p.sendMessage(Util.color("&7Cual sera la descripcion de tu item?"));
		}else if(data.getPhase() == Phase.ITEM_DESCRIPTION) {
			data.setDescription(Util.stripColor(e.getMessage()));
			data.changePhase(Phase.ITEM_MATERIAL);
			p.sendMessage(Util.color("&7Que material vas a ponerle a tu item?"));
		}else if(data.getPhase() == Phase.ITEM_MATERIAL) {
			try {
				Material mat = Material.matchMaterial(e.getMessage());
				if(mat == null) {
					data.setMaterial(Material.BEDROCK);
					data.changePhase(Phase.ITEM_DATA);
					p.sendMessage(Util.color("&7Cual es el dato de identificación de el material?"));
					return;
				}
				data.setMaterial(mat);
				data.changePhase(Phase.ITEM_DATA);
				p.sendMessage(Util.color("&7Cual es el dato de identificación de el material?"));
			}catch(IllegalArgumentException ex) {
				p.sendMessage(Util.color("&c"+e.getMessage()+" &7no es válido para su conversión."));
			}
		}else if(data.getPhase() == Phase.ITEM_DATA) {
			if(!isNumber(e.getMessage())) {
				data.setData(0);
				data.changePhase(Phase.ATTRIBUTE_DAMAGE);
				p.sendMessage(Util.color("&7Cual sera el daño que hara tu item?"));
			} else {
				data.setData(Integer.parseInt(e.getMessage()));
				data.changePhase(Phase.ATTRIBUTE_DAMAGE);
				p.sendMessage(Util.color("&7Cual sera el daño que hara tu item?"));
			}
		}
		else if(data.getPhase() == Phase.ATTRIBUTE_DAMAGE) {
			if(!isNumber(e.getMessage())) {
				p.sendMessage(Util.color("&cLo que acabas de introducir no es un número, intentalo de nuevo."));
				return;
			}
			data.setDamage(Integer.parseInt(e.getMessage()));
			data.changePhase(Phase.ATTRIBUTE_STRENGTH);
			p.sendMessage(Util.color("&7Cuanta fuerza dará tu item?"));
		}else if(data.getPhase() == Phase.ATTRIBUTE_STRENGTH) {
			if(!isNumber(e.getMessage())) {
				p.sendMessage(Util.color("&cLo que acabas de introducir no es un número, intentalo de nuevo."));
				return;
			}
			data.setStrength(Integer.parseInt(e.getMessage()));
			data.changePhase(Phase.ATTRIBUTE_SPEED);
			p.sendMessage(Util.color("&7Cual será la velocidad que va a dar tu item?"));
		}else if(data.getPhase() == Phase.ATTRIBUTE_SPEED) {
			if(!isNumber(e.getMessage())) {
				p.sendMessage(Util.color("&cLo que acabas de introducir no es un número, intentalo de nuevo."));
				return;
			}
			data.setSpeed(Integer.parseInt(e.getMessage()));
			data.changePhase(Phase.ENCHANTED);
			p.sendMessage(Util.color("&7Quieres que tu item esté encantado?"));
		}else if(data.getPhase() == Phase.ENCHANTED) {
			if(e.getMessage().equalsIgnoreCase("yes")) {
				data.enchant(true);
				data.changePhase(Phase.ABILITY);
				p.sendMessage(Util.color("&7Cual será la habilidad de tu item?"));
			}else if(e.getMessage().equalsIgnoreCase("no")) {
				data.enchant(false);
				data.changePhase(Phase.ABILITY);
				p.sendMessage(Util.color("&7Cual será la habilidad de tu item?"));
			} else {
				p.sendMessage(Util.color("&cDebes especificar un &c&lsi&r&c o un &c&lno&r&c."));
			}
		}
		else if(data.getPhase() == Phase.ABILITY) {
			if(e.getMessage().equalsIgnoreCase("none")) {
				data.setAbility(null);
				data.changePhase(Phase.RARITY);
				p.sendMessage(Util.color("&cDebido a que no quieres añadir una habilidad se saltará 1 pregunta..."));
				p.sendMessage(Util.color("&7Cual será la rareza del item?"));
			} else {
				try {
					ItemAbility ab = ItemAbility.valueOf(e.getMessage().toUpperCase().replace(" ", "_"));
					data.setAbility(ab);
					data.changePhase(Phase.ACTION_ABILITY);
					p.sendMessage(Util.color("&7Cual será la acción que tendrá su habilidad?"));
				}catch(IllegalArgumentException ex) {
					p.sendMessage(Util.color("&c"+e.getMessage()+" &7no es válido para su conversión."));
				}
			}
		}else if(data.getPhase() == Phase.ACTION_ABILITY) {
			if(data.getAbility() == null) {
				data.setAbilityAction(null);
				data.changePhase(Phase.RARITY);
				p.sendMessage(Util.color("&7Cual será la rareza del item?"));
			} else {
				try {
					ItemAbilityAction ab = ItemAbilityAction.valueOf(e.getMessage().toUpperCase().replace(" ", "_"));
					data.setAbilityAction(ab);
					data.changePhase(Phase.RARITY);
					p.sendMessage(Util.color("&7Cual será la rareza del item?"));
				}catch(IllegalArgumentException ex) {
					p.sendMessage(Util.color("&c"+e.getMessage()+" &7no es válido para su conversión."));
				}
			}
		}else if(data.getPhase() == Phase.RARITY) {
			try {
				Rarity rarity = Rarity.valueOf(e.getMessage().toUpperCase().replace(" ", "_"));
				data.setRarity(rarity);
				p.sendMessage(Util.color("&7Item construido correctamente! se van a procesar sus datos..."));
				pl.getPlayersInEditor().remove(p);
				CustomItemCreator itemBuild = new CustomItemCreator(data.getMaterial(), 1, data.getData(), data.getName(), data.getDescription(), data.getRarity(), data.getAbility(), data.getAction(), data.isEnchanted(), data.getDamage(), data.getStrength(), data.getSpeed());
				p.getInventory().addItem(itemBuild.build());
				p.sendMessage(Util.color("&7Item creado correctamente, disfruta!"));
			}catch(IllegalArgumentException ex) {
				p.sendMessage(Util.color("&c"+e.getMessage()+" &7no es válido para su conversión."));
			}
		}
	}
	
	public boolean isNumber(String x) {
		try {
			Integer.parseInt(x);
			return true;
		}catch(NumberFormatException | NullPointerException e) {
			return false;
		}
	}
}