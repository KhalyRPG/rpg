package me.khaly.core.loader.attributes;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import me.khaly.core.KhalyCore;
import me.khaly.core.attributes.Damage;
import me.khaly.core.attributes.Defense;
import me.khaly.core.attributes.object.Attribute;
import me.khaly.core.loader.Loader;

public class AttributeLoader implements Loader {
	
	private KhalyCore main;
	
	public AttributeLoader(KhalyCore main) {
		this.main = main;
	}
	
	@Override
	public void load() {
		registerAttributes(
				new Damage(main),
				new Defense(main)
				);
	}
	
	private void registerAttributes(Attribute... attributes) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		for(Attribute attribute : attributes) {
			pluginManager.registerEvents(attribute, main);
		}
	}
	
}
