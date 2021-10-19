package me.khaly.core.creator;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import me.khaly.core.creator.object.Creator;
import me.khaly.core.enums.ItemType;
import me.khaly.core.enums.Rarity;
import me.khaly.core.enums.Requirement;
import me.khaly.core.util.ItemUtils;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;

public class ItemCreator implements Creator {
	private ItemStack item;
	
	private NBTTagList requirements;
	private boolean enchanted;
	private boolean creationDate;
	
	protected ItemType type;
	protected NBTTagCompound customTag;
	
	public ItemCreator(ItemStack item, String displayName) {
		this.item = item;
		this.creationDate = true;
		this.requirements = new NBTTagList();
		this.customTag = new NBTTagCompound();
		customTag.setString("rarity", Rarity.COMMON.name());
		customTag.setString("name", displayName);
		this.type = ItemType.OTHER;
		customTag.setString("type", ItemType.OTHER.name());
		/*if(item.getItemMeta() instanceof Damageable) {
			Damageable meta = (Damageable)item.getItemMeta();
			customTag.setInt("max-durability", (int) meta.getMaxHealth());
			customTag.setInt("durability", (int) meta.getMaxHealth());
		}*/
	}
	
		
	public ItemCreator(Material mat, int amount, short data, String displayName) {
		this(new ItemStack(mat, amount, data), displayName);
	}
	
	public ItemCreator(Material mat, int amount, String displayName) {
		this(mat, amount, (short)0, displayName);
	}
	
	public ItemCreator(Material mat, short data, String displayName) {
		this(mat, 1, data, displayName);
	}
	
	public ItemCreator(Material mat, String displayName) {
		this(mat, 1, (short)0, displayName);
	}
	
	
	public ItemCreator setMaxDurability(int durability) {
		customTag.setInt("max-durability", durability);
		customTag.setInt("durability", durability);
		return this;
	}
	
	public ItemCreator setAmount(int amount) {
		this.item.setAmount(amount);
		return this;
	}
	
	public ItemCreator setDescription(String description) {
		if(description != null)customTag.setString("description", description);
		return this;
	}
	
	public ItemCreator setRarity(Rarity rarity) {
		customTag.setString("rarity", rarity.name());
		return this;
	}
	
	public ItemCreator setType(ItemType type) {
		customTag.setString("type", type.name());
		this.type = type;
		return this;
	}
	
	public ItemCreator setEnchantable(boolean enchantable) {
		customTag.setBoolean("enchantable", enchantable);
		return this;
	}
	
	public ItemCreator setTradeable(boolean tradeable) {
		customTag.setBoolean("tradeable", tradeable);
		return this;
	}
	
	public ItemCreator setQuestItem(boolean questItem) {
		customTag.setBoolean("quest-item", questItem);
		return this;
	}
	
	public ItemCreator setKeepEnchants(boolean keepEnchants) {
		customTag.setBoolean("keepEnchants", keepEnchants);
		return this;
	}
	
	public ItemCreator setId(String id) {
		customTag.setString("id", id);
		return this;
	}
	
	protected ItemCreator configureType(ItemType type) {
		customTag.setString("type", type.name());
		this.type = type;
		return this;
	}
		
	public ItemCreator addRequirement(@NotNull Requirement req, int amount) {
		NBTTagCompound comp = new NBTTagCompound();
		comp.setString("type", req.name());
		comp.setInt("level", amount);
		this.requirements.add(comp);
		customTag.set("requirements", this.requirements);
		return this;
	}

	public ItemCreator setEnchanted(boolean enchanted) {
		this.enchanted = enchanted;
		return this;
	}
	
	public ItemCreator setCreationDate(boolean create) {
		this.creationDate = create;
		return this;
	}
	
	public ItemCreator setCustomTagValue(String key, NBTBase base) {
		this.customTag.set(key, base);
		return this;
	}
	
	public ItemCreator setCustomTagValue(String key, String str) {
		this.customTag.setString(key, str);
		return this;
	}
	
	public ItemCreator setCustomTagValue(String key, int integer) {
		this.customTag.setInt(key, integer);
		return this;
	}
	
	public String getTagString(String key) {
		return this.customTag.getString(key);
	}
	
	public int getTagInt(String key) {
		return this.customTag.getInt(key);
	}
	
	public double getTagDouble(String key) {
		return this.customTag.getDouble(key);
	}
	
	protected ItemStack getItemStack() {
		return item;
	}
	
	public ItemType getType() {
		return this.type;
	}
	
	public Rarity getRarity() {
		return Rarity.valueOf(customTag.getString("rarity"));
	}
	
	public ItemCreator setMaterial(Material material) {
		this.item.setType(material);
		return this;
	}
	
	public ItemCreator setMaterial(Material material, short data) {
		this.setMaterial(material);
		this.item.setDurability(data);
		return this;
	}
	
	
	protected ItemStack build(Player player) {
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = nms.hasTag() ? nms.getTag() : new NBTTagCompound();
		if(creationDate) {
			customTag.setLong("createdAt", System.currentTimeMillis());
		}
		tag.set("khaly", customTag);
		nms.setTag(tag);
		ItemStack parsedItem = CraftItemStack.asBukkitCopy(nms);
		ItemMeta parsedItemMeta = parsedItem.getItemMeta();
		parsedItemMeta.setLore(ItemUtils.buildLore(tag, player));
		parsedItemMeta.setUnbreakable(true);
		Rarity rarity = Rarity.valueOf(customTag.getString("rarity"));
		parsedItemMeta.setDisplayName(rarity.getColor() + customTag.getString("name"));
		if(enchanted) {
			parsedItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		}
		parsedItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS);
		parsedItem.setItemMeta(parsedItemMeta);
		return parsedItem;
	}
	
	public void give(Player player) {
		player.getInventory().addItem(buildItem(player));
	}
	
	public ItemStack buildItem(Player player) {
		return build(player);
	}
}
