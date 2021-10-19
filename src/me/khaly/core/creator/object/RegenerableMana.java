package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface RegenerableMana {
	<T extends ItemCreator> T setManaRegeneration(double manaRegeneration);
}
