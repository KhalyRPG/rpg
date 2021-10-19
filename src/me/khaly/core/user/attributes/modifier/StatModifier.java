package me.khaly.core.user.attributes.modifier;

import java.util.function.Consumer;

public class StatModifier {
	
	private String id;
	private Consumer<ValueConsumer> value;
	
	public StatModifier(String id, Consumer<ValueConsumer> value) {
		this.id = id;
		this.value = value;
	}
	
	public String getID() {
		return id;
	}
	
	public double getValue() {
		ValueConsumer consumer = new ValueConsumer();
		this.value.accept(consumer);
		return consumer.get();
	}
	
	public class ValueConsumer {
		private double value;
		
		public void set(double value) {
			this.value = value;
		}
		
		public double get() {
			return value;
		}
	}
	
}
