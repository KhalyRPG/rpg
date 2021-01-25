package me.atog.anr.plugin.templates;

import org.bukkit.command.CommandSender;

import me.atog.anr.plugin.object.Util;

public interface MMORPGSender extends CommandSender {
	public default void sendColoredMessage(String toColor) {
		this.sendMessage(Util.color(toColor));
	}
}
