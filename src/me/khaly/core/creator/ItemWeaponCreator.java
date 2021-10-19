package me.khaly.core.creator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.creator.object.Cooldownable;
import me.khaly.core.creator.object.Damageable;
import me.khaly.core.creator.object.Damn;
import me.khaly.core.creator.object.Intelligenceable;
import me.khaly.core.creator.object.Luckable;
import me.khaly.core.creator.object.MeleeDamage;
import me.khaly.core.creator.object.RangedDamage;
import me.khaly.core.creator.object.Strengthable;
import me.khaly.core.creator.object.UnchangeableType;
import me.khaly.core.enums.ItemCooldown;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.StatAttribute;

@SuppressWarnings("unchecked")
public class ItemWeaponCreator extends ItemCreator implements UnchangeableType, Cooldownable, Damageable, Strengthable, Intelligenceable, Luckable, Damn, MeleeDamage, RangedDamage {
	
	public ItemWeaponCreator(ItemStack item, String displayName) {
		super(item, displayName);
		configureType(ItemType.WEAPON);
		this.setKeepEnchants(true);
	}
	
	public ItemWeaponCreator(Material material, String displayName) {
		this(new ItemStack(material, 1, (short)0), displayName);
	}
		
	@Override
	public ItemWeaponCreator setDamage(double damage) {
		customTag.setDouble("damage", damage);
		return this;
	}

	@Override
	public ItemWeaponCreator setStrength(double strength) {
		customTag.setDouble(StatAttribute.STRENGTH.toString(), strength);
		return this;
	}

	@Override
	public ItemWeaponCreator setIntelligence(double intelligence) {
		customTag.setDouble(StatAttribute.INTELLIGENCE.toString(), intelligence);
		return this;
	}
		
	@Override
	public ItemWeaponCreator setLuck(double luck) {
		customTag.setDouble(StatAttribute.LUCK.toString(), luck);
		return this;
	}
	
	@Override
	public ItemWeaponCreator setCursedEnergy(double energy) {
		customTag.setDouble(StatAttribute.CURSED_ENERGY.toString(), energy);
		return this;
	}
	
	@Override
	public ItemWeaponCreator setRangedDamage(double value) {
		customTag.setDouble(StatAttribute.RANGED_DAMAGE.toString(), value);
		return this;
	}

	@Override
	public ItemWeaponCreator setMeleeDamage(double value) {
		customTag.setDouble(StatAttribute.MELEE_DAMAGE.toString(), value);
		return this;
	}

	@Override
	public ItemWeaponCreator setCooldown(ItemCooldown itemCooldown) {
		customTag.setString("cooldown", itemCooldown.name());
		return this;
	}
}
