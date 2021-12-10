package me.khaly.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.khaly.core.KhalyCore;
import me.khaly.core.enchantment.Enchant;
import me.khaly.core.enchantment.ItemEnchantment;
import me.khaly.core.enums.ItemCooldown;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.PassiveType;
import me.khaly.core.enums.Rarity;
import me.khaly.core.enums.Requirement;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.enums.Symbols;
import me.khaly.core.passive.object.ItemPassive;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;

public class ItemUtils extends Util {
	
	@SuppressWarnings("unused")
	public static List<String> buildLore(NBTTagCompound tag, Player player) {
		KhalyCore core = KhalyCore.getInstance();
		List<String> lore = new ArrayList<>();
		NBTTagCompound data = tag.getCompound("khaly");
		if(data.hasKey("cooldown")) {
			lore.add(TextUtil.color("&8"+ItemCooldown.valueOf(data.getString("cooldown")).getDisplayName()));
		}
		lore.add(" ");
		if(hasKeysStr(data, "rarity", "type", "description", "ability")) {
			lore.add(TextUtil.color("&8&m               &f 「 INFO 」 &8&m                 "));
			
			if(data.hasKey("rarity")) {
				Rarity rarity = Rarity.valueOf(data.getString("rarity"));
				lore.add(TextUtil.color("&f» &7Rareza: "+rarity.getColor()+rarity.getName()));
			}
			if(data.hasKey("type")) {
				ItemType type = ItemType.valueOf(data.getString("type"));
				lore.add(TextUtil.color("&f» &7Tipo: &b"+type.getDisplayName()));
			}
			
			if(data.hasKey("ability") && core.getServices().getPassives().existsPassive(data.getString("ability")) && false) {
				ItemPassive itemPassive = core.getServices().getPassives().getPassive(data.getString("ability"));
				
				PassiveType action = itemPassive.getPassiveType();
				int manaCost = itemPassive.getManaCost();
				int cooldown = itemPassive.getCooldown();
				String name = itemPassive.getDisplayName();
				//String actionString = action == null ? "" : "&8[&6"+action.getChar()+"&8]";
				
				//lore.add(TextUtil.color("&f» &7Habilidad: &c" + name + " &8[&b" + manaCost + "✎&8]" + actionString + " &8"+cooldown+"s"));
			}
			
			if(data.hasKey("description")) {
				lore.add(" ");
				lore.add(TextUtil.color("&7"+data.getString("description")));
			}
		}
		NBTTagList req = (NBTTagList) data.get("requirements");
		if(data.hasKey("requirements") && !req.isEmpty() /*&& req instanceof NBTTagList*/) {
			lore.add(TextUtil.color("&8&m             &f 「 REQUISITOS 」 &8&m           "));
			for(int i = 0; i < req.size(); i++) {
				NBTTagCompound comp = req.get(i);
				if(!comp.getString("type").isEmpty()) {
					Requirement requirement = Requirement.valueOf(comp.getString("type"));
					int amount = comp.getInt("level");
					String text = Requirement.requirementText(requirement, amount);
					if(Requirement.hasRequirement(core.getUser(player), requirement, amount))
					lore.add(TextUtil.color("&a"+Symbols.CHECK_MARK+" "+text));
					else lore.add(TextUtil.color("&c"+Symbols.X+" "+text));
				}
			}
			/*for(int i = 0;i<(req.size() - 1);i++) {
				NBTTagCompound comp = req.get(i);
				lore.add(Util.color("&cRequires "+Requirement.getByID(comp.getString("type")).getDisplayName()+" nivel "+comp.getInt("level")));
			}*/
		}
		
		NBTTagList enchants = (NBTTagList) data.get("enchantments");
		if(data.hasKey("enchantments") && !enchants.isEmpty()) {
			lore.add(TextUtil.color("&8&m          &f 「 ENCHANTMENTS 」 &8&m          "));
			
			List<String> enchantments = new ArrayList<>();
			String split = "\n";
			
			for(Enchant enchant : EnchantmentUtil.readEnchantments(enchants)) {
				ItemEnchantment enchantment = enchant.getEnchantment();
				if(enchantment == null) {
					continue;
				}
				
				int level = enchant.getLevel();
				
				enchantments.add("§9" + enchantment.getDisplayName() + " " + level);
			}
			StringBuilder build = new StringBuilder();
			enchantments.forEach(ench -> {
				if(!build.toString().isEmpty()) {
					build.append("§f, ");
				}
				
				build.append(ench);
			});
			
			lore.add(build.toString());
		}
		
		if(hasKeysNum(data, "strength", "damage", "defense", "max-durability", "health", "intelligence", "cursed-energy", "melee-damage", "ranged-damage")) {
			lore.add(TextUtil.color("&8&m               &f 「 STATS 」 &8&m               "));
			
			if(hasKey(data, "damage")) {
				double damage = data.getDouble("damage");
				lore.add("§c"+Symbols.DAMAGE+" Daño: "+checkPlus(damage)+(int)damage);
			}
			
			if(hasKey(data, "cursed-energy")) {
				double energy = data.getDouble("cursed-energy");
				lore.add("§d"+Symbols.CURSED_ENERGY+" Energía maldita: "+checkPlus(energy)+(int)energy);
			}
			
			if(hasKey(data, "health")) {
				int health = (int)data.getDouble("health");
				lore.add(TextUtil.color("&c"+Symbols.HEALTH+" Vida: "+checkPlus(health)+health));
			}
			
			if(hasKey(data, "strength")) {
				int strength = (int)data.getDouble("strength");
				lore.add(TextUtil.color("&c"+Symbols.STRENGTH+" Fuerza: "+checkPlus(strength)+strength));
			}
			
			if(hasKey(data, "intelligence")) {
				int intelligence = (int)data.getDouble("intelligence");
				lore.add(TextUtil.color("&b"+Symbols.INTELLIGENCE+" Inteligencia: "+checkPlus(intelligence)+intelligence));
			}
			
			if(hasKey(data, "speed")) {
				double speed = data.getDouble("speed");
				lore.add(TextUtil.color("&f"+Symbols.SPEED+" Velocidad: "+checkPlus(speed)+speed));
			}
			
			if(hasKey(data, "defense")) {
				int defense = (int)data.getDouble("defense");
				lore.add(TextUtil.color("&a"+Symbols.DEFENSE+" Defensa: "+checkPlus(defense)+defense));
			}
			
			if(hasKey(data, "luck")) {
				int luck = (int)data.getDouble("luck");
				lore.add(TextUtil.color("&a"+Symbols.LUCK+" Suerte: "+checkPlus(luck)+luck));
			}
			
			if(hasKey(data, "melee-damage")) {
				double melee = data.getDouble("melee-damage");
				lore.add(TextUtil.color("&c" + Symbols.DAMAGE + " Daño melee: "+checkPlus(melee) + melee + "%"));
			}
			
			if(hasKey(data, "ranged-damage")) {
				double ranged = data.getDouble("ranged-damage");
				lore.add(TextUtil.color("&c" + Symbols.DAMAGE + " Daño a distancia: " + checkPlus(ranged) + ranged + "%"));
			}
			
			if(hasKey(data, "healthRegen")) {
				double regen = data.getDouble("healthRegen");
				lore.add(TextUtil.color("&c"+Symbols.PLUS+" Regeneración de vida: "+checkPlus(regen)+regen+"%"));
			}
			
			if(hasKey(data, "manaRegen")) {
				double regen = data.getDouble("manaRegen");
				lore.add(TextUtil.color("&b"+Symbols.MANA+" Regeneración de maná: "+checkPlus(regen)+regen));
			}
						
			if(hasKeysNum(data, "durability", "max-durability") && data.getInt("max-durability") > 0) {
				lore.add(" ");
				lore.add(TextUtil.color("&f◆ &7&oDurabilidad: &f"+data.getInt("durability")+"&7&o/&r&f"+data.getInt("max-durability")));
			}
		}
		
		/*if(data.hasKey("orb")) {
			Orb orb = Orbs.getOrbByID(data.getString("orb"));
			if(orb != null) {
				lore.add(TextUtil.color("&8&m                &f 「 ORBE 」 &8&m               "));
				for(int i = 0; i < orb.getDescription().length;i++) {
					String line = orb.getDescription()[i];
					String conditional = line.startsWith("&7") ? "" : "&7";
					lore.add(TextUtil.color(conditional+line));
				}
			}
		}
		*/
		/*
		if(hasKeys(data, "ability")) {
			lore.add(TextUtil.color("&8&m             &f 「 HABILIDAD 」 &8&m              "));
			NBTTagCompound nbtTag = data.getCompound("ability");
			KhalyItemAbility ability = KhalyItemAbility.valueOf(nbtTag.getString("id"));
			Action action = ability.getAction();
			int manaCost = ability.getManaCost();
			int cooldown = ability.getCooldown();
			StringBuilder charr = new StringBuilder();
			switch(action) {
			case LEFT_CLICK_AIR:
				charr.append("Clic izquierdo");
				break;
			case LEFT_CLICK_BLOCK:
				charr.append("Clic izquierdo");
				break;
			case PHYSICAL:
				charr.append("Clic central");
				break;
			case RIGHT_CLICK_AIR:
				charr.append("Clic derecho");
				break;
			case RIGHT_CLICK_BLOCK:
				charr.append("Clic derecho");
				break;
			}
			lore.add(TextUtil.color("&f» &7Habilidad: &c"+ability.getDisplayName()));
			//&c"+ability.getDisplayName()+" &8[&b"+manaCost+"&8]"
			lore.add(TextUtil.color("&f» &7Costo: &b"+manaCost+" Mana"));
			lore.add(TextUtil.color("&f» &7Activación: &6"+charr.toString()));
			lore.add(TextUtil.color("&f» &7Tiempo de espera: &8"+cooldown+"s"));
		}*/
		
		return lore;
	}
	private static String checkPlus(double number) {
		return number > 0 ? "§f+" : "§c";
	}
	
	private static boolean hasKeysNum(NBTTagCompound tag, String... keys) {
		boolean bool = false;
		for(String str : keys) {
			if(hasKey(tag, str)) {
				bool = true;
				break;
			}
		}
		
		return bool;
	}
	
	private static boolean hasKeysStr(NBTTagCompound tag, String... keys) {
		boolean bool = false;
		for(String str : keys) {
			if(tag.hasKey(str)) {
				bool = true;
				break;
			}
		}
		
		return bool;
	}
	
	private static boolean hasKey(NBTTagCompound tag, String key) {
		return tag.hasKey(key) && (tag.getInt(key) != 0 || tag.getDouble(key) != 0);
	}
	
	public static boolean isType(ItemStack item, ItemType type) {
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		if(!tag.hasKey("khaly")) {
			return false;
		}
		NBTTagCompound data = tag.getCompound("khaly");
		if(data.hasKey("type")) {
			if(ItemType.valueOf(data.getString("type")) == type) {
				return true;
			}
		}
		return false;
	}
	
	public static double getModifier(StatAttribute modifier, ItemStack item) {
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		if(!tag.hasKey("khaly"))return 0;
		NBTTagCompound data = tag.getCompound("khaly");
		return data.getDouble(modifier.toString());
	}
	
	
	public static boolean canUseItem(KhalyCore core, Player player, ItemStack item) {
		if(item == null)return true;
		if(item.getType() == Material.AIR)return true;
		if(!item.hasItemMeta())return true;
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		if(!tag.hasKey("khaly")) {
			return true;
		}
		NBTTagCompound data = tag.getCompound("khaly");
		boolean bool = true;
		NBTTagList req = (NBTTagList) data.get("requirements");
		if(req == null)return true;
		if(req.isEmpty())return true;
		for(int i = 0; i < req.size(); i++) {
			NBTTagCompound comp = req.get(i);
			if(!comp.getString("type").isEmpty()) {
				Requirement requirement = Requirement.valueOf(comp.getString("type"));
				int amount = comp.getInt("level");
				if(!Requirement.hasRequirement(core.getUser(player), requirement, amount)) {
					bool = false;
					break;
				}
			}
		}
		return bool;
		
	}
	
	public static boolean isCustomItem(ItemStack item) {
		if(item == null)return false;
		if(item.getType() == Material.AIR)return false;
		if(!item.hasItemMeta())return false;
		if(!CraftItemStack.asNMSCopy(item).hasTag())return false;
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		return tag.hasKey("khaly");
	}
	
	public static boolean hasAbility(ItemStack item) {
		if(!isCustomItem(item))return false;
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag().getCompound("khaly");
		return tag.hasKey("ability");
	}
	
	public static String getAbility(ItemStack item) {
		if(!hasAbility(item))return null;
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag().getCompound("khaly");
		return tag.getString("ability");
	}
	
	@Deprecated
	public static boolean hasPassive(ItemStack item) {
		if(!isCustomItem(item))return false;
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag().getCompound("khaly");
		return tag.hasKey("passive");
	}
	
	public static double getArmorAmplifiers(Player player, StatAttribute modifier) {
		double amplifier = 0;
		for(ItemStack item : player.getInventory().getArmorContents()) {
			amplifier += ItemUtils.getModifier(modifier, item);
		}
		return amplifier;
	}
	
	public static double getHandAmplifiers(KhalyCore core, Player player, StatAttribute modifier) {
		double amplifier = 0;
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(!ItemUtils.isTypes(hand, ItemType.ARMOR, ItemType.BRACELET, ItemType.COLLAR, ItemType.RING, ItemType.MENU)) {
			if(ItemUtils.canUseItem(core, player, hand))
				amplifier += ItemUtils.getModifier(modifier, hand);
		}
		return amplifier;
	}
	
