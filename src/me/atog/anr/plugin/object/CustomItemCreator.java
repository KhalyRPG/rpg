package me.atog.anr.plugin.object;

import java.util.ArrayList;
import java.util.List;

import me.atog.anr.plugin.types.ItemAbilityAction;
import me.atog.anr.plugin.types.ItemAbility;
import me.atog.anr.plugin.types.Rarity;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemCreator {
    private final ItemMeta m = Util.getEmptyItemMeta();
	private Material mat;
	private int amount;
	private short data;
	private Rarity rarity;
	private String name, description;
	private ItemAbility ability;
	private ItemAbilityAction action;
	private List<String> lore = new ArrayList<>();
	private boolean hasData = false, hasAbility = false, hasDamage = false, hasStrength = false, hasSpeed = false, enchanted = false;
	private int damage, strength, speed;
	private ItemFlag[] flags = {ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS};





	public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,boolean enchanted,int damage,int strength,int speed) {
        this.mat = mat;
	    this.amount = amount;
		this.data = data;
		this.name = name;
		this.description = description;
		this.enchanted = enchanted;
		this.rarity = rarity;
		this.strength = strength;
		this.damage = damage;
		this.speed = speed;
		this.ability = ability;
		this.action = action;
        this.hasDamage = true;
        this.hasStrength = true;
        this.hasSpeed = true;
        if(ability != null)this.hasAbility = true;
	}





    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,boolean enchanted,int damage,int strength) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.enchanted = enchanted;
        this.rarity = rarity;
        this.strength = strength;
        this.damage = damage;
        this.ability = ability;
        this.action = action;
        this.hasDamage = true;
        this.hasStrength = true;
        if(ability != null)this.hasAbility = true;
    }

    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,boolean enchanted,int damage) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.enchanted = enchanted;
        this.rarity = rarity;
        this.damage = damage;
        this.ability = ability;
        this.action = action;
        this.hasDamage = true;
        this.hasAbility = true;
    }
    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,int damage) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.damage = damage;
        this.ability = ability;
        this.action = action;
        this.hasDamage = true;
        if(ability != null)this.hasAbility = true;
    }
    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,int damage, int strength, int speed) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.damage = damage;
        this.strength = strength;
        this.speed = speed;
        this.ability = ability;
        this.action = action;
        this.hasDamage = true;
        this.hasStrength = true;
        this.hasSpeed = true;
        if(ability != null)this.hasAbility = true;
    }
    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,boolean enchanted) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.enchanted = enchanted;
        this.rarity = rarity;
        this.ability = ability;
        this.action = action;
        if(ability != null)this.hasAbility = true;
    }

    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.ability = ability;
        this.action = action;
        if(ability != null)this.hasAbility = true;
    }

    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
    }
    public CustomItemCreator(Material mat, int amount, short data,String name,String description) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
    }
    public CustomItemCreator(Material mat, int amount, short data,String name,Rarity rarity) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.rarity = rarity;
    }

    public CustomItemCreator(Material mat, int amount, short data,String name) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
    }
    @Deprecated
    public CustomItemCreator(Material mat, int amount, short data) {
	    this.mat = mat;
	    this.amount = amount;
	    this.data = data;
    }
    
    public CustomItemCreator(Material mat, int amount, short data,String name,String description,Rarity rarity,ItemAbility ability, ItemAbilityAction action,int damage,int strength) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.strength = strength;
        this.damage = damage;
        this.ability = ability;
        this.action = action;
        this.hasDamage = true;
        this.hasStrength = true;
        this.hasAbility = true;
    }

	public ItemStack build() {
	    preProcess();
		setName();
		setAttributes();
		setDescription();
		setAbility();
		setRarity();
		enchant();
		hideAttributes();
		this.m.setLore(this.lore);
		ItemStack newItem = new ItemStack(this.mat, this.amount, hasData ? this.data : (short)0);
		newItem.setItemMeta(this.m);
		return newItem;
	}
    private void preProcess() {
        if(rarity == null)rarity = Rarity.COMMON;
	}
    private void setName() {
		if(name != null)this.m.setDisplayName(this.rarity.getRarityColor() + Util.color("&l"+this.name));
	    //else this.m.setDisplayName(this.rarity.getRarityColor()+Util.color("&l"+this.mat.getData().getCanonicalName()));
	}
	
	private void setAttributes() {
		if(this.hasDamage) {if(this.damage > 0)this.lore.add(Util.color("&fDamage: &c+"+this.damage));}
		if(this.hasStrength) {if(this.strength > 0)this.lore.add(Util.color("&fStrength: &c+"+this.strength));}
		if(this.hasSpeed) {if(this.speed > 0)this.lore.add(Util.color("&fSpeed: &a+"+this.speed));}
		if(this.hasDamage || this.hasStrength || this.hasSpeed)this.lore.add(Util.color("&7"));
	}

	private void setDescription() {
	    if(this.description != null)this.lore.add(Util.color("&7"+this.description));
    }

	private void enchant() {
		if(this.enchanted)this.m.addEnchant(Enchantment.DURABILITY, 1, true);
	}

	private void setAbility() {
		if(this.hasAbility) {
			this.lore.add(Util.color("&7"));
			this.lore.add(Util.color("&6Item Ability: &b&l"+this.ability.name()+" &e&l"+action.replaceToSpaces()));
			this.lore.add(Util.color("&7"+this.ability.getDescription()));
			this.lore.add(Util.color("&8Mana cost: &3"+this.ability.getCost()));
		}
	}

	private void setRarity() {
        if (this.rarity != null) {
            this.lore.add(Util.color("&7"));
            this.lore.add(this.rarity.getRarityColor() + Util.color("&l" + this.rarity.name().replace("_", " ")));
        }
    }

    private void hideAttributes() {
	    this.m.addItemFlags(this.flags);
    }

    /*meta.setDisplayName(rarity.getRarityColor() + "" + Util.color("&l"+name));
		List<String> lore = new ArrayList<>();
		if(hasDamage)lore.add(Util.color("&fDamage: &c+"+damage));
		if(hasStrength)lore.add(Util.color("&fStrength: &c+"+strength));
		if(hasSpeed)lore.add(Util.color("&fSpeed: &a+"+speed));
		if(hasDamage || hasStrength || hasSpeed)lore.add(Util.color("&7"));
		lore.add(Util.color("&7"+description));
		if(hasAbility) {
			lore.add(Util.color("&7"));
			lore.add(Util.color("&6Item Ability: &b&l"+ability.name()+" &e&l"+action.replaceToSpaces()));
			lore.add(Util.color("&7"+ability.getDescription()));
			lore.add(Util.color("&8Mana cost: &3"+ability.getCost()));
		}
		lore.add(Util.color("&7"));
		lore.add(rarity.getRarityColor()+Util.color("&l"+rarity.name().replace("_", " ")));
		meta.setLore(lore);
		meta.setUnbreakable(true);
		if(enchanted)meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(flags);
		item.setItemMeta(meta);*/
}
