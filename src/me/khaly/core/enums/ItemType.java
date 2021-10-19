package me.khaly.core.enums;

public enum ItemType {
	MENU("Menú"),
	WEAPON("Arma"),
	ARMOR("Armadura"),
	WAND("Varita"),
	BOW("Arco"),
	SHIELD("Escudo"),
	FOOD("Comida"),
	ACCESSORY("Accesorio"),
	ELEMENT("Elemento"),
	GEM("Gema"),
	MINERAL("Mineral"),
	ELYTRA("Élitros"),
	ORB("Orbe"),
	OTHER("Otro");
	private String display;
	
	ItemType(String display) {
		this.display = display;
	}
	
	public String getDisplayName() {
		return display;
	}
}