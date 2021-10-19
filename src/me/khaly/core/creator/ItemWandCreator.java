package me.khaly.core.creator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.creator.object.Damageable;
import me.khaly.core.creator.object.Damn;
import me.khaly.core.creator.object.Intelligenceable;
import me.khaly.core.creator.object.Luckable;
import me.khaly.core.creator.object.MeleeDamage;
import me.khaly.core.creator.object.RangedDamage;
import me.khaly.core.creator.object.RegenerableMana;
import me.khaly.core.creator.object.Strengthable;
import me.khaly.core.creator.object.UnchangeableType;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.StatAttribute;

@SuppressWarnings("unchecked")
public class ItemWandCreator extends ItemCreator implements UnchangeableType, Damageable, Intelligenceable, Luckable, RegenerableMana, Strengthable, Damn, MeleeDamage, RangedDamage {

	public ItemWandCreator(ItemStack item, String displayName) {
		super(item, displayName);
		configureType(ItemType.WAND);
		this.setKeepEnchants(true);
	}
	
	public ItemWandCreator(Material material, String displayName) {
		this(new ItemStack(material), displayName);
	}
	
	@Override
	public ItemWandCreator setDamage(double damage) {
		customTag.setDouble("damage", damage);
		return this;
	}


	@Override
	public ItemWandCreator setStrength(double strength) {
		customTag.setDouble(StatAttribute.STRENGTH.toString(), strength);
		return this;
	}


	@Override
	public ItemWandCreator setManaRegeneration(double manaRegeneration) {
		customTag.setDouble(StatAttribute.MANA_REGEN.toString(), manaRegeneration);
		return this;
	}


	@Override
	public ItemWandCreator setLuck(double luck) {
		customTag.setDouble(StatAttribute.LUCK.toString(), luck);
		return this;
	}


	@Override
	public ItemWandCreator setIntelligence(double intelligence) {
		customTag.setDouble(StatAttribute.INTELLIGENCE.toString(), intelligence);
		return this;
	}
	
	@Override
	public ItemWandCreator setCursedEnergy(double energy) {
		customTag.setDouble(StatAttribute.CURSED_ENERGY.toString(), energy);
		return this;
	}

	@Override
	public ItemWandCreator setRangedDamage(double value) {
		customTag.setDouble(StatAttribute.RANGED_DAMAGE.toString(), value);
		return this;
	}

	@Override
	public ItemWandCreator setMeleeDamage(double value) {
		customTag.setDouble(StatAttribute.MELEE_DAMAGE.toString(), value);
		return this;
	}
	
}
