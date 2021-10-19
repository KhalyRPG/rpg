package me.khaly.core.creator.object;

import me.khaly.core.creator.ItemCreator;
import me.khaly.core.enums.ItemCooldown;

public interface Cooldownable {
	public <T extends ItemCreator> T setCooldown(ItemCooldown itemCooldown);
}
