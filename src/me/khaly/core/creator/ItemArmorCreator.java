package me.khaly.core.creator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.creator.object.Damn;
import me.khaly.core.creator.object.Defenseable;
import me.khaly.core.creator.object.Healthable;
import me.khaly.core.creator.object.Intelligenceable;
import me.khaly.core.creator.object.Luckable;
import me.khaly.core.creator.object.MeleeDamage;
import me.khaly.core.creator.object.RangedDamage;
import me.khaly.core.creator.object.RegenerableHealth;
import me.khaly.core.creator.object.RegenerableMana;
import me.khaly.core.creator.object.Strengthable;
import me.khaly.core.creator.object.UnchangeableType;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.StatAttribute;

@SuppressWarnings("unchecked")
public class ItemArmorCreator extends ItemCreator implements UnchangeableType, Healthable, Intelligenceable, Luckable, Strengthable, Defenseable, RegenerableHealth, RegenerableMana, Damn, MeleeDamage, RangedDamage {
	
	public ItemArmorCreator(ItemStack item, String displayName) {
		super(item, displayName);
		configureType(ItemType.ARMOR);
		this.setKeepEnchants(true);
	}
	
	public ItemArmorCreator(Material material, String displayName) {
		this(new ItemStack(material), displayName);
	}

	@Override
	public ItemArmorCreator setStrength(double strength) {
		customTag.setDouble(StatAttribute.STRENGTH.toString(), strength);
		return this;
	}

	@Override
	public ItemArmorCreator setLuck(double luck) {
		customTag.setDouble(StatAttribute.LUCK.toString(), luck);
		return this;
	}
	
	@Override
	public ItemArmorCreator setIntelligence(double intelligence) {
		customTag.setDouble(StatAttribute.INTELLIGENCE.toString(), intelligence);
		return this;
	}

	@Override
	public ItemArmorCreator setHealth(double health) {
		customTag.setDouble(StatAttribute.HEALTH.toString(), health);
		return this;
	}
	
	@Override
	public ItemArmorCreator setDefense(double defense) {
		customTag.setDouble(StatAttribute.DEFENSE.toString(), defense);
		return this;
	}
	
	@Override
	public ItemStack buildItem(Player player) {
		return build(player);
	}

	@Override
	public ItemArmorCreator setHealthRegeneration(double healthRegeneration) {
		customTag.setDouble(StatAttribute.HEALTH_REGEN.toString(), healthRegeneration);
		return this;
	}

	@Override
	public ItemArmorCreator setManaRegeneration(double manaRegeneration) {
		customTag.setDouble(StatAttribute.MANA_REGEN.toString(), manaRegeneration);
		return this;
	}

	@Override
	public ItemArmorCreator setCursedEnergy(double energy) {
		customTag.setDouble(StatAttribute.CURSED_ENERGY.toString(), energy);
		return this;
	}
	
	@Override
	public ItemArmorCreator setRangedDamage(double value) {
		customTag.setDouble(StatAttribute.RANGED_DAMAGE.toString(), value);
		return this;
	}

	@Override
	public ItemArmorCreator setMeleeDamage(double value) {
		customTag.setDouble(StatAttribute.MELEE_DAMAGE.toString(), value);
		return this;
	}
	
}