package me.khaly.core.rpg.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.rpg.classes.object.RPGClass;

public class Mage extends RPGClass {
	public Mage() {
		super("mage", "Mago", new ItemStack(Material.BLAZE_ROD), 50);
		setHealth(0, 1.875);
		setIntelligence(0, 5.875);
		setStrength(0, 1.875);
		setMeleeDamage(1.1, 0.1);
		setRangedDamage(1.1, 0.1);
		
		description.add("Los magos son seres con capacidades");
		description.add("inhumanas de usar mágia. Su especialidad");
		description.add("es el uso de mágia pesada, debido a esto,");
		description.add("son más débiles cuerpo a cuerpo y con armas");
		description.add("a distancia. Tienen poca defensa para compensar");
		description.add("sus habilidades mágicas pero son más eficientes");
		description.add("usando esa §l§benergía§r.");
				
		setColor(ChatColor.AQUA);
		setManaRegen(5);
		setShortText("Ranged/Melee");
	}
}
