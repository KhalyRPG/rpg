package me.khaly.core.builder;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;

public class ActionbarBuilder {
	private String text;
	private PacketPlayOutTitle packet;
	public ActionbarBuilder(String text) {
	    this.text = text;
	    this.packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + this.text + "\"}"));
	}
	
	
	public void send(Player p) {
		(((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
	}
	
}
