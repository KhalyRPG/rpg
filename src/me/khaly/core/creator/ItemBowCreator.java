package me.khaly.core.creator;

import org.bukkit.Material;

import me.khaly.core.creator.object.Damageable;
import me.khaly.core.creator.object.Damn;
import me.khaly.core.creator.object.Intelligenceable;
import me.khaly.core.creator.object.Luckable;
import me.khaly.core.creator.object.MeleeDamage;
import me.khaly.core.creator.object.RangedDamage;
import me.khaly.core.creator.object.Strengthable;
import me.khaly.core.creator.object.UnchangeableMaterial;
import me.khaly.core.creator.object.UnchangeableType;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.StatAttribute;

@SuppressWarnings("unchecked")
public class ItemBowCreator extends ItemCreator implements UnchangeableType, Damn, Damageable, Intelligenceable, Luckable, Strengthable, MeleeDamage, RangedDamage, UnchangeableMaterial {

	public ItemBowCreator(String displayName) {
		super(Material.BOW, displayName);
		configureType(ItemType.BOW);
		this.setKeepEnchants(true);
	}
	
	@Override
	public ItemBowCreator setStrength(double strength) {
		customTag.setDouble(StatAttribute.STRENGTH.toString(), strength);
		return this;
	}

	@Override
	public ItemBowCreator setLuck(double luck) {
		customTag.setDouble(StatAttribute.LUCK.toString(), luck);
		return this;
	}

	@Override
	public ItemBowCreator setIntelligence(double intelligence) {
		customTag.setDouble(StatAttribute.INTELLIGENCE.toString(), intelligence);
		return this;
	}

	@Override
	public ItemBowCreator setDamage(double damage) {
		customTag.setDouble("damage", damage);
		return this;
	}
	
	@Override
	public ItemBowCreator setCursedEnergy(double energy) {
		customTag.setDouble(StatAttribute.CURSED_ENERGY.toString(), energy);
		return this;
	}

	@Override
	public ItemBowCreator setRangedDamage(double value) {
		customTag.setDouble(StatAttribute.RANGED_DAMAGE.toString(), value);
		return this;
	}

	@Override
	public ItemBowCreator setMeleeDamage(double value) {
		customTag.setDouble(StatAttribute.MELEE_DAMAGE.toString(), value);
		return this;
	}
}