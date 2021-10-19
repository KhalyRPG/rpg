package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface MeleeDamage {
	<T extends ItemCreator> T setMeleeDamage(double value);
}
