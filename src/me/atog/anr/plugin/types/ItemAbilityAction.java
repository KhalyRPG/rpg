package me.atog.anr.plugin.types;

public enum ItemAbilityAction {
	LEFT_CLICK, RIGHT_CLICK, PHYSICAL;
	
	
	public String replaceToSpaces(/*ItemAbilityAction ac*/) {
		return this.name().replace("_", " ").replace("-", " ");
	}
	
	public String replaceFormSpaces(/*ItemAbilityAction ac*/) {
		return this.name().replace(" ", "_");
	}
	
	
}
