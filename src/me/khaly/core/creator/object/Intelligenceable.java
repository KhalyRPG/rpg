package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Intelligenceable {
	<T extends ItemCreator> T setIntelligence(double intelligence);
}
