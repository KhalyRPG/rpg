package me.khaly.core.creator.object;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;

import me.khaly.core.creator.ItemCreator;
import me.khaly.core.enums.PresetShieldPattern;

public interface Customizable {
	<T extends ItemCreator> T setBaseColor(DyeColor baseColor);
	<T extends ItemCreator> T addPattern(Pattern pattern);
	<T extends ItemCreator> T setPreset(PresetShieldPattern preset);
}
