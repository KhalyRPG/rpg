package me.khaly.core.creator;

import org.bukkit.Material;

import me.khaly.core.creator.object.Cooldownable;
import me.khaly.core.creator.object.Damageable;
import me.khaly.core.creator.object.Damn;
import me.khaly.core.creator.object.Defenseable;
import me.khaly.core.creator.object.Healthable;
import me.khaly.core.creator.object.Intelligenceable;
import me.khaly.core.creator.object.Luckable;
import me.khaly.core.creator.object.RegenerableHealth;
import me.khaly.core.creator.object.RegenerableMana;
import me.khaly.core.creator.object.Strengthable;
import me.khaly.core.enums.ItemCooldown;

@SuppressWarnings("unchecked")
public class ItemUniversalCreator extends ItemCreator implements Damn, Cooldownable, Damageable, Defenseable, Healthable, Intelligenceable, Luckable, RegenerableHealth, RegenerableMana, Strengthable {

	public ItemUniversalCreator(Material mat, int amount, short data, String displayName) {
		super(mat, amount, data, displayName);
	}

	@Override
	public ItemUniversalCreator setStrength(double strength) {
		customTag.setDouble("strength", strength);
		return this;
	}

	@Override
	public ItemUniversalCreator setManaRegeneration(double manaRegeneration) {
		customTag.setDouble("manaRegen", manaRegeneration);
		return this;
	}

	@Override
	public ItemUniversalCreator setHealthRegeneration(double healthRegeneration) {
		customTag.setDouble("healthRegen", healthRegeneration);
		return this;
	}

	@Override
	public ItemUniversalCreator setLuck(double luck) {
		customTag.setDouble("luck", luck);
		return this;
	}

	@Override
	public ItemUniversalCreator setIntelligence(double intelligence) {
		customTag.setDouble("intelligence", intelligence);
		return this;
	}

	@Override
	public ItemUniversalCreator setHealth(double health) {
		customTag.setDouble("health", health);
		return this;
	}

	@Override
	public ItemUniversalCreator setDefense(double defense) {
		customTag.setDouble("defense", defense);
		return this;
	}

	@Override
	public ItemUniversalCreator setDamage(double damage) {
		customTag.setDouble("damage", damage);
		return this;
	}

	@Override
	public ItemUniversalCreator setCooldown(ItemCooldown itemCooldown) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ItemUniversalCreator setCursedEnergy(double energy) {
		customTag.setDouble("cursed-energy", energy);
		return this;
	}

}