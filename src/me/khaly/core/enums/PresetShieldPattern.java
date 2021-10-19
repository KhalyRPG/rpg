package me.khaly.core.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

public enum PresetShieldPattern {
	CREEPER(new Pattern(DyeColor.CYAN, PatternType.CIRCLE_MIDDLE),
			new Pattern(DyeColor.CYAN, PatternType.TRIANGLE_TOP))
	;
	private List<Pattern> patterns;
	PresetShieldPattern(Pattern... patterns) {
		this.patterns = new ArrayList<>();
		for(Pattern pattern : patterns) {
			this.patterns.add(pattern);
		}
		
	}
	
	public List<Pattern> getPatterns() {
		return patterns;
	}
}