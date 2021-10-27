package me.khaly.core.enums;

public enum AccessoryType {
	RING(ItemType.RING),
	BRACELET(ItemType.BRACELET),
	COLLAR(ItemType.COLLAR);
	
	private ItemType type;
	
	AccessoryType(ItemType type) {
		this.type = type;
	}
	
	public ItemType getType() {
		return type;
	}
}
