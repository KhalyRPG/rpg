package me.khaly.core.enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.inventory.ItemStack;

import me.khaly.core.enums.EnchantCastType;
import me.khaly.core.enums.EnchantmentCategory;
import me.khaly.core.enums.ItemType;
import me.khaly.core.user.User;
import me.khaly.core.util.ItemUtils;

public abstract class ItemEnchantment {
	
	private String id;
	private String name;
	private int numberID;
	
	private BiConsumer<User, EnchantmentFunction> function;
	private int maxLevel;
	private List<ItemType> allowedTypes;
	private EnchantCastType castType;
	private EnchantmentCategory category;
	
	public ItemEnchantment(String id, String name, int numberID, EnchantCastType castType) {
		this.id = id;
		this.name = name;
		this.numberID = numberID;
		this.maxLevel = 1;
		this.allowedTypes = new ArrayList<>();
		this.castType = castType;
	}
	
	public String getID() {
		return id;
	}
	
	public String getDisplayName() {
		return name;
	}
	
	public int getIntegerID() {
		return numberID;
	}
	
	public BiConsumer<User, EnchantmentFunction> getFunction() {
		return function;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public List<ItemType> getAllowedTypes() {
		return allowedTypes;
	}
	
	public boolean isAllowed(ItemStack item) {
		if(!ItemUtils.isCustomItem(item)) {
			return false;
		}
		
		boolean allowed = false;
		
		for(ItemType type : getAllowedTypes()) {
			boolean isType = ItemUtils.isType(item, type);
			if(isType) {
				allowed = isType;
				break;
			}
		}
		
		return allowed;
	}
	
	public EnchantCastType getCastType() {
		return castType;
	}
	
	public EnchantmentCategory getCategory() {
		return category;
	}
	
	protected void setFunction(BiConsumer<User, EnchantmentFunction> function) {
		this.function = function;
	}
	
	protected void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	protected void addAllowedTypes(ItemType...types) {
		for(ItemType type : types) {
			this.allowedTypes.add(type);
		}
	}
	
	protected void setCategory(EnchantmentCategory category) {
		this.category = category;
	}
}
