package me.khaly.core.enums;

public enum AttributeName {
	ATTACK_DAMAGE("generic.attackDamage", "damage"),
	ATTACK_SPEED("generic.attackSpeed", "attack_speed"),
	MAX_HEALTH("generic.maxHealth", "health"),
	MOVEMENT_SPEED("generic.movementSpeed", "speed"),
	LUCK("generic.luck", "luck"),
	DEFENSE("generic.armor", "defense");
	private String name,
				   khalyTagName;
	AttributeName(String name, String khalyTagName) {
		this.name = name;
		this.khalyTagName = khalyTagName;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNameInTag() {
		return khalyTagName;
	}
	
}
