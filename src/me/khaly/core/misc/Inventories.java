package me.khaly.core.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import me.khaly.core.KhalyCore;
import me.khaly.core.libraries.YamlFile;

public class Inventories {
	
	private YamlFile file;
	private Player p;

	public Inventories(KhalyCore core, Player p) {
		this.file = new YamlFile(core, p.getUniqueId().toString(), ".yml", "inventories");
		this.p = p;
	}

	public boolean hasInventoryInFile() {
		return this.file.contains("inventory");
	}

	public void saveInventory() {
		this.file.set("inventory", toBase64(this.p));
		this.file.save();
	}

	public void loadInventory(String base64) {
		// if(!this.hasInventoryInFile())return;
		p.getInventory().clear();
		PlayerInventory inv = p.getInventory();
		setItems(fromBase64(base64), inv);
	}
	
	public void loadInventory() {
		p.getInventory().clear();
		PlayerInventory inv = p.getInventory();
		setItems(fromBase64(this.file.getString("inventory")), inv);
		file.set("inventory", null);
		file.save();
	}
	
	public String toBase64(PlayerInventory inv) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeInt(inv.getSize());
			for (int i = 0; i < inv.getSize(); i++)
				dataOutput.writeObject(inv.getItem(i));
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks", e);
		}
	}
	
	public String toBase64(Player p) {
		return toBase64(p.getInventory());
	}

	public Map<Integer, ItemStack> fromBase64(String from) {
		Map<Integer, ItemStack> items = new HashMap<>();
		if (from != null && !from.isEmpty())
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(from));
				BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
				int size = dataInput.readInt();
				for (int i = 0; i < size; i++)
					items.put(Integer.valueOf(i), (ItemStack) dataInput.readObject());
				dataInput.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		return items;
	}

	private void setItems(Map<Integer, ItemStack> items, PlayerInventory inv) {
		int i = 0;
		for (ItemStack is : items.values()) {
			if (is != null)
				inv.setItem(i, is);
			i++;
		}
	}
}