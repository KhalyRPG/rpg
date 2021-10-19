package me.khaly.core.user;

import java.util.Map;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.khaly.core.enums.StatAttribute;
import me.khaly.core.libraries.YamlFile;
import me.khaly.core.user.attributes.stat.UserAttribute;
import me.khaly.core.user.mana.Mana;
import me.khaly.core.user.profile.Profile;
import net.minecraft.server.v1_12_R1.Packet;

public interface User {
	
	static User createUser(Player player) {
		return new UserImpl(player);
	}
	
	long joinedAt();
	Player getBukkitPlayer();
	void sendMessage(String text);
	UserAttribute getAttribute(StatAttribute attribute);
	//UserAttributes getAttributes();
	Profile getProfile();
	void setProfile(String profile);
	Map<String, Profile> getProfiles();
	void loadProfile();
	void updateProfiles();
	boolean hasProfile();
	boolean hasProfile(String name);
	YamlFile getFile();
	Mana getManaSystem();
	
	default void sendPacket(Object packet) {
		((CraftPlayer)getBukkitPlayer()).getHandle().playerConnection.sendPacket((Packet<?>) packet);
	}
}
