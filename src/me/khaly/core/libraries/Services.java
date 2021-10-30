package me.khaly.core.libraries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;
import me.khaly.core.creator.ItemCreator;
import me.khaly.core.enchantment.ItemEnchantment;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.Rarity;
import me.khaly.core.gui.object.Gui;
import me.khaly.core.passive.object.ItemPassives;
import me.khaly.core.util.Utils;
import net.minecraft.server.v1_12_R1.NBTTagByte;

public class Services {
	
	private Cache cache;
	private ItemPassives itemPassives;
	private Utils utils;
	private Files files;
	private ItemUpdater itemUpdater;
	private ItemStack menu;
	
	public Services(KhalyCore core) {
		this.cache = new Cache();
		this.itemPassives = new ItemPassives();
		this.utils = new Utils(core);
		this.files = new Files(core);
		this.itemUpdater = new ItemUpdater(core);
		
		ItemCreator creator = new ItemCreator(Material.NETHER_STAR, "Khaly Menu");
		creator.setRarity(Rarity.SPECIAL);
		creator.setType(ItemType.MENU);
		creator.setCustomTagValue("menuItem", new NBTTagByte((byte)1));
		this.menu = creator.buildItem(null);
	}
	
	public Cache getCache() {
		return cache;
	}
	
	public ItemPassives getPassives() {
		return itemPassives;
	}
	
	public Utils getUtils() {
		return utils;
	}
	
	public Files getFiles() {
		return files;
	}
	
	public ItemUpdater getItemUpdater() {
		return itemUpdater;
	}
	
	public Gui getGUI(String id) {
		return cache.getGuis().get(id);
	}
	
	public ItemEnchantment getEnchantment(String id) {
		return cache.getEnchantments().get(id);
	}
	
	public ItemStack getMenuItemStack() {
		return this.menu;
	}
	
}
