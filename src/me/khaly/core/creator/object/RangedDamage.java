package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface RangedDamage {
	<T extends ItemCreator> T setRangedDamage(double value);
}
