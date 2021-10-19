package me.khaly.core.enums;

public enum StatAttribute {
	HEALTH(10, "health"),
	DEFENSE("defense"),
	INTELLIGENCE(5, "intelligence"),
	STRENGTH("strength"),
	SPEED("speed"),
	MANA_REGEN(1, "manaRegen"),
	LUCK("luck"),
	HEALTH_REGEN(1, "healthRegen"),
	CURSED_ENERGY("cursed-energy"),
	MELEE_DAMAGE(1, "melee-damage"),
	RANGED_DAMAGE(1, "ranged-damage"),
	ATTACK_SPEED("attack-speed");
	
	private double defaultValue;
	private String tagID;
	
	StatAttribute(double value, String tagID) {
		this.defaultValue = value;
		this.tagID = tagID;
	}
	
	StatAttribute(String tagID) {
		this.defaultValue = 0;
		this.tagID = tagID;
	}
	
	public double getDefaultValue() {
		return this.defaultValue;
	}
	
	public String getTagID() {
		return tagID;
	}
	
	public String toString() {
		return tagID;
	}
	
}
