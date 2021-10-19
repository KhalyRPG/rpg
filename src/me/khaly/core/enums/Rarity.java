package me.khaly.core.enums;

import org.bukkit.ChatColor;

public enum Rarity {
	COMMON("Común", ChatColor.WHITE, (short)0),
	UNCOMMON("Poco común", ChatColor.GREEN, (short)5),
	RARE("Raro", ChatColor.LIGHT_PURPLE, (short)2),
	EPIC("Épico", ChatColor.DARK_PURPLE, (short)10),
	FABLED("Legendario", ChatColor.RED, (short)14),
	MYTHIC("Mítico", ChatColor.GOLD, (short)4),
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