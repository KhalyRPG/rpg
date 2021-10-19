package me.khaly.core.util;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import me.khaly.core.builder.ItemBuilder;
import me.khaly.core.enums.Rarity;
import me.khaly.core.libraries.ItemUpdater;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class Serializer extends Util {
	public static Map<String, Object> serialize(ItemStack item) {
		if(item == null || item.getType() == Material.AIR)return null;
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		NBTTagCompound customTag = tag.getCompound("khaly");
		Map<String, Object> map = new HashMap<>();
		
		if(customTag.hasKey("id")) {
			map.put("id", customTag.getString("id"));
		}
		if(customTag.hasKey("name")) {
			map.put("name", customTag.getString("name"));
		}
		if(customTag.hasKey("rarity")) {
			map.put("rarity", customTag.getString("rarity"));
		}
		if(customTag.hasKey("type")) {
			map.put("type", customTag.getString("type"));
		}
		if(customTag.hasKey("ability")) {
			map.put("ability", customTag.getString("ability"));
		}
		if(customTag.hasKey("enchantable")) {
			map.put("enchantable", customTag.getBoolean("enchantable"));
		}
		if(customTag.hasKey("strength")) {
			map.put("strength", customTag.getDouble("strength"));
		}
		if(customTag.hasKey("damage")) {
			map.put("damage", customTag.getDouble("damage"));
		}
		if(customTag.hasKey("intelligence")) {
			map.put("intelligence", customTag.getDouble("intelligence"));
		}
		if(customTag.hasKey("luck")) {
			map.put("luck", customTag.getDouble("luck"));
		}
		if(customTag.hasKey("defense")) {
			map.put("defense", customTag.getDouble("defense"));
		}
		if(customTag.hasKey("healthRegen")) {
			map.put("healthRegen", customTag.getDouble("healthRegen"));
		}
		if(customTag.hasKey("manaRegen")) {
			map.put("manaRegen", customTag.getDouble("manaRegen"));
		}
		if(customTag.hasKey("max-durability")) {
			map.put("max-durability", customTag.getInt("max-durability"));
			map.put("durability", customTag.getInt("max-durability"));
		}
		map.put("itemstack.material", item.getType().name());
		map.put("itemstack.durability", item.getDurability());
		//map.put("itemstack.amount", item.getAmount());
		map.put("itemstack.extra", toBase64(item));
		return map;
	}
	
	public static Map<String, Object> serialize(ConfigurationSection section) {
		Map<String, Object> map = new HashMap<>();
		if(section.contains("id", false)) {
			map.put("id", section.getString("id"));
		}
		if(section.contains("name")) {
			map.put("name", section.getString("name"));
		}
		if(section.contains("rarity")) {
			map.put("rarity", section.getString("rarity"));
		}
		if(section.contains("type")) {
			map.put("type", section.getString("type"));
		}
		if(section.contains("ability")) {
			map.put("ability", section.getString("ability"));
		}
		if(section.contains("enchantable")) {
			map.put("enchantable", section.getBoolean("enchantable"));
		}
		if(section.contains("strength")) {
			map.put("strength", section.getDouble("strength"));
		}
		if(section.contains("damage")) {
			map.put("damage", section.getDouble("damage"));
		}
		if(section.contains("intelligence")) {
			map.put("intelligence", section.getDouble("intelligence")); 
		}
		if(section.contains("luck")) {
			map.put("luck", section.getDouble("luck"));
		}
		if(section.contains("defense")) {
			map.put("defense", section.getDouble("defense"));
		}
		if(section.contains("healthRegen")) {
			map.put("healthRegen", section.getDouble("healthRegen"));
		}
		if(section.contains("manaRegen")) {
			map.put("manaRegen", section.getDouble("manaRegen"));
		}
		if(section.contains("max-durability")) {
			map.put("max-durability", section.getInt("max-durability"));
		}
		if(section.contains("itemstack.material")) {
			map.put("itemstack.material", section.getString("itemstack.material").toUpperCase());
		}
		if(section.contains("itemstack.durability")) {
			map.put("itemstack.durability", (short)section.getInt("itemstack.durability"));
		}
		if(section.contains("itemstack.extra")) {
			map.put("itemstack.extra", section.getString("itemstack.extra"));
		}
		return map;
	}
	
	public static ItemStack deserialize(Map<String, Object> map, Player player, boolean isDefaultItem) {
		ItemStack result = null;
		if(map.containsKey("itemstack.extra")) {
			result = ItemBuilder.fromBase64((String) map.get("itemstack.extra"));
			result.setType(Material.valueOf(((String)map.get("itemstack.material")).toUpperCase()));
			if(map.containsKey("amount")) {
				result.setAmount((int)map.get("itemstack.amount"));
			}
			result.setDurability((short)((int)map.get("itemstack.durability")));
		} else {
			Material material = Material.valueOf(((String)map.get("itemstack.material")).toUpperCase());
			int amount = map.containsKey("amount") ? (int)map.get("itemstack.amount") : 1;
			short durability = (short)map.get("itemstack.durability");
			result = new ItemStack(material, amount, durability);
		}
		
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(result);
		NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		NBTTagCompound customTag = tag.getCompound("khaly");
		
		serializeToNBTBase(map).forEach((str, base) -> {
			if(str.startsWith("itemstack."))return;
			if(!ItemUpdater.isValidKey(str))return;
			customTag.set(str, base);
		});
		if(isDefaultItem) {
			customTag.setBoolean("default", isDefaultItem);
		}
		tag.set("khaly", customTag);
		nms.setTag(tag);
		ItemStack configured = CraftItemStack.asBukkitCopy(nms);
		ItemMeta meta = configured.getItemMeta();
		Rarity rarity = Rarity.valueOf(customTag.getString("rarity"));
		meta.setDisplayName(rarity.getColor()+customTag.getString("name"));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS);
		meta.setUnbreakable(true);
		meta.setLore(ItemUtils.buildLore(tag, player));
		configured.setItemMeta(meta);
		return configured;
	}
	
	public static Map<String, NBTBase> serializeToNBTBase(Map<String, Object> objectMap) {
		Map<String, NBTBase> nbtMap = new HashMap<>();
		
		objectMap.forEach((str, obj) -> {
			if(obj instanceof String) {
				nbtMap.put(str, new NBTTagString((String) obj));
			} else if(obj instanceof Integer) {
				nbtMap.put(str, new NBTTagInt((Integer) obj));
			} else if(obj instanceof Double) {
				nbtMap.put(str, new NBTTagDouble((Double) obj));
			} else if(obj instanceof Float) {
				nbtMap.put(str, new NBTTagFloat((Float) obj));
			} else if(obj instanceof Short) {
				nbtMap.put(str, new NBTTagShort((Short) obj));
			}
		});
		
		return nbtMap;
	}
	
	private static String toBase64(Object object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeObject(object);
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save object", e);
		}
	}
}