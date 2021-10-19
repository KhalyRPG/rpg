package me.khaly.core.creator.object;

import me.khaly.core.passive.object.ItemPassive;

public interface Passiveable {
	<T extends ItemPassive> T setAbility(ItemPassive ability);
}
