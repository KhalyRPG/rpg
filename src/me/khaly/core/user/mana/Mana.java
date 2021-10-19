package me.khaly.core.user.mana;

import me.khaly.core.enums.StatAttribute;
import me.khaly.core.user.User;

public class Mana {
	
	private User user;
	private double mana;
	
	public Mana(User user) {
		this.user = user;
		this.mana = user.getAttribute(StatAttribute.INTELLIGENCE).getValue() * 2;
	}
 	
	public double getMana() {
		return mana;
	}
	
	public double getMaxMana() {
		return user.getAttribute(StatAttribute.INTELLIGENCE).getValue() * 2;
	}
	
	public void setMana(double arg0) {
		this.mana = arg0;
	}

	public void addMana(double arg0) {
		if(arg0 > getMaxMana()) {
			arg0 = getMaxMana();
		} else {
			this.mana += arg0;
		}
	}

	public void removeMana(double arg0) {
		if((arg0 - mana) <= 0) {
			setMana(0);
		} else {
			this.mana -= arg0;
		}
	}
}
