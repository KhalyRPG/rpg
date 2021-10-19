package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Defenseable {
	<T extends ItemCreator> T setDefense(double defense);
}
