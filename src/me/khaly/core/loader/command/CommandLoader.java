package me.khaly.core.loader.command;

import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.khaly.core.KhalyCore;
import me.khaly.core.command.DeveloperCommand;
import me.khaly.core.command.ModuleCommand;
import me.khaly.core.loader.Loader;

public class CommandLoader implements Loader {
	
	private KhalyCore core;
	
	
	public CommandLoader(KhalyCore core) {
		this.core = core;
	}
	
	@Override
	public void load() {
		registerCommands(
				new DeveloperCommand(core),
				new ModuleCommand(core)
				);
	}
	
	private void registerCommands(CommandClass... commands) {
		BukkitCommandManager commandManager = core.getBukkitCommandManager();
		AnnotatedCommandTreeBuilder commandBuilder = core.getAnnotatedCommandTreeBuilder();
		
		for(CommandClass command : commands) {
			commandManager.registerCommands(commandBuilder.fromClass(command));
		}
	}

}
