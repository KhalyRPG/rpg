package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Strengthable {
	<T extends ItemCreator> T setStrength(double strength);
}
