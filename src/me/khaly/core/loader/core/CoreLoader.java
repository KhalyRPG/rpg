package me.khaly.core.loader.core;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.khaly.core.KhalyCore;
import me.khaly.core.loader.Loader;
import me.khaly.core.loader.attributes.AttributeLoader;
import me.khaly.core.loader.command.CommandLoader;
import me.khaly.core.loader.gui.GuiLoader;
import me.khaly.core.loader.listener.ListenerLoader;
import me.khaly.core.loader.task.TaskLoader;
import me.khaly.core.user.User;
import me.khaly.core.loader.classes.ClassLoader;

public class CoreLoader implements Loader {
	
	private KhalyCore main;
	
	public CoreLoader(KhalyCore main) {
		this.main = main;
	}
	
	@Override
	public void load() {
		loadLoaders(
				new CommandLoader(main),
				new AttributeLoader(main),
				new ListenerLoader(main),
				new ClassLoader(),
				new GuiLoader(main),
				new TaskLoader(main)
				);
		
		main.getLocalModuleManager().load(Bukkit.getConsoleSender());
	}
	
	@Override
	public void unload() {
		for(Map.Entry<UUID, User> entry : main.getServices().getCache().getUsers().entrySet()) {
			User user = entry.getValue();
			if(user.hasProfile()) {
				user.getProfile().save();
			}
		}
		
		main.getLocalModuleManager().kill();
	}
	
	private void loadLoaders(Loader...loaders) {
		for(Loader loader : loaders) {
			loader.load();
		}
	}
	
}
