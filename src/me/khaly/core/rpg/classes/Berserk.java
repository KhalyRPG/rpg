package me.khaly.core.rpg.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.rpg.classes.object.RPGClass;

public class Berserk extends RPGClass {
	
	public Berserk() {
		super("berserk", "Berserker", new ItemStack(Material.IRON_SWORD), 50);
		setHealth(0, 2.875);
		setIntelligence(0, 3.04);
		setStrength(0, 2.875);
		setMeleeDamage(2.3, 1.875);
		setRangedDamage(-63.8, 0);
		
		description.add("El Berserker es una clase luchadora.");
		description.add("Se caracteriza por su alto ataque");
		description.add("y capacidad de combear rápido.");
		description.add("Principalmente usan hachas y");
		description.add("espadas, aunque también pueden usar");
		description.add("martillos, arcos y armas de fuego.");
		
		setColor(ChatColor.GRAY);
		setManaRegen(4);
		setShortText("Melee");
	}
}
