package me.khaly.core.module;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.io.File;
import java.lang.reflect.Modifier;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import me.khaly.core.KhalyCore;
import me.khaly.core.util.FileUtil;
import me.khaly.core.util.Futures;

public class LocalModuleManager implements Listener {
	private static final String FOLDER_NAME = "modules";

	private static final Set<MethodSignature> ABSTRACT_MODULE_METHODS = Arrays.stream(Module.class.getDeclaredMethods())
			.filter(method -> Modifier.isAbstract(method.getModifiers()))
			.map(method -> new MethodSignature(method.getName(), method.getParameterTypes()))
			.collect(Collectors.toSet());

	private final File folder;
	private final KhalyCore plugin;

	private final Map<String, Module> modules = new ConcurrentHashMap<>();
	private final ReentrantLock modulesLock = new ReentrantLock();

	public LocalModuleManager(final KhalyCore plugin) {
		this.plugin = plugin;
		this.folder = new File(plugin.getDataFolder(), FOLDER_NAME);

		if (!this.folder.exists() && !folder.mkdirs()) {
			plugin.getLogger().warning("failed to create modules folder");
		}
	}

	public void load(final CommandSender sender) {
		registerAll(sender);
	}

	public void kill() {
		unregisterAll();
	}

	public File getModulesFolder() {
		return folder;
	}

	public Collection<String> getIdentifiers() {
		modulesLock.lock();
		try {
			return ImmutableSet.copyOf(modules.keySet());
		} finally {
			modulesLock.unlock();
		}
	}

	public Collection<Module> getModules() {
		modulesLock.lock();
		try {
			return ImmutableSet.copyOf(modules.values());
		} finally {
			modulesLock.unlock();
		}
	}

	public Module getModule(String identifier) {
		modulesLock.lock();
		try {
			return modules.get(identifier.toLowerCase());
		} finally {
			modulesLock.unlock();
		}
	}

	public Optional<Module> findModuleByName(String name) {
		modulesLock.lock();
		try {
			Module bestMatch = null;
			for (Map.Entry<String, Module> entry : modules.entrySet()) {
				Module module = entry.getValue();
				if (module.getName().equalsIgnoreCase(name)) {
					bestMatch = module;
					break;
				}
			}
			return Optional.ofNullable(bestMatch);
		} finally {
			modulesLock.unlock();
		}
	}

	public Optional<Module> findModuleById(final String identifier) {
		return Optional.ofNullable(getModule(identifier));
	}

	public Optional<Module> register(final Class<? extends Module> clazz) {
		try {
			final Module module = createModuleInstance(clazz);

			// Objects.requireNonNull(expansion.getAuthor(), "The expansion author is
			// null!");
			Objects.requireNonNull(module.getUniqueId(), "The module id is null!");
			Objects.requireNonNull(module.getVersion(), "The module version is null!");

			if (!module.register()) {
				return Optional.empty();
			}

			return Optional.of(module);
		} catch (LinkageError | NullPointerException ex) {
			final String reason;

			if (ex instanceof LinkageError) {
				reason = " (Is a dependency missing?)";
			} else {
				reason = " | One of its properties is null which is not allowed";
			}

			plugin.getLogger().severe("Failed to load module class " + clazz.getSimpleName() + reason);
			plugin.getLogger().log(Level.SEVERE, "", ex);
		}

		return Optional.empty();
	}

	public boolean register(final Module module) {
		final String identifier = module.getUniqueId().toLowerCase();

		if (!module.canRegister()) {
			return false;
		}

		final Module removed = getModule(identifier);
		if (removed != null && !removed.unregister()) {
			return false;
		}
		
		modulesLock.lock();
		try {
			modules.put(identifier, module);
		} finally {
			modulesLock.unlock();
		}

		if (module instanceof Listener) {
			Bukkit.getPluginManager().registerEvents((Listener) module, plugin);
		}

		module.load();
		plugin.getLogger().info("§aSuccessfully registered module: §7" + module.getName());
		return true;
	}

	private void registerAll(final CommandSender sender) {
		plugin.getLogger().info("Module registration initializing...");

		Futures.onMainThread(plugin, findModulesOnDisk(), (classes, exception) -> {
			if (exception != null) {
				plugin.getLogger().log(Level.SEVERE, "failed to load class files of modules", exception);
				return;
			}

			final long registered = classes.stream().filter(Objects::nonNull).map(this::register)
					.filter(Optional::isPresent).count();
			
			
			
			sender.sendMessage(registered == 0 ? "§6No modules were registered"
					: "§a" + registered + " modules were correctly registered.");
		});
	}

	private void unregisterAll() {
		for (final Module module : Sets.newHashSet(modules.values())) {
			if (module.isPersistent()) {
				continue;
			}

			unregister(module);
		}
	}

	public boolean unregister(final Module module) {
		if (modules.remove(module.getUniqueId()) == null) {
			return false;
		}

		if (module instanceof Listener) {
			HandlerList.unregisterAll((Listener) module);
		}

		module.unload();

		return true;
	}

	public CompletableFuture<List<Class<? extends Module>>> findModulesOnDisk() {
		return Arrays.stream(folder.listFiles((dir, name) -> name.endsWith(".jar"))).map(this::findModuleInFile)
				.collect(Futures.collector());
	}

	public CompletableFuture<Class<? extends Module>> findModuleInFile(final File file) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				final Class<? extends Module> moduleClass = FileUtil.findClass(file, Module.class);

				if (moduleClass == null) {
					plugin.getLogger().severe("Failed to load module: " + file.getName() + ", as it does not have"
							+ " a class which extends Module.");
					return null;
				}

				Set<MethodSignature> moduleMethods = Arrays.stream(moduleClass.getDeclaredMethods())
						.map(method -> new MethodSignature(method.getName(), method.getParameterTypes()))
						.collect(Collectors.toSet());
				if (!moduleMethods.containsAll(ABSTRACT_MODULE_METHODS)) {
					plugin.getLogger().severe("Failed to load Module: " + file.getName() + ", as it does not have the"
							+ " required methods declared for a Module.");
					return null;
				}

				return moduleClass;
			} catch (final VerifyError ex) {
				plugin.getLogger()
						.severe("Failed to load Module class " + file.getName() + " (Is a dependency missing?)");
				plugin.getLogger().severe("Cause: " + ex.getClass().getSimpleName() + " " + ex.getMessage());
				return null;
			} catch (final Exception ex) {
				throw new CompletionException(ex);
			}
		});
	}

	public Module createModuleInstance(final Class<? extends Module> clazz) throws LinkageError {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (final Exception ex) {
			if (ex.getCause() instanceof LinkageError) {
				throw ((LinkageError) ex.getCause());
			}

			plugin.getLogger().warning("There was an issue with loading an module.");

			return null;
		}
	}

}
