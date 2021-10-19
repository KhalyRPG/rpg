package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Damn {
	<T extends ItemCreator> T setCursedEnergy(double energy);
}
