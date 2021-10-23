package me.khaly.core.user.attributes.stat;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableSet;

import me.khaly.core.enums.StatAttribute;
import me.khaly.core.user.attributes.modifier.StatModifier;

public class UserAttribute {
	
	private double baseValue;
	private Map<String, StatModifier> modifiers;
	private StatAttribute attribute;
	
	public UserAttribute(StatAttribute attribute, double baseValue) {
		this.attribute = attribute;
		this.baseValue = baseValue;
		this.modifiers = new HashMap<>();
	}
	
	public void setBaseValue(double value) {
		this.baseValue = value;
	}
	
	public double getBaseValue() {
		return baseValue;
	}
	
	public double getValue() {
		double value = baseValue;
		for(StatModifier modifier : ImmutableSet.copyOf(modifiers.values())) {
			value += modifier.getValue();
		}
		return value;
	}
	
	public void addModifier(StatModifier... modifiers) {
		for(StatModifier modifier : modifiers) {
			if(this.modifiers.containsKey(modifier.getID())) {
				this.modifiers.remove(modifier.getID());
			}
			this.modifiers.put(modifier.getID(), modifier);
		}
	}
	
	public StatModifier getModifier(String id) {
		return modifiers.get(id);
	}
	
	public boolean hasModifier(String id) {
		return modifiers.containsKey(id);
	}
	
	public boolean removeModifier(String id) {
		if(hasModifier(id)) {
			modifiers.remove(id);
			return true;
		}
		return false;
	}
	
	public StatAttribute getAttribute() {
		return attribute;
	}
	
}
