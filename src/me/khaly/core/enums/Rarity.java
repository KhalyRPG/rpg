package me.khaly.core.enums;

import org.bukkit.ChatColor;

public enum Rarity {
	COMMON("Com�n", ChatColor.WHITE, (short) 0),
	UNCOMMON("Poco com�n", ChatColor.GREEN, (short) 5),
	RARE("Raro", ChatColor.BLUE, (short) 11),
	EPIC("�pico", ChatColor.DARK_PURPLE, (short) 10),
	FABLED("Legendario", ChatColor.GOLD, (short) 4),
	MYTHIC("M�tico", ChatColor.RED, (short) 14),
	SPECIAL("Especial", ChatColor.AQUA, (short) 3)
	;
	private String name;
	private ChatColor color;
	private short blockColor;
	
	Rarity(String name, ChatColor color, short blockColor) {
		this.name = name;
		this.color = color;
		this.blockColor = blockColor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
	
	public short getColorBlockId() {
		return blockColor;
	}
	
}