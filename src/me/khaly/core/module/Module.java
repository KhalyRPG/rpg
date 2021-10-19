package me.khaly.core.module;

import org.bukkit.Bukkit;

import me.khaly.core.KhalyCore;

public abstract class Module {

	private KhalyCore core;
	private String name;
	private String uniqueId;
	private String author;
	private float version;
	private boolean persistent = false;

	public Module(String name, String uniqueId, float version) {
		this.core = KhalyCore.getInstance();
		this.name = name;
		this.uniqueId = uniqueId;
		this.version = version;
	}

	public abstract void load();

	public void unload() { }

	public String getName() {
		return name;
	}

	public float getVersion() {
		return version;
	}
	
	public String getAuthor() {
		return author;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public final boolean register() {
		return core.getLocalModuleManager().register(this);
	}

	public final boolean unregister() {
		return core.getLocalModuleManager().unregister(this);
	}

	public boolean isPersistent() {
		return persistent;
	}

	public boolean canRegister() {
		return core == null || Bukkit.getPluginManager().getPlugin("core") != null;
	}
	
	protected KhalyCore getProvider() {
		return core;
	}
	
	protected void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
	
	protected void log(String message) {
		Bukkit.getConsoleSender().sendMessage("[" + name + "] " + message);
	}
	
	protected void setAuthor(String author) {
		this.author = author;
	}

}
