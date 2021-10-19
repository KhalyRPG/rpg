package me.khaly.core.libraries;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.khaly.core.KhalyCore;
import me.khaly.core.enums.Rarity;
import me.khaly.core.util.ItemUtils;
import me.khaly.core.util.Serializer;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class ItemUpdater {
	private KhalyCore core;
	private static List<String> valid;
	
	public ItemUpdater(KhalyCore core) {
		this.core = core;
	}

	static {
		valid = Arrays.asList("id", "name", "rarity", "type", "ability", "enchantable", "strength", "damage", "intelligence", "luck", "defense", "healthRegen", "manaRegen", "max-durability");
	}
	
	public ItemStack updateItem(Player player, ItemStack item) {
		YamlFile file = core.getServices().getFiles().getItems();
		ItemStack base = item.clone();
		
		if(!ItemUtils.isCustomItem(item)) {
			ItemStack result = item.clone();
			for(String key : core.getServices().getFiles().getDefaultItems().getConfigurationSection("items").getKeys(false)) {
				if(key.toUpperCase().equals(item.getType().name())) {
					Map<String, Object> serialized = Serializer.serialize(core.getServices().getFiles().getDefaultItems().getConfigurationSection("items."+key));
					result = Serializer.deserialize(serialized, player, true);
					break;
				}
			}
			return result;
		}
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(base);
		NBTTagCompound tag = nms.getTag();
		NBTTagCompound customTag = tag.getCompound("khaly");
		String id = customTag.getString("id");
		if(customTag.hasKey("default")) {
			id = base.getType().name().toLowerCase();
			file = core.getServices().getFiles().getDefaultItems();
		} else {
			if(!customTag.hasKey("id") || !file.contains("items."+id)) {
				ItemStack clone = item.clone();
				ItemMeta cloneMeta = clone.getItemMeta();
				cloneMeta.setLore(ItemUtils.buildLore(tag, player));
				clone.setItemMeta(cloneMeta);
				return clone;
			}
		}
		
		ConfigurationSection section = file.getConfigurationSection("items."+id);
		Map<String, Object> serialized = Serializer.serialize(section);
		Serializer.serializeToNBTBase(serialized).forEach((str, nbt) -> {
			if(str.startsWith("itemstack.")) {
				if(str.equals("itemstack.material")) {
					base.setType(Material.valueOf((String)serialized.get(str)));
				} else if(str.equals("itemstack.durability")) {
					base.setDurability((Short)serialized.get(str));
				}
				return;
			}
			if(!isValidKey(str))return;
			customTag.set(str, nbt);
		});
		
		tag.set("khaly", customTag);
		nms.setTag(tag);
		ItemStack result = CraftItemStack.asBukkitCopy(nms);
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(Rarity.valueOf(customTag.getString("rarity")).getColor() + customTag.getString("name"));
		meta.setLore(ItemUtils.buildLore(tag, player));
		meta.setUnbreakable(true);
		result.setItemMeta(meta);
		result.setType(base.getType());
		result.setAmount(base.getAmount());
		result.setDurability(base.getDurability());
		return result;
	}
	
	public static List<String> getValidKeys() {
		return valid;
	}
	
	public static boolean isValidKey(String key) {
		boolean valid = false;
		for(String validKey : getValidKeys()) {
			if(validKey.equalsIgnoreCase(key)) {
				valid = true;
				break;
			}
		}
		return valid;
	}
	
}