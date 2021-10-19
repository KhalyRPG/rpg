package me.khaly.core.creator.object;

import org.bukkit.Material;

import me.khaly.core.creator.ItemCreator;

public interface UnchangeableMaterial {
	/**
	 * @deprecated You cannot change the item material since you are using an object that does it by default.
	 */
	@Deprecated
	default public ItemCreator setMaterial(Material material) {
		return null;
	}
}
