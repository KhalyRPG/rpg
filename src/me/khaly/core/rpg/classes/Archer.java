package me.khaly.core.rpg.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.rpg.classes.object.RPGClass;

public class Archer extends RPGClass {
	public Archer() {
		super("archer", "Arquero", new ItemStack(Material.BOW), 50);
		setHealth(0, 3.875);
		setIntelligence(0, 0);
		setStrength(0, 1.875);
		setRangedDamage(2.3, 1.875);
		setMeleeDamage(-63.8, 0);

		setDescription(32,
				"El arquero ayuda a su equipo con"
				+ " provenientes de la naturaleza para protegerlos. "
				+ "Son una parte táctica de cualquier grupo, con "
				+ "sus ataques de larga distancia. "
				+ "Los arqueros pueden equipar "
				+ "arcos o ballestas.");
		/*
		description.add("El arquero ayuda a su equipo");
		description.add("con provenientes de la naturaleza");
		description.add("para protegerlos. Son una parte");
		description.add("táctica de cualquier grupo, con");
		description.add("sus ataques de larga distancia. ");
		description.add("Los arqueros pueden equipar arcos");
		description.add("o ballestas.");
		*/
	
		setColor(ChatColor.DARK_AQUA);
		setManaRegen(4);
		setShortText("A distancia");
	}
}
