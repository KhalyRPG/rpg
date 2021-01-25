package me.atog.anr.plugin.object;

import org.bukkit.Material;

import me.atog.anr.plugin.types.ItemAbility;
import me.atog.anr.plugin.types.ItemAbilityAction;
import me.atog.anr.plugin.types.Phase;
import me.atog.anr.plugin.types.Rarity;

public class CustomItemDataConstructor {
	private Phase phase;
	private Material mat;
	private short data;
	private String displayName;
	private String description;
	private int damage;
	private int strength;
	private int speed;
	private boolean is;
	private ItemAbility ability;
	private ItemAbilityAction action;
	private Rarity rarity;
	
	public CustomItemDataConstructor(Phase phase) {
		this.phase = phase;
		this.is = false;
	}
	
	public String getName() {
		return displayName;
	}
	public short getData() {
		return this.data;
	}
	public void setName(String changeString) {
		this.displayName = changeString;
	}
	public Material getMaterial() {
		return mat;
	}
	public boolean isEnchanted() {
		return is;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String x) {
		this.description = x;
	}
	public int getDamage() {
		return damage;
	}
	public int getStrength() {
		return strength;
	}
	public int getSpeed() {
		return speed;
	}
	public ItemAbility getAbility() {
		return this.ability;
	}
	public ItemAbilityAction getAction() {
		return action;
	}
	public Rarity getRarity() {
		return rarity;
	}
	public void setData(int x) {
		this.data = (short)x;
	}
	public void setData(short x) {
		this.data = x;
	}
	public void setDamage(int x) {
		this.damage = x;
	}
	public void setStrength(int x) {
		this.strength = x;
	}
	public void setSpeed(int x) {
		this.speed = x;
	}
	public void setAbility(ItemAbility a) {
		this.ability = a;
	}
	public void enchant(boolean bool) {
		this.is = bool;
	}
	public void setAbilityAction(ItemAbilityAction a) {
		this.action = a;
	}
	public void setRarity(Rarity r) {
		this.rarity = r;
	}
	public void setMaterial(Material mat) {
		this.mat = mat;
	}
	public Phase getPhase() {
		return phase;
	}
	public void changePhase(Phase toChange) {
		this.phase = toChange;
	}
}