	public static boolean isTypes(ItemStack item, ItemType... types) {
		return isTypes(item, Arrays.asList(types));
	}
	
	public static boolean isTypes(ItemStack item, List<ItemType> types) {
		boolean a = false;
		for(ItemType type : types) {
			if(isType(item, type)) {
				a = true;
				break;
			}
		}
		
		return a;
	}
	
	public static double getItemAmplifiers(KhalyCore core, Player player, StatAttribute modifier) {
		return (getHandAmplifiers(core, player, modifier) + getArmorAmplifiers(player, modifier));
	}
	
	public static boolean isOrb(ItemStack item) {
		if(!isCustomItem(item))return false;
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag().getCompound("khaly");
		return tag.hasKey("orb");
	}
	/*
	static Orb getOrb(ItemStack item) {
		if(!isOrb(item))return null;
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag().getCompound("khaly");
		return Orbs.getOrbByID(tag.getString("orb"));
	}
	*/
	public static NBTTagCompound getKhalyCompound(ItemStack item) {
		return CraftItemStack.asNMSCopy(item).getTag().getCompound("khaly");
	}
	
	@Deprecated
	public static double getAttribute(ItemStack item, StatAttribute attribute) {
		if(!ItemUtils.isCustomItem(item))return 0;
		return getKhalyCompound(item).getDouble(attribute.getTagID());
	}
	
	public static boolean isMenuItem(ItemStack item) {
		if(item == null)return false;
		if(!isCustomItem(item))return false;
		NBTTagCompound tag = getKhalyCompound(item);
		return tag.hasKey("menuItem");
	}
	
	public static List<Enchant> getEnchantments(ItemStack item) {
		if(!hasEnchantments(item)) {
			return new ArrayList<>();
		}
		
		NBTTagCompound tag = getKhalyCompound(item);
		NBTTagList list = (NBTTagList) tag.get("enchantments");
		List<Enchant> enchants = EnchantmentUtil.readEnchantments(list);
		
		return enchants;
	}
	
	public static boolean hasEnchantments(ItemStack item) {
		if(!isCustomItem(item)) {
			return false;
		}
		
		NBTTagCompound tag = getKhalyCompound(item);
		return tag.hasKey("enchantments") && tag.get("enchantments") instanceof NBTTagList;
	}
	
	public static boolean hasEnchantment(ItemStack item, String id) {
		if(!isCustomItem(item)) {
			return false;
		}
		boolean hasEnchant = false;
		for(Enchant enchant : getEnchantments(item)) {
			if(enchant.getEnchantmentID().equals(id)) {
				hasEnchant = true;
				break;
			}
		}
			
		return hasEnchant;
	}
	
	public static ItemStack addEnchantment(ItemStack item, String id, int level) {
		return addEnchantment(null, item, id, level);
	}
	
	public static ItemStack removeEnchantment(Player player, ItemStack item, String id) {
		if(!isCustomItem(item) || hasEnchantment(item, id) || !hasEnchantments(item)) {
			return item;
		}
		net.minecraft.server.v1_12_R1.ItemStack newItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound mainTag = newItem.getTag();
		NBTTagCompound tag = mainTag.getCompound("khaly");
		NBTTagList enchants = (NBTTagList) tag.get("enchantments");
		
		for(int i = 0; i < enchants.size(); i++) {
			NBTTagCompound enchant = enchants.get(i);
			if(enchant.getString("id").equals(id)) {
				enchants.remove(i);
				break;
			}
		}
		
		tag.set("enchantments", enchants);
		mainTag.set("khaly", tag);
		
		ItemStack finalItem = CraftItemStack.asBukkitCopy(newItem);
		ItemMeta meta = finalItem.getItemMeta();
		
		meta.setLore(buildLore(mainTag, player));
		finalItem.setItemMeta(meta);
		return finalItem;
	}
	
	public static ItemStack addEnchantment(Player player, ItemStack item, String id, int level) {
		if(!isCustomItem(item) || hasEnchantment(item, id)) {
			return item;
		}
		
		net.minecraft.server.v1_12_R1.ItemStack newItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound mainTag = newItem.getTag();
		NBTTagCompound tag = mainTag.getCompound("khaly");
		NBTTagList enchants = new NBTTagList();
				
		if(hasEnchantments(item)) {
			enchants = (NBTTagList) tag.get("enchantments");
		}
		
		ItemEnchantment enchantment = KhalyCore.getInstance().getServices().getEnchantment(id);
		
		if(enchantment == null) {
			return item;
		}
		
		if(level > enchantment.getMaxLevel()) {
			level = enchantment.getMaxLevel();
		} else if(level < 1) {
			level = 1;
		}
		
		NBTTagCompound enchant = new NBTTagCompound();
		enchant.setString("id", id);
		enchant.setInt("level", level);
		
		enchants.add(enchant);
		tag.set("enchantments", enchants);
		mainTag.set("khaly", tag);
		
		ItemStack finalItem = CraftItemStack.asBukkitCopy(newItem);
		ItemMeta meta = finalItem.getItemMeta();
		
		meta.setLore(buildLore(mainTag, player));
		finalItem.setItemMeta(meta);
		return finalItem;
	}
	
	public static enum MaterialDamage {
		WOOD_SPADE(2.5),
		STONE_SPADE(3.5),
		IRON_SPADE(4.5),
		GOLD_SPADE(2.5),
		DIAMOND_SPADE(5.5),
		
		WOOD_PICKAXE(2),
		STONE_PICKAXE(3),
		IRON_PICKAXE(4),
		GOLD_PICKAXE(2),
		DIAMOND_PICKAXE(5),
		
		WOOD_AXE(7),
		STONE_AXE(9),
		IRON_AXE(9),
		GOLD_AXE(7),
		DIAMOND_AXE(9),
		
		WOOD_SWORD(4),
		GOLD_SWORD(4),
		STONE_SWORD(5),
		IRON_SWORD(6),
		DIAMOND_SWORD(7);
		
		private double damage;
		MaterialDamage(double damage) {
			
			this.damage = damage;
		}
		
		public double getDamage() {
			return damage;
		}
	}
}
