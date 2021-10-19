package me.khaly.core.loader.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import me.khaly.core.KhalyCore;
import me.khaly.core.listener.ArmorListener;
import me.khaly.core.listener.EnchantmentsListener;
import me.khaly.core.listener.EquipmentListener;
import me.khaly.core.listener.HotbarItemsListener;
import me.khaly.core.listener.ItemUpdaterListener;
import me.khaly.core.listener.RegenerationListener;
import me.khaly.core.listener.RemoveArrowsListener;
import me.khaly.core.listener.PlayerDeathListener;
import me.khaly.core.listener.PlayerJoinListener;
import me.khaly.core.listener.PlayerQuitListener;
import me.khaly.core.listener.InstantBowShootListener;
import me.khaly.core.loader.Loader;
import team.unnamed.gui.core.GUIListeners;

public class ListenerLoader implements Loader {
	
	private KhalyCore core;
	
	public ListenerLoader(KhalyCore core) {
		this.core = core;
	}
	
	@Override
	public void load() {
		registerListeners(
				new PlayerJoinListener(core),
				new PlayerQuitListener(core),
				new PlayerDeathListener(core),
				new ItemUpdaterListener(core),
				new HotbarItemsListener(core),
				new RegenerationListener(core),
				new EquipmentListener(core),
				new ArmorListener(core),
				new InstantBowShootListener(core),
				new EnchantmentsListener(core),
				
				new RemoveArrowsListener(),
				new GUIListeners()
				);
	}
	
	private void registerListeners(Listener... listeners) {
		for(Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener, core);
		}
	}

}
