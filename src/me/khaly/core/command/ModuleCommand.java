package me.khaly.core.command;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.khaly.core.KhalyCore;
import me.khaly.core.module.LocalModuleManager;
import me.khaly.core.module.Module;
import me.khaly.core.util.Futures;

@Command(names = {
		"module",
		"modules"
}, permission = "khaly.module")
public class ModuleCommand implements CommandClass {

	private KhalyCore core;

	public ModuleCommand(KhalyCore core) {
		this.core = core;
	}

	@Command(names = "")
	public void mainCommand(CommandSender sender) {
		sender.sendMessage(new String[] { "    §6Módulos", "", "§7/module §f- §eVer esto.",
				"§7/module reload [all/module] §f- §eRecargar un módulo o todos los módulos.",
				"§7/module register <module> §f- §eRegistrar un módulo.",
				"§7/module unregister <module> §f- §eDesactivar un módulo.",
				"§7/module list §f- §eVer todos los módulos registrados.",
				"§7/module info <module> §f- §eVer la información de un módulo." });
	}

	@Command(names = "reload")
	public void reloadCommand(CommandSender sender, @OptArg("all") String arg) {
		LocalModuleManager manager = core.getLocalModuleManager();

		if (arg.equals("all")) {
			manager.kill();
			manager.load(sender);
		} else {
			Module module = manager.getModule(arg);

			if (module == null) {
				sender.sendMessage("§cEl modulo que especificaste no se ha encontrado.");
				return;
			}

			if (module.isPersistent()) {
				sender.sendMessage("&cUn módulo persistente no se puede recargar.");
				return;
			}

			if (module.unregister()) {
				module.register();
				sender.sendMessage("§aSe ha recargado el módulo " + module.getUniqueId() + " correctamente.");
			} else {
				sender.sendMessage("§cEl módulo no se ha podido recargar.");
			}
		}
	}

	@Command(names = "register")
	public void registerCommand(CommandSender sender, @OptArg() String arg) {
		if (arg == null || arg.isEmpty()) {
			sender.sendMessage("§cDebes especificar el nombre de archivo del módulo.");
			return;
		}

		LocalModuleManager manager = core.getLocalModuleManager();
		File file = new File(manager.getModulesFolder(), arg);

		if (!file.exists()) {
			sender.sendMessage("§cEl archivo §f" + arg + " §cno existe.");
			return;
		}

		Futures.onMainThread(core, manager.findModuleInFile(file), (clazz, exception) -> {
			if (exception != null) {
				sender.sendMessage("§cAlgo falló intentando encontrar el módulo.");
				return;
			}

			if (clazz == null) {
				sender.sendMessage("§cNo se ha encontrado ninguna clase con la responsabilidad del módulo.");
				return;
			}

			Optional<Module> module = manager.register(clazz);
			if (!module.isPresent()) {
				sender.sendMessage("§cNo se ha encontrado el modulo§f: " + arg);
				return;
			}

			sender.sendMessage("§aEl modulo se ha registrado correctamente.");
		});
	}

	@Command(names = {
			"unregister",
			"disable"
	})
	public void unregisterCommand(CommandSender sender, @OptArg() String arg) {
		if (arg == null || arg.isEmpty()) {
			sender.sendMessage("§cDebes especificar la id del módulo.");
			return;
		}

		Optional<Module> module = core.getLocalModuleManager().findModuleByName(arg);
		if (!module.isPresent()) {
			sender.sendMessage("§cNo se ha encontrado módulo cargado con la id: §f" + arg);
			return;
		}

		final String message = !module.get().unregister() ? "§cAlgo falló mientras se desactivaba el módulo: §f"
				: "§aSe ha desactivado el módulo: §f";

		sender.sendMessage(message + module.get().getName());
	}

	@Command(names = "list")
	public void listCommand(CommandSender sender) {
		Set<String> modules = core.getRegistreredModules();
		if (modules.isEmpty()) {
			sender.sendMessage("§aNo hay ningún módulo activo.");
			return;
		}

		List<List<String>> partitions = Lists.partition(modules.stream().sorted().collect(Collectors.toList()), 10);
		sender.sendMessage("§7Un total de §a" + modules.size() + " §7módulos activos:");
		sender.sendMessage(partitions.stream().map(partition -> String.join("§7, §a", partition))
            .collect(Collectors.joining("\n")));

	}
	
	@Command(names = {
			"info",
			"information",
			"información",
			"informacion"
	})
	public void infoCommand(CommandSender sender, @OptArg() String arg) {
		if (arg == null || arg.isEmpty()) {
			sender.sendMessage("§cDebes especificar la id del módulo.");
			return;
		}
		
		Module module = core.getLocalModuleManager().findModuleById(arg).orElse(null);
		
		if(module == null) {
			sender.sendMessage("§cEse módulo no ha sido encontrado.");
			return;
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("§7Información del módulo: §r")
		       .append(module.getName())
		       .append("þ")
		       .append("§7Estado: §aActivo")
		       .append("þ");
		
		String author = module.getAuthor();
		if(author != null) {
			builder.append("&7Autor: §r")
			       .append(author)
			       .append("þ");
		}
		
		float version = module.getVersion();
		builder.append("§7Versión: §rv" + version);
		
		String[] message = builder.toString().split("þ");
		sender.sendMessage(message);
	}

}
