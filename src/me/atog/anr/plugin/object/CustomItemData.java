package me.atog.anr.plugin.object;

import java.util.List;

import me.atog.anr.plugin.types.ItemAbilityAction;
import me.atog.anr.plugin.types.ItemAbility;
import me.atog.anr.plugin.types.Rarity;
import me.atog.anr.plugin.types.SlayerType;

import org.bukkit.inventory.ItemStack;

public class CustomItemData {
	private boolean hasRarity = false;
	private Rarity rarity;
	private ItemAbility ability;
	private ItemAbilityAction action;
	private boolean hasAbility = false, hasDamage = false, hasStrength = false, hasSlayerRequeriment = false, hasSpeed = false;
	String damage;
	private String strength, speed, name;
	private Slayer slayer;
	
	public CustomItemData(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		this.name = Util.stripColor(item.getItemMeta().getDisplayName());
		for(int i=0;i<lore.size();i++) {
			String line = Util.stripColor(lore.get(i));
			try {
				if(line.startsWith("Damage")) {
					this.damage = line.split(":")[1].substring(2);
					this.hasDamage = true;
				}else if(line.startsWith("Strength")) {
					this.strength = line.split(":")[1].substring(2);
					this.hasStrength = true;
				}else if(line.startsWith("Speed")) {
					this.strength = line.split(":")[1].substring(2);
					this.hasSpeed = true;
				}else if(line.startsWith("Item Ability")) {
					String[] preparsed = line.substring(14).split(" ");
					this.ability = ItemAbility.valueOf(preparsed[0]);
					this.action = ItemAbilityAction.valueOf(preparsed[1]+"_"+preparsed[2]);
					this.hasAbility = true;
				}else if(line.startsWith("Requires")) { // Requires WOLF SLAYER 3 (example)
					String[] preparsed = line.substring(9).split(" ");
					SlayerType type = SlayerType.valueOf(preparsed[0]);
					String level = preparsed[2];
					Slayer slayer = new Slayer(type, level);
					this.hasSlayerRequeriment = true;
					this.slayer = slayer;
				}
				else if(Util.isRarity(line)) {
					this.rarity = Rarity.valueOf(line.replace(" ", "_"));
					this.hasRarity = true;
				}
			}catch(IllegalArgumentException | NullPointerException e) {System.out.println("Error: "+e.getMessage()+" | Cause: "+e.getCause());}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public ItemAbility getAbility() {
		return ability;
	}
	
	public ItemAbilityAction getAbilityAction() {
		return action;
	}
	public int getDamage() {
		return Integer.parseInt(damage);
	}
	public int getStrength() {
		return Integer.parseInt(strength);
	}
	public int getSpeed() {
		return Integer.parseInt(speed);
	}
	public Rarity getRarity() {
		return rarity;
	}
	public Slayer getSlayer() {
		return slayer;
	}
	
	public boolean hasAbility() {
		return hasAbility;
	}
	public boolean hasDamage() {
		return hasDamage;
	}
	public boolean hasStrength() {
		return hasStrength;
	}
	public boolean hasSpeed() {
		return hasSpeed;
	}
	public boolean hasSlayerRequeriment() {
		return hasSlayerRequeriment;
	}
	public boolean hasRarity() {
		return hasRarity;
	}
}
