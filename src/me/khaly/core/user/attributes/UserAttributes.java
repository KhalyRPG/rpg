package me.khaly.core.user.attributes;

public interface UserAttributes {
	
	double getHealth();
	double getMaxHealth();
	double getDefense();
	double getIntelligence();
	double getStrength();
	double getSpeed();
	double getCursedEnergy();
	double getLuck();
	double getMana();
	double getMaxMana();
	
	double getNoItemStrength();
	double getNoItemIntelligence();
	double getNoItemCursedEnergy();
	
	void setMana(double arg0);
	void addMana(double arg0);
	void removeMana(double arg0);
}
