package me.khaly.core.enums;

import org.bukkit.ChatColor;

public enum Rarity {
	COMMON("Com�n", ChatColor.WHITE, (short)0),
	UNCOMMON("Poco com�n", ChatColor.GREEN, (short)5),
	RARE("Raro", ChatColor.LIGHT_PURPLE, (short)2),
	EPIC("�pico", ChatColor.DARK_PURPLE, (short)10),
	FABLED("Legendario", ChatColor.RED, (short)14),
	MYTHIC("M�tico", ChatColor.GOLD, (short)4),
	SPECIAL("Especial", ChatColor.AQUA, (short)3)
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