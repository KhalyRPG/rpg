package me.khaly.core.user.profile;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import me.khaly.core.KhalyCore;
import me.khaly.core.api.events.UserReceiveExperienceEvent;
import me.khaly.core.enums.ExperienceCause;
import me.khaly.core.libraries.YamlFile;
import me.khaly.core.misc.Classes;
import me.khaly.core.misc.Inventories;
import me.khaly.core.rpg.classes.object.RPGClass;
import me.khaly.core.user.User;

public class Profile {
	private User user;
	private String name;
	private RPGClass rpgClass;
	private Inventories inv;
	
	private double exp;
	
	public Profile(User user, String name) {
		this.user = user;
		this.name = name;
		this.rpgClass = Classes.getRPGClass(user.getFile().getString("profiles."+name+".class"));
		this.inv = new Inventories(KhalyCore.getInstance(), user.getBukkitPlayer());
		this.exp = user.getFile().getDouble("profiles."+name+".xp", 0);
	}
	
	public RPGClass getRPGClass() {
		return rpgClass;
	}
	
	public double getExp() {
		return exp;
	}
	
	public void setExp(double amount, ExperienceCause cause) {
		this.exp = amount;
		
		if(cause == null) {
			cause = ExperienceCause.UNKNOWN;
		}
		
		UserReceiveExperienceEvent event = new UserReceiveExperienceEvent(this.user, amount, cause);
		
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public void addExp(double amount, ExperienceCause cause) {
		this.exp += amount;
		
		if(cause == null) {
			cause = ExperienceCause.UNKNOWN;
		}
		
		UserReceiveExperienceEvent event = new UserReceiveExperienceEvent(this.user, amount, cause);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public int getLevel() {
		return Classes.getLevelFormExp((int)getExp());
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
		
	public ConfigurationSection getConfigurationSection() {
		return user.getFile().getConfigurationSection("profiles." + name);
	}
	
	public void load() {
		inv.loadInventory(getConfigurationSection().getString("inventory"));
		exp = getConfigurationSection().getDouble("xp");
	}
	
	public YamlFile getFile() {
		return user.getFile();
	}
	
	public void save() {
		getConfigurationSection().set("xp", exp);
		getConfigurationSection().set("inventory", inv.toBase64(user.getBukkitPlayer().getInventory()));
		getFile().save();
	}
	
	public User getOwner() {
		return user;
	}
}