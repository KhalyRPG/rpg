package me.khaly.core.util;

public class MathUtils extends Util {
	
	public static double getPercentage(double number, double total) {
        return number * 100 / total;
    }
	
	public static double decreaseByPercentage(double number, double percentage) {
		return number * (100 - percentage) / 100;
	}
	
	public static double increaseByPercentage(double number, double percentage) {
		return number + (percentage * (number / 100));
	}
	
	public static boolean isEven(double number) {
		return ((int)(number) & 1) == 0;
	}
	
}
