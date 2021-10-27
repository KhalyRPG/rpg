package me.khaly.core.creator;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import me.khaly.core.builder.ItemBuilder;
import me.khaly.core.enums.AccessoryType;

public class ItemAccessoryCreator extends ItemUniversalCreator {

	public ItemAccessoryCreator(ItemStack item, String displayName, AccessoryType type) {
		super(item, displayName);
		this.setType(type.getType());
		this.setCustomTagValue("stackable", UUID.randomUUID().toString());
	}
	
	public ItemAccessoryCreator(ItemBuilder builder, String displayName, AccessoryType type) {
		this(builder.build(), displayName, type);
	}
	
}
