package me.khaly.core.passive.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemPassives {
	private Map<String, ItemPassive> itemPassives;
	
	public ItemPassives() {
		this.itemPassives = new HashMap<>(); 
	}
	
	public void registerPassive(ItemPassive... abilities) {
		for(ItemPassive passive : abilities) {
			this.itemPassives.put(passive.getKey(), passive);
		}
	}
	
	public boolean existsPassive(String key) {
		return itemPassives.containsKey(key);
	}
	
	public boolean existsPassive(ItemPassive key) {
		return itemPassives.containsValue(key);
	}
	
	public Map<String, ItemPassive> getPassives() {
		return Collections.unmodifiableMap(itemPassives);
	}

	public ItemPassive getPassive(String keyAbility) {
		return itemPassives.get(keyAbility);
	}
}
