package me.khaly.core.loader.command;

import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.khaly.core.KhalyCore;
import me.khaly.core.command.DeveloperCommand;
import me.khaly.core.command.ModuleCommand;
import me.khaly.core.loader.Loader;

public class CommandLoader implements Loader {
	
	private KhalyCore core;
	private AnnotatedCommandTreeBuilder builder;
	private BukkitCommandManager commandManager;
	
	public CommandLoader(KhalyCore core) {
		this.core = core;
		commandManager = new BukkitCommandManager("Core");
		PartInjector injector = PartInjector.create();
		injector.install(new DefaultsModule());
		injector.install(new BukkitModule());
		builder = new AnnotatedCommandTreeBuilderImpl(injector);
	}
	
	@Override
	public void load() {
		registerCommands(
				new DeveloperCommand(core),
				new ModuleCommand(core)
				);
	}
	
	private void registerCommands(CommandClass... commands) {
		for(CommandClass command : commands) {
			commandManager.registerCommands(builder.fromClass(command));
		}
	}

}
