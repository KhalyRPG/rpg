package me.atog.anr.plugin.object;

import me.atog.anr.plugin.types.SlayerType;

public class Slayer {
	
	private SlayerType type;
	private String level;
	
	public Slayer() {
		
	}
	
	public Slayer(SlayerType type, String level) {
		this.type = type;
		this.level = level;
	}
	
	public SlayerType getType() {
		return type;
	}
	
	public String getLevel() {
		return level;
	}
}
