package me.khaly.core.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.builder.ItemBuilder;
import me.khaly.core.builder.SkullItemBuilder;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.enums.Symbols;
import me.khaly.core.gui.object.Gui;
import me.khaly.core.user.User;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

public class MenuGUI extends Gui {
	
	private KhalyCore core;
	private Map<ItemStack, Integer> items;
	
	public MenuGUI(KhalyCore core) {
		super("menu");
		this.core = core;
		this.items = new HashMap<>();
		
		items.put(new ItemBuilder(Material.GOLD_CHESTPLATE).setName("&dHabilidades").build(), 22);
		items.put(new ItemBuilder(Material.ENDER_CHEST).setName("&7Cofre de ender").build(), 21);
		items.put(new ItemBuilder(Material.BOOK).setName("&bRecetas").build(), 23);
		items.put(new ItemBuilder(Material.BARRIER).setName("&cCerrar").build(), 40);
		items.put(new ItemBuilder(Material.COMMAND_MINECART).setName("&cConfiguración").build(), 41);
	}

	@Override
	public Inventory get(Player player) {
		GUIBuilder builder = GUIBuilder.builder("§bGame Menu", 5);
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7)
				.setName("&7")
				.build();
		
		builder.fillItem(ItemClickable.builderCancellingEvent().setItemStack(glass).build(), 0, 45);
		
		for(Map.Entry<ItemStack, Integer> entry : items.entrySet()) {
			builder.addItem(ItemClickable.builderCancellingEvent()
					.setItemStack(entry.getKey())
					.build(), entry.getValue());
		}
		
		builder.addItem(ItemClickable.builderCancellingEvent()
				.setItemStack(profile(player))
				.build(), 13);
		
		return builder.build();
	}
	
	private ItemStack profile(Player author) {
		List<String> lore = new ArrayList<>();
		User user = core.getUser(author);
		//UserAttributes attr = user.getAttributes();
		//lore.add(" ");
		String space = "  ";
		
		int maxHealth = getAttribute(user, StatAttribute.HEALTH);
		int intelligence = getAttribute(user, StatAttribute.INTELLIGENCE);
		int speed = getAttribute(user, StatAttribute.SPEED);
		int strength = getAttribute(user, StatAttribute.STRENGTH);
		int cursedEnergy = getAttribute(user, StatAttribute.CURSED_ENERGY);
		int defense = getAttribute(user, StatAttribute.DEFENSE);
		int luck = getAttribute(user, StatAttribute.LUCK);
		
		lore.add(space+"§c"+Symbols.HEALTH+" Vida: §f"+maxHealth);
		lore.add(space+"§b"+Symbols.INTELLIGENCE+" Inteligencia: §f"+intelligence);
		lore.add(space+"§f"+Symbols.SPEED+" Velocidad: §f"+speed);
		lore.add(space+"§c"+Symbols.STRENGTH+" Fuerza: §f"+strength);
		if(cursedEnergy > 0) {
			lore.add(space+"§d"+Symbols.CURSED_ENERGY+" Energía maldita: §f"+cursedEnergy);
		}
		lore.add(space+"§a"+Symbols.DEFENSE+" Defensa: §f"+defense);
		lore.add(space+"§a"+Symbols.LUCK+" Suerte: §f"+luck);
//		lore.add(space+"§8"+Symbols.INFINITE+" Resistencia de empuje: §f"+(int)(attr.getKnockbackResistance()));
		return new SkullItemBuilder().setOwner(author.getName()).setName("&b"+author.getName()).setLore(lore).build();
	}
	
	private int getAttribute(User user, StatAttribute stat) {
		return (int) user.getAttribute(stat).getValue();
	}
	
}
