package me.atog.anr.plugin.nms.actionbar;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.atog.anr.plugin.object.Util;
import me.atog.anr.plugin.templates.ActionBar;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class ActionBar_V1_12 extends ActionBar {
	private String message;
	private Player player;
	private boolean toCancel = false;
	public ActionBar_V1_12() {}
	public ActionBar_V1_12(String message) {
		this.message = message;
	}
	
	public ActionBar_V1_12(String message, Player player) {
		this.message = message;
		this.player = player;
	}
	
	public ActionBar_V1_12 setMessage(@NotNull String message) {
		this.message = message;
		return this;
	}
	
	public ActionBar_V1_12 setReceiver(@NotNull Player p) {
		this.player = p;
		return this;
	}
	
	public ActionBar_V1_12 build() {
		if(toCancel)return null;
		PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(Util.color(message)));
		(((CraftPlayer)this.player).getHandle()).playerConnection.sendPacket(packet);
		this.toCancel = true;
		return this;
	}
}
