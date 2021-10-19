package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;

public interface Luckable {
	<T extends ItemCreator> T setLuck(double luck);
}
