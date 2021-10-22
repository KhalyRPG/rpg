package me.khaly.core.gui;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserJoinEvent;
import me.khaly.core.builder.ItemBuilder;
import me.khaly.core.enums.Rarity;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.gui.object.Gui;
import me.khaly.core.user.User;
import me.khaly.core.user.mana.Mana;
import me.khaly.core.user.profile.Profile;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

public class ProfileGUI extends Gui {

	private KhalyCore core;
	private ItemStack unlock;
	
	public ProfileGUI(KhalyCore core) {
		super("profile");
		this.core = core;
		this.unlock = new ItemBuilder(Material.STAINED_GLASS_PANE, Rarity.FABLED.getColorBlockId()).setName("§cCreate").build();
	}

	public Inventory get(Player player) {
		User user = core.getUser(player);
		GUIBuilder builder = GUIBuilder.builder("Profile Selection", 3);
		
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).setName("&7").build();
		
		builder.fillItem(ItemClickable.builderCancellingEvent().setItemStack(glass).build(), 0, 27);
		
		int currentIndex = 10;
		for (Map.Entry<String, Profile> entry : user.getProfiles().entrySet()) {
			Profile profile = entry.getValue();

			ItemStack item = new ItemBuilder(profile.getRPGClass().getIcon()) {
				{
					setName("&b" + profile.getName());
					setLore(Arrays.asList("", " &7Clase: &d" + profile.getRPGClass().getName(),
							" &7Nivel: &d" + profile.getLevel(),
							"", "§aClic para seleccionar"));
					addFlags(ItemFlag.values());
				}
			}.build();
			builder.addItem(ItemClickable.builder()
					.setItemStack(item)
					.setAction(event -> {
						if(!user.hasProfile(profile.getName())) {
							return false;
						}
						player.closeInventory();
						user.setProfile(profile.getName());
						user.loadProfile();
						
//						UserAttributes attributes = user.getAttributes();
						Mana manaSystem = user.getManaSystem();
						UserJoinEvent customEvent = new UserJoinEvent(core.getUser(player));
						
						manaSystem.setMana(manaSystem.getMaxMana());
						player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((int)user.getAttribute(StatAttribute.HEALTH).getValue());
						player.setHealth((int)user.getAttribute(StatAttribute.HEALTH).getValue());
						
						Bukkit.getPluginManager().callEvent(customEvent);
						event.setCancelled(true);
						return true;
					})
					.build(), currentIndex);
			if(currentIndex >= 17)break;
			currentIndex++;
		}
		
		if(currentIndex <= 17) {
			for(int i = currentIndex; i < 17; i++) {
				builder.addItem(ItemClickable.builder(i)
						.setItemStack(unlock)
						.setAction(event -> {
							Gui selection = core.getServices().getGUI("profile_selection");
							selection.open(player);
							return false;
						})
						.build());
			}
		}
				
		builder.closeAction(event -> {
			Bukkit.getScheduler().runTaskLater(core, new Runnable() {
				public void run() {
					if(!player.isOnline() || player == null || user.hasProfile()) {
						return;
					}
					if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null
							&& player.getOpenInventory().getTopInventory().getTitle().equals("Selecciona una clase")) {
						return;
					}
					ProfileGUI.this.open(player);
				}
			}, 20);
		});

		return builder.build();
	}

}
