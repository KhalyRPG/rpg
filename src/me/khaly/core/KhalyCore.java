package me.khaly.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableSet;

import me.khaly.core.libraries.Services;
import me.khaly.core.loader.core.CoreLoader;
import me.khaly.core.module.LocalModuleManager;
import me.khaly.core.user.User;

public class KhalyCore extends JavaPlugin {

	private CoreLoader loader;
	private static KhalyCore instance;
	private Services services;
	private LocalModuleManager localModuleManager;
	private boolean locked;

	@Override
	public void onLoad() {
		loader = new CoreLoader(this);
		instance = this;
		this.services = new Services(this);
		this.localModuleManager = new LocalModuleManager(this);
	}

	@Override
	public void onEnable() {
		loader.load();
	}

	@Override
	public void onDisable() {
		loader.unload();
	}

	public static KhalyCore getInstance() {
		return instance;
	}

	public Services getServices() {
		return services;
	}

	public LocalModuleManager getLocalModuleManager() {
		return localModuleManager;
	}

	public Set<String> getRegistreredModules() {
		return ImmutableSet.copyOf(localModuleManager.getIdentifiers());
	}

	public User getUser(Player player) {
		Map<UUID, User> cache = getServices().getCache().getUsers();
		return cache.get(player.getUniqueId());
	}

	public boolean isLocked() {
		return locked;
	}

	public void lockServer(boolean locked) {
		this.locked = locked;
	}

	public String formatValue(double value) {
		int power;
		String suffix = " kmbt";
		String formattedNumber = "";

		NumberFormat formatter = new DecimalFormat("#,###.#");
		power = (int) StrictMath.log10(value);
		value = value / (Math.pow(10, (power / 3) * 3));
		formattedNumber = formatter.format(value);
		formattedNumber = formattedNumber + suffix.charAt(power / 3);
		return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
	}

}
