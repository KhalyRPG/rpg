package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;
import me.khaly.core.enums.ItemType;

public interface UnchangeableType {
	/**
	 * @deprecated You cannot change the item type since you are using an object that does it by default.
	 */
	@Deprecated
	default public ItemCreator setType(ItemType type) {
		return null;
	}
}
