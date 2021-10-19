package me.khaly.core.builder;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.khaly.core.enums.LeatherArmor;

public class LeatherArmorBuilder extends ItemBuilder {
	private int r, g, b;

	public LeatherArmorBuilder(LeatherArmor v) {
		super(v.getMaterial(), 1, (short) 0);
	}

	public LeatherArmorBuilder setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		return this;
	}

	public LeatherArmorBuilder setColor(Color color) {
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
		return this;
	}

	public LeatherArmorBuilder setColor(String hex) {
		this.r = Integer.valueOf(hex.substring(1, 3), 16);
		this.g = Integer.valueOf(hex.substring(3, 5), 16);
		this.b = Integer.valueOf(hex.substring(5, 7), 16);
		return this;
	}

	public ItemStack build() {
		ItemStack i = this.convert();
		LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
		m.setColor(Color.fromRGB(r, g, b));
		i.setItemMeta(m);
		return i;
	}

}
