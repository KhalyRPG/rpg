package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Healthable {
	<T extends ItemCreator> T setHealth(double health);
}
