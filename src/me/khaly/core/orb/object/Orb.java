package me.khaly.core.orb.object;

import java.util.function.BiConsumer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.khaly.core.KhalyCore;

public abstract class Orb {
	
	private String name,
				   id,
				   particle;
	private String[] description;
	
	private ChatColor color;
	private int percentage,
				duration,
				radius;
	private BiConsumer<KhalyCore, Player> function;
	private ItemStack item;
	
	public Orb(String name, String id, ChatColor color, int percentage, int duration, int radius, BiConsumer<KhalyCore, Player> function, ItemStack item, String particle) {
		this.name = name;
		this.id = id;
		this.color = color;
		this.percentage = percentage;
		this.duration = duration;
		this.radius = radius;
		this.function = function;
		this.item = item;
		this.particle = particle.toUpperCase();
	}

	public String getName() {
		return name;
	}
	
	public String getID() {
		return id;
	}
	
	public ChatColor getColor() {
		return color;
	}

	public int getPercentage() {
		return percentage;
	}

	public int getDuration() {
		return duration;
	}

	public int getRadius() {
		return radius;
	}
	
	public void executeFunction(KhalyCore core, Entity entity) {
		this.function.accept(core, (Player)entity);
	}

	public ItemStack getItem() {
		return item;
	}
	
	public String[] getDescription() {
		return description;
	}
	
	public void setDescription(String[] description) {
		this.description = description;
	}
	
	public String getParticle() {
		return this.particle;
	}
	
}