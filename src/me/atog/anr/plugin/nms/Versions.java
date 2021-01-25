package me.atog.anr.plugin.nms;

import org.bukkit.Bukkit;

public class Versions {
	public static boolean is1_8() {
	    return (Bukkit.getVersion().contains("1.8") && Bukkit.getServer().getClass().getPackage().getName().contains("R1"));
	  }
	  
	  public static boolean is1_8_8() {
	    return (Bukkit.getVersion().contains("1.8") && Bukkit.getServer().getClass().getPackage().getName().contains("R3"));
	  }
	  
	  public static boolean is1_9() {
	    return Bukkit.getVersion().contains("1.9");
	  }
	  
	  public static boolean is1_10() {
	    return Bukkit.getVersion().contains("1.10");
	  }
	  
	  public static boolean is1_11() {
	    return Bukkit.getVersion().contains("1.11");
	  }
	  
	  public static boolean is1_12() {
	    return Bukkit.getVersion().contains("1.12");
	  }
	  
	  public static boolean is1_13() {
	    return Bukkit.getVersion().contains("1.13");
	  }
	  
	  public static boolean is1_14() {
	    return Bukkit.getVersion().contains("1.14");
	  }
	  
	  public static boolean matchVersion(String version) {
	    return Bukkit.getVersion().contains(version);
	  }
	  
}
