package me.khaly.core.util;

import java.util.ArrayList;
import java.util.List;

import me.khaly.core.enchantment.Enchant;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;

public class EnchantmentUtil {
	    
	public static List<Enchant> readEnchantments(NBTTagList list) {
		List<Enchant> enchantments = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			NBTTagCompound enchant = list.get(i);
			Enchant enchantment = new Enchant(enchant.getString("id"), enchant.getInt("level"));
			enchantments.add(enchantment);
		}
		return enchantments;
	}
	
}
