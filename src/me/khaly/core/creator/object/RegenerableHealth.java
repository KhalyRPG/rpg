package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface RegenerableHealth {
	<T extends ItemCreator> T setHealthRegeneration(double healthRegeneration);
}
