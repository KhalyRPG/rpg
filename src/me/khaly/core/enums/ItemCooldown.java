package me.khaly.core.enums;

public enum ItemCooldown {
	VERY_SLOW(3.5, "Velocidad muy lenta"),
	SLOW(3, "Velocidad lenta"),
	NORMAL(2.3, "Velocidad normal"),
	FAST(1.5, "Velocidad rápida"),
	VERY_FAST(1, "Velocidad muy rápida")
	;
	private double time;
	private String name;
	
	ItemCooldown(double time, String name) {
		this.time = time;
		this.name = name;
	}
	/**
	 * 
	 * @return the time in seconds
	 */
	public double getTime() {
		return time;
	}
	
	/**
	 * 
	 * @return the display name
	 */
	public String getDisplayName() {
		return name;
	}
	
	public int getTimeInTicks() {
		return (int)(time * 20);
	}
	
	/**
	 * 
	 * @return the time in milliseconds
	 */
	public double getTimeInMilliseconds() {
		return (int)(this.time * 1000);
	}
	
}
