package me.khaly.core.gui;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserJoinEvent;
import me.khaly.core.builder.ItemBuilder;
import me.khaly.core.gui.object.Gui;
import me.khaly.core.misc.Classes;
import me.khaly.core.rpg.classes.object.RPGClass;
import me.khaly.core.user.User;
import me.khaly.core.util.ProfileUtil;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

public class ProfileSelectionGUI extends Gui {
	
	private KhalyCore core;
	
	public ProfileSelectionGUI(KhalyCore core) {
		super("profile_selection");
		this.core = core;
	}

	@Override
	public Inventory get(Player player) {
		GUIBuilder builder = GUIBuilder.builder("Selecciona una clase", 1);
		User user = core.getUser(player);
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7)
				.setName("&7")
				.build();
		
		builder.addItem(ItemClickable.builderCancellingEvent()
				.setItemStack(glass)
				.build(), 0, 1, 2, 6, 7, 8);
		
		int index = 3;
		
		for(Map.Entry<String, RPGClass> entry : Classes.getClasses().entrySet()) {
			RPGClass value = entry.getValue();
			
			if(entry.getKey().equals("human")) {
				continue;
			}
			
			if(index > 5) {
				break;
			}
			List<String> description = value.getDescription();
			StringBuilder lore = new StringBuilder();
			lore.append("§8" + value.getShortText() + "¢");
			lore.append("¢");
			for(String line : description) {
				lore.append(line + "¢");
			}
			lore.append(" ¢");
			lore.append("§aClic para crear nuevo perfil.");
			ItemStack item = new ItemBuilder(value.buildItem())
					.setLore(lore.toString().split("¢"))
					.build();
			
			builder.addItem(ItemClickable.builder(index)
					.setItemStack(item)
					.setAction(event -> {
						ProfileUtil util = core.getServices().getUtils().getProfileUtil();
						
						util.createProfile(user, value);
						player.closeInventory();
						
						UserJoinEvent customEvent = new UserJoinEvent(user);
						Bukkit.getPluginManager().callEvent(customEvent);
						return false;
					})
					.build());
			index++;
		}
				
		builder.closeAction(event -> {
			Bukkit.getScheduler().runTaskLater(core, new Runnable() {
				public void run() {
					if(!player.isOnline() || player == null || user.hasProfile()) {
						return;
					}
					core.getServices().getGUI("profile").open(player);
				}
			}, 20);
		});
		
		return builder.build();
	}

}
