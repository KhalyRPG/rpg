package me.khaly.core.loader.gui;

import java.util.Map;

import me.khaly.core.KhalyCore;
import me.khaly.core.gui.MenuGUI;
import me.khaly.core.gui.ProfileGUI;
import me.khaly.core.gui.ProfileSelectionGUI;
import me.khaly.core.gui.object.Gui;
import me.khaly.core.loader.Loader;

public class GuiLoader implements Loader {
	
	private KhalyCore core;
	
	public GuiLoader(KhalyCore core) {
		this.core = core;
	}
	
	@Override
	public void load() {
		registerGUIs(
				new ProfileGUI(core),
				new ProfileSelectionGUI(core),
				new MenuGUI(core)
				);
	}
	
	private void registerGUIs(Gui... guis) {
		Map<String, Gui> cache = core.getServices().getCache().getGuis();
		for(Gui gui : guis) {
			cache.put(gui.getID(), gui);
		}
	}
	
}
