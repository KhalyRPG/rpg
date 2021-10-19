package me.khaly.core.enums;

import org.bukkit.event.block.Action;

public enum ClickAction {
	LEFT("Izquierda", 'I', Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK),
	RIGHT("Derecha", 'D', Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
	
	private String name;
	private char charString;
	private Action[] actions;
	
	ClickAction(String name, char charString, Action... actions) {
		this.name = name;
		this.charString = charString;
		this.actions = actions;
	}
	
	public String getName() {
		return name;
	}
	
	public char getChar() {
		return charString;
	}
	
	public Action[] getActions() {
		return actions;
	}
	
	public boolean isValidAction(Action actionToCompare) {
		boolean valid = false;
		for(int i=0;i<getActions().length;i++) {
			Action action = getActions()[i];
			if(actionToCompare == action) {
				valid = true;
				break;
			}
		}
		return valid;
	}
	
	
}
