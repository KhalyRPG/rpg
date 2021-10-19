package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Damageable {
	<T extends ItemCreator> T setDamage(double damage);
}
