
package me.atog.anr.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.atog.anr.plugin.command.MainCommand;
import me.atog.anr.plugin.object.CustomItemDataConstructor;
import me.atog.anr.plugin.types.Phase;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.translator.DefaultMapTranslationProvider;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGMain extends JavaPlugin {
	public String ruta;
	private final ConsoleCommandSender console = Bukkit.getConsoleSender();
	private static RPGMain instance;
	private AnnotatedCommandTreeBuilder builder;
	private BukkitCommandManager commandManager;
	private Map<Player, CustomItemDataConstructor> inList = new HashMap<>();
	public void onEnable() {
		instance = this;
		console.sendMessage("Iniciado.");
		registerConfig();
		createCommandManager();
		registerCommands(new TestCMD());
		registerCommands(new MainCommand(this));
		Bukkit.getPluginManager().registerEvents(new MainCommand(this), this);
	}
	
	public void onDisable() {
		console.sendMessage("Acabado.");
	}
	
	private void registerConfig() {
		File config = new File(getDataFolder(), "config.yml");
		ruta = config.getPath();
		if (!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	public static RPGMain getInstance() {
		return instance;
	}
	

	/*@Override
	public void registerClasses(SkillAPI sa) {
		
	}

	@Override
	public void registerSkills(SkillAPI sa) {
		
	}*/
    private void createCommandManager() {
        commandManager = new BukkitCommandManager("MMORPG");
        commandManager.getTranslator().setProvider(new CustomLanguage());
        PartInjector injector = PartInjector.create();
        injector.install(new DefaultsModule());
        injector.install(new BukkitModule());
        builder = new AnnotatedCommandTreeBuilderImpl(injector);
    }
    public Map<Player, CustomItemDataConstructor> getPlayersInEditor() {
    	return this.inList;
    }
    public void registerCommands(CommandClass commandClass) {
        commandManager.registerCommands(builder.fromClass(commandClass));
    }

    private static class TestCMD implements CommandClass {
		@Command(names = "prueba", desc = "Prueba comando", permission = "prueba.si", permissionMessage = "Si")
		public boolean exect(@Sender CommandSender sender, @OptArg({"none"}) String pepe) {
			System.out.println("Funciona movistar");
			sender.sendMessage("Funciona LGTV+ "+pepe);
			return true;
		}
	}

	private class CustomLanguage extends DefaultMapTranslationProvider {
	    public CustomLanguage() {
	        translations.put("player.offline", "El jugador %s esta desconectado!");
            translations.put("sender.unknown", "The sender for the command is unknown!");
            translations.put("sender.only-player", "Only players can execute this command!");
            translations.put("invalid.gamemode", "The gamemode %s is not valid!");
        }
	    
	    public String getTranslation(String key) {
	    	return this.translations.get(key);
	    }
	    
	    public String getTranslation(Namespace namespace, String key) {
	    	return getTranslation(key);
	    }
    }

}
