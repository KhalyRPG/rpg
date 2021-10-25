package me.khaly.core.enums;

public enum ItemType {
	MENU("Menú"),
	WEAPON("Arma"),
	ARMOR("Armadura"),
	WAND("Varita"),
	BOW("Arco"),
	SHIELD("Escudo"),
	FOOD("Comida"),
	ELEMENT("Elemento"),
	GEM("Gema"),
	MINERAL("Mineral"),
	ELYTRA("Élitros"),
	ORB("Orbe"),
	
	RING("Anillo"),
	COLLAR("Collar"),
	BRACELET("Brazalete"),
	
	OTHER("Otro");
	private String display;
	
	ItemType(String display) {
		this.display = display;
	}
	
	public String getDisplayName() {
		return display;
	}
}