package me.khaly.core.creator.object;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Creator {
	ItemStack buildItem(Player player);
}
