package me.khaly.core.enums;

import me.khaly.core.misc.Classes;
import me.khaly.core.user.User;

public enum Requirement {
	MAGE_CLASS,
	BERSERK_CLASS,
	ARCHER_CLASS,
	COMBAT_LEVEL;
		
	public static String requirementText(Requirement req, int amount) {
		switch(req) {
		case ARCHER_CLASS: return "Clase Req: Arquero";
		case MAGE_CLASS: return "Clase Req: Mago";
		case BERSERK_CLASS: return "Clase Req: Berserker";
//		case STRENGTH: return "Fuerza Req: "+amount;
//		case HEALTH: return "Vida Req: "+amount;
//		case INTELLIGENCE: return "Inteligencia Req: "+amount;
		case COMBAT_LEVEL: return "Nivel de Combate Min: "+amount;
//		case DEFENSE: return "Defensa Req: "+amount;
		default: return "§8Desconocido";
		}
	}
	
	public String requirementText(int amount) {
		return requirementText(this, amount);
	}
	
	public boolean hasRequirement(User user, int amount) {
		return hasRequirement(user, this, amount);
	}
	
	public static boolean hasRequirement(User user, Requirement req, int amount) {
		if(user == null)return false;
		String clazz = user.getProfile().getRPGClass().getID();
		switch(req) {
		case ARCHER_CLASS:
			return check(clazz, "archer");
		case MAGE_CLASS:
			return check(clazz, "mage");
		case BERSERK_CLASS:
			return check(clazz, "berserk");
		/*case STRENGTH:
			return attr.getNoItemStrength() >= amount;
		case HEALTH: 
			return attr.getMaxHealth() >= amount;
		case INTELLIGENCE: 
			return attr.getNoItemIntelligence() >= amount;
		case DEFENSE: 
			return attr.getDefense() >= amount;*/
		case COMBAT_LEVEL:
			if(Classes.getLevelFormExp((int) user.getProfile().getExp()) >= amount)return true;
		default: return false;
		
		}
	}
	
	private static boolean check(String clazz, String target) {
		if(clazz == null || !clazz.equalsIgnoreCase(target))return false;
		return true;
	}
	
}