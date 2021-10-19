package me.khaly.core.rpg.classes.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.khaly.core.util.TextUtil;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public abstract class RPGClass {
	
	private String name;
	private String uuid;
	//private String prefix;
	private String perkText = "";
	private ChatColor color;
	private ItemStack id;
	private int maxLevel;
	private String shortText;
	protected List<String> description;
	
	private double[] health;
	private double[] intelligence;
	private double[] strength;
	private double[] attackDamage;
	private double[] rangedDamage;
	private double manaRegen;
	
	public RPGClass(String uuid,String name, ItemStack id, int maxLevel) {
		this.description = new ArrayList<>();
		
		this.uuid = uuid;
		this.name = name;
		this.id = id;
		this.maxLevel = maxLevel;
		this.color = ChatColor.WHITE;
		double[] doubleDefault = new double[] {0, 0};
		this.health = doubleDefault;
		this.intelligence = doubleDefault;
		this.attackDamage = doubleDefault;
		this.rangedDamage = doubleDefault;
		this.strength = doubleDefault;
	}
	
	protected void setHealth(double base, double scale) {
		this.health = new double[] {
				base, scale
		};
	}
		
	protected void setIntelligence(double base, double scale) {
		this.intelligence = new double[] {
				base, scale
		};
	}
	
	protected void setStrength(double base, double scale) {
		this.strength = new double[] {
				base, scale
		};
	}
	
	protected void setMeleeDamage(double base, double scale) {
		this.attackDamage = new double[] {
				base, scale
		};
	}
	
	protected void setRangedDamage(double base, double scale) {
		this.rangedDamage = new double[] {
				base, scale
		};
	}
	
	public List<String> getDescription() {
		return Collections.unmodifiableList(description);
	}
	
	public double getBaseHealth() {
		return this.health[0];
	}
	
	public double getHealthScale() {
		return this.health[1];
	}
	
	public double getHealth(int level) {
		if(level == 1)return getBaseHealth();
		return getBaseHealth() + (getHealthScale() * level);
	}
		
	public double getBaseIntelligence() {
		return this.intelligence[0];
	}
	
	public double getIntelligenceScale() {
		return this.intelligence[1];
	}
	
	public double getIntelligence(int level) {
		if(level == 1)return getBaseIntelligence();
		return getBaseIntelligence() + (getIntelligenceScale() * level);
	}
	
	public double getBaseStrength() {
		return this.strength[0];
	}
	
	public double getStrengthScale() {
		return this.strength[1];
	}
	
	public double getStrength(int level) {
		if(level == 1)return getBaseStrength();
		return getBaseStrength() + (getStrengthScale() * level);
	}
	
	public double getBaseMeleeDamage() {
		return attackDamage[0];
	}
	
	public double getMeleeDamageScale() {
		return attackDamage[1];
	}
	
	public double getMeleeDamage(int level) {
		if(level == 1) {
			return getBaseMeleeDamage();
		}
		return getBaseMeleeDamage() + (getMeleeDamageScale() * level);
	}
	
	public double getBaseRangedDamage() {
		return rangedDamage[0];
	}
	
	public double getRangedDamageScale() {
		return rangedDamage[1];
	}
	
	public double getRangedDamage(int level) {
		if(level == 1) {
			return getBaseRangedDamage();
		}
		return getBaseRangedDamage() + (getRangedDamageScale() * level);
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getIcon() {
		return id;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public double getManaRegen() {
		return manaRegen;
	}
	
	public String getPrefix() {
		return color+getName();
	}
	
	protected void setManaRegen(double amount) {
		manaRegen = amount;
	}
	
	protected void setColor(ChatColor color) {
		this.color = color;
	}
	
	public String getID() {
		return uuid;
	}
	
	public String getPerkText() {
		return perkText == null ? "" : perkText;
	}
	
	protected void setPerkText(String text) {
		perkText = text;
	}
	
	public boolean hasPerkText() {
		return perkText != null;
	}
	
	public String getShortText() {
		return shortText;
	}
	
	public void setShortText(String text) {
		this.shortText = text;
	}
	
	
	public ItemStack buildItem() {
		ItemStack item = new ItemStack(getIcon()) {{
			ItemMeta meta = getItemMeta();
			meta.setDisplayName(getPrefix());
			List<String> lore = new ArrayList<>();
			lore.add(" ");
			getDescription().forEach(txt -> lore.add(TextUtil.color("&7"+txt)));
			lore.add(TextUtil.color(" "));
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.values());
			setItemMeta(meta);
		}};
		
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		tag.setString("class", getID());
		nms.setTag(tag);
		return CraftItemStack.asBukkitCopy(nms);
	}
	
	
	
}