package me.khaly.core.user.attributes;

import org.bukkit.attribute.Attribute;

import me.khaly.core.KhalyCore;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.rpg.classes.object.RPGClass;
import me.khaly.core.user.User;
import me.khaly.core.util.ItemUtils;

public class UserAttributesImpl implements UserAttributes {
	
	private User user;
	private KhalyCore core;
	
	private double mana;
	
	public UserAttributesImpl(KhalyCore core, User user) {
		this.user = user;
		this.core = core;
		
		this.mana = getIntelligence() * 2;
	}
	
	@Override
	public double getHealth() {
		return user.getBukkitPlayer().getHealth();
	}

	@Override
	public double getMaxHealth() {
		double amplifier = ItemUtils.getArmorAmplifiers(user.getBukkitPlayer(), StatAttribute.HEALTH);
		return (10 + amplifier + getClassAmplifier(Types.HEALTH));
	}

	@Override
	public double getDefense() {
		return ItemUtils.getArmorAmplifiers(user.getBukkitPlayer(), StatAttribute.DEFENSE);
	}

	@Override
	public double getIntelligence() {
		return this.getNoItemIntelligence() + ItemUtils.getHandAmplifiers(core, user.getBukkitPlayer(), StatAttribute.INTELLIGENCE);
	}
	
	@Override
	public double getStrength() {
		return (getNoItemStrength() + ItemUtils.getHandAmplifiers(core, user.getBukkitPlayer(), StatAttribute.STRENGTH));
	}

	@Override
	public double getSpeed() {
		double armor = ItemUtils.getArmorAmplifiers(user.getBukkitPlayer(), StatAttribute.SPEED);
		double hand = ItemUtils.getHandAmplifiers(core, user.getBukkitPlayer(), StatAttribute.SPEED);
		return armor + hand;
	}

	@Override
	public double getCursedEnergy() {
		return this.getNoItemCursedEnergy() + ItemUtils.getHandAmplifiers(core, user.getBukkitPlayer(), StatAttribute.CURSED_ENERGY);
	}

	@Override
	public double getLuck() {
		return user.getBukkitPlayer().getAttribute(Attribute.GENERIC_LUCK).getValue();
	}

	@Override
	public double getMana() {
		return mana;
	}

	@Override
	public double getMaxMana() {
		return getIntelligence() * 2;
	}

	@Override
	public double getNoItemStrength() {
		double amplifier = ItemUtils.getArmorAmplifiers(user.getBukkitPlayer(), StatAttribute.STRENGTH);
		return amplifier;
	}

	@Override
	public double getNoItemIntelligence() {
		double amplifier = ItemUtils.getArmorAmplifiers(user.getBukkitPlayer(), StatAttribute.INTELLIGENCE);
		return (5 + amplifier + getClassAmplifier(Types.INTELLIGENCE));
	}
	
	private double getClassAmplifier(Types type) {
		if(!user.hasProfile())return 0;
		RPGClass clazz = user.getProfile().getRPGClass();
		int level = user.getProfile().getLevel();
		switch(type) {
		case HEALTH:
			return clazz.getHealth(level);
		case INTELLIGENCE:
			return clazz.getIntelligence(level);
		}
		return 0;
	}
	
	enum Types {
		HEALTH, INTELLIGENCE;
	}

	@Override
	public double getNoItemCursedEnergy() {
		return ItemUtils.getArmorAmplifiers(user.getBukkitPlayer(), StatAttribute.CURSED_ENERGY);
	}

	@Override
	public void setMana(double arg0) {
		this.mana = arg0;
	}

	@Override
	public void addMana(double arg0) {
		if(arg0 > getMaxMana()) {
			arg0 = getMaxMana();
		} else {
			this.mana += arg0;
		}
	}

	@Override
	public void removeMana(double arg0) {
		if((arg0 - mana) <= 0) {
			setMana(0);
		} else {
			this.mana -= arg0;
		}
	}
}
