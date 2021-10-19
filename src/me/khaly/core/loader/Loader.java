package me.khaly.core.loader;

public interface Loader {
	void load();
	default void unload() {
		
	}
}
