package me.khaly.core.libraries;

import me.khaly.core.KhalyCore;

public class Files {
	private YamlFile messages;
	private YamlFile recipes;
	private YamlFile config;
	private YamlFile heroes;
	private YamlFile defaultItems;
	private YamlFile items;
	
	public Files(KhalyCore core) {
		this.messages = new YamlFile(core, "messages");
		this.recipes = new YamlFile(core, "recipes");
		this.config = new YamlFile(core, "config");
		this.heroes = new YamlFile(core, "heroes");
		this.defaultItems = new YamlFile(core, "default-items");
		this.items = new YamlFile(core, "items");
	}
	
	public void reload() {
		messages.reload();
		recipes.reload();
		config.reload();
		heroes.reload();
		defaultItems.reload();
		items.reload();
	}
	
	public YamlFile getMessages() {
		return messages;
	}
	
	public YamlFile getRecipes() {
		return recipes;
	}

	public YamlFile getConfig() {
		return config;
	}
	
	public YamlFile getHeroes() {
		return heroes;
	}
	
	public YamlFile getDefaultItems() {
		return defaultItems;
	}
	
	public YamlFile getItems() {
		return items;
	}
}
