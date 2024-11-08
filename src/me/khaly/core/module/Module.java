package me.khaly.core.module;

import org.bukkit.Bukkit;

import me.khaly.core.KhalyCore;
import me.khaly.core.util.TextUtil;

public abstract class Module {

	private KhalyCore core;
	private String name;
	private String uniqueId;
	private String author;
	private float version;
	
	private boolean persistent = false;
	private boolean isDisabled = false;

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
	
	public void disable() {
		this.isDisabled = true;
	}
	
	public void enable() {
		this.isDisabled = false;
	}
	
	public boolean isDisabled() {
		return isDisabled;
	}
	
	public void log(String message) {
		System.out.println(TextUtil.color("[" + name + "] " + message));
	}
	
	public void log(String message, Exception ex) {
		this.log(message);
		ex.printStackTrace();
	}
	
	protected void setAuthor(String author) {
		this.author = author;
	}

}
