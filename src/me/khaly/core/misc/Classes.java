package me.khaly.core.misc;

import java.util.HashMap;
import java.util.Map;

import me.khaly.core.rpg.classes.object.RPGClass;

public class Classes {
	private static Map<String, RPGClass> classes = new HashMap<>();
	
	public static Map<String, RPGClass> getClasses() {
		return classes;
	}
	
	public static void register(RPGClass... classes) {
		for(RPGClass rpgClass : classes) {
			Classes.classes.put(rpgClass.getID(), rpgClass);
		}
	}
	
	public static boolean isRegistrered(String name) {
		return classes.containsKey(name.toLowerCase());
	}
	
	public static RPGClass getRPGClass(String name) {
		return classes.get(name.toLowerCase());
	}
	
	private static double getLevelExp(int level) {
		return 5 * (Math.pow(level, 2)) + 50 * level + 100;
	}
	
	public static int getExpFormLevel(int level) {
		int exp = 0;
		for (int i = level - 1;i >= 0;i--) {
			exp += Classes.getLevelExp(i);
		}
		return exp;
	}
	
	public static int getLevelFormExp(int exp) {
		int level = 0;
		
		while(exp >= getLevelExp(level)) {
			exp -= getLevelExp(level);
			level++;
		}
		
		if(level <= 0) {
			return 1;
		}
		if(level >= 280) {
			return 130;
		}
		
		return level;
	}
	
}