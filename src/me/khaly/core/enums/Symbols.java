package me.khaly.core.enums;

public enum Symbols {
	DAMAGE("⚔"),
	STRENGTH("❀"),
	INTELLIGENCE("✦"),
	MANA("✎"),
	DEFENSE("✲"),
	HEALTH("❤"),
	CURSED_DAMAGE("☠"),
	CURSED_ENERGY("♰"),
	FEMALE("♀"),
	MALE("♂"),
	CIRCLE_STAR("✪"),
	STAR("★"),
	EMPTY_STAR("☆"),
	INFINITE("∞"),
	SPEED("❃"),
	LUCK("♧"),
	PLUS("✚"),
	SUN("☀"),
	CLOUD("☁"),
	MOON("☾"),
	SOVIET("☭"),
	SNOWMAN("☃"),
	TM("™"),
	X("✖"),
	X_ITALIC("✗"),
	CHECK_MARK("✔"),
	CHECK_MARK_ITALIC("✓"),
	POINT_RIGHT("→"),
	SMALL_STAR("⋆"),
	ARROW("➹"),
	OMEGA("Ω"),
	VERTICAL_HEART("❥"),
	NUCLEAR("☢"),
	FALLING_STAR("☄"),
	CIRCLE_WITH_POINT_IN_THE_CENTER("⊙")
	;
	private String symbol;
	
	Symbols(String symbol) {
		this.symbol = symbol;
	}
	
	public String toString() {
		return this.symbol;
	}
}
