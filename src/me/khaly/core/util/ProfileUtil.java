package me.khaly.core.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import me.khaly.core.KhalyCore;
import me.khaly.core.libraries.YamlFile;
import me.khaly.core.misc.Inventories;
import me.khaly.core.rpg.classes.object.RPGClass;
import me.khaly.core.user.User;
import me.khaly.core.user.profile.Profile;

public class ProfileUtil {
	private final List<String> names;
	private KhalyCore core;
	
	public ProfileUtil(KhalyCore core) {
		this.core = core;
		names = new ArrayList<>();
		names.add("Water");
		names.add("Minecraft");
		names.add("Wheat");
		names.add("World");
		names.add("Tomato");
		names.add("Popcorn");
		names.add("Hamburguer");
		names.add("Hotdog");
		names.add("k-Haly");
		names.add("Money");
		names.add("Bread");
		names.add("Potato");
		names.add("Red");
		names.add("Carrot");
		names.add("Broccoli");
		names.add("Corn");
		names.add("Cucumber");
		names.add("Eggplant");
		names.add("Onion");
		names.add("Pumpkin");
		names.add("Beetroot");
		names.add("Peas");
		names.add("Zucchini");
		names.add("Radish");
		names.add("Leek");
		names.add("Cabbage");
		names.add("Celery");
		names.add("Chili");
		names.add("Garlic");
		names.add("Basil");
		names.add("Coriander");
		names.add("Bean");
		names.add("Lentil");
	}
	
	private String generateName() {
		int index = (int)Math.floor(Math.random() * names.size());
	    return names.get(index) == null ? names.get(0) : names.get(index);
	}
	
	public boolean hasProfile(Player player) {
		return KhalyCore.getInstance().getUser(player).getProfile() != null;
	}
	
	public Profile createProfile(User user, RPGClass clazz) {
		String name = generateName();
		YamlFile file = user.getFile();
		boolean search = true;
		while(search) {
			if(file.contains("profiles."+name)) {
				name = generateName();
			} else {
				search = false;	
			}
		}
		
		Inventories inv = new Inventories(core, user.getBukkitPlayer());
		PlayerInventory emptyInventory = user.getBukkitPlayer().getInventory();
		emptyInventory.clear();
		ConfigurationSection section = file.createSection("profiles."+name);
		section.set("createdAt", System.currentTimeMillis());
		section.set("class", clazz.getID());
		section.set("xp", 0);
		section.set("inventory", inv.toBase64(emptyInventory));
		file.save();
		Profile profile = parseProfile(user, name);
		user.setProfile(name);
		user.updateProfiles();
		return profile;
	}
	
	public Profile parseProfile(User user, String name) {
		Profile profile = new Profile(user, name);
		return profile;
	}
}
