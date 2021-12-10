package me.khaly.core.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.khaly.core.KhalyCore;
import me.khaly.core.enums.StatAttribute;
import me.khaly.core.libraries.YamlFile;
import me.khaly.core.rpg.classes.object.RPGClass;
import me.khaly.core.user.attributes.modifier.StatModifier;
import me.khaly.core.user.attributes.modifier.StatModifier.ValueConsumer;
import me.khaly.core.user.attributes.stat.UserAttribute;
import me.khaly.core.user.mana.Mana;
import me.khaly.core.user.profile.Profile;
import me.khaly.core.util.ItemUtils;

public class UserImpl implements User {

	private UUID uuid;
	private long joinedAt;
	// private UserAttributes attributes;
	private Map<String, Profile> profiles;
	private String profile;
	private YamlFile file;
	private KhalyCore core;
	private Map<StatAttribute, UserAttribute> attributes;
	private Mana mana;

	public UserImpl(Player player) {
		this.core = KhalyCore.getInstance();
		this.uuid = player.getUniqueId();
		// this.attributes = new UserAttributesImpl(this.core, this);
		this.attributes = new HashMap<>();
		setupAttributes();
		this.joinedAt = System.currentTimeMillis();
		this.profiles = new HashMap<>();
		this.file = new YamlFile(this.core, player.getUniqueId().toString(), ".yml", "players");
		if (file.contains("profiles")) {
			for (String profile : file.getConfigurationSection("profiles").getKeys(false)) {
				this.profiles.put(profile, new Profile(this, profile));
			}
		}
		this.mana = new Mana(this);
	}

	@Override
	public long joinedAt() {
		return joinedAt;
	}

	@Override
	public Player getBukkitPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	@Override
	public void sendMessage(String text) {
		getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', text));
	}

	// @Override
	/*
	 * protected UserAttributes getAttributes() { return attributes; }
	 */

	@Override
	public UserAttribute getAttribute(StatAttribute attribute) {
		return attributes.get(attribute);
	}

	@Override
	public Profile getProfile() {
		return profiles.get(profile);
	}

	@Override
	public YamlFile getFile() {
		return file;
	}

	@Override
	public boolean hasProfile(String name) {
		boolean has = false;
		for (Map.Entry<String, Profile> entry : getProfiles().entrySet()) {
			if (entry.getValue().getName().equals(name)) {
				has = true;
				break;
			}
		}
		return has;
	}

	@Override
	public boolean hasProfile() {
		return profile != null;
	}

	@Override
	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public Map<String, Profile> getProfiles() {
		return profiles;
	}

	@Override
	public void loadProfile() {
		if (profile == null)
			return;
		Profile profile = getProfile();
		profile.load();
	}

	@Override
	public void updateProfiles() {
		this.profiles.clear();
		for (String profile : file.getConfigurationSection("profiles").getKeys(false)) {
			this.profiles.put(profile, new Profile(this, profile));
		}
	}

	@Override
	public Mana getManaSystem() {
		return mana;
	}

	private void setupAttributes() {
		for (StatAttribute attribute : StatAttribute.values()) {
			put(new UserAttribute(attribute, attribute.getDefaultValue()));
		}
		setupModifiers();
	}

	private void setupModifiers() {
		addModifier(StatAttribute.DEFENSE, StatAttribute.CURSED_ENERGY, StatAttribute.ATTACK_SPEED,
				StatAttribute.HEALTH_REGEN, StatAttribute.MANA_REGEN, StatAttribute.SPEED);

		addModifierWithClass(StatAttribute.HEALTH, StatAttribute.INTELLIGENCE, StatAttribute.STRENGTH,
				StatAttribute.MELEE_DAMAGE, StatAttribute.RANGED_DAMAGE);

		addModifier(StatAttribute.LUCK, "global", (value) -> {
			value.set(getBukkitPlayer().getAttribute(Attribute.GENERIC_LUCK).getValue());
		});
	}

	private void addModifierWithClass(StatAttribute... attributes) {
		for (StatAttribute attribute : attributes) {
			try {
				addModifier(attribute, "global", (value) -> {
					double amplifier = ItemUtils.getItemAmplifiers(core, getBukkitPlayer(), attribute);

					// amplifier += evaluateModule(attribute);

					value.set(amplifier + getClassAmplifier(Types.valueOf(attribute.name())));
				});
			} catch (Exception ex) {
				core.getLogger().log(Level.SEVERE, "Cannot load modifier \"" + attribute.name() + "\"", ex);
			}
		}
	}

	private void addModifier(StatAttribute... attributes) {
		for (StatAttribute attribute : attributes) {
			addModifier(attribute, "global", (value) -> {
				double amplifier = ItemUtils.getItemAmplifiers(core, getBukkitPlayer(), attribute);

				// amplifier += evaluateModule(attribute);

				value.set(amplifier);
			});
		}
	}

	/*
	 * private double evaluateModule(StatAttribute attribute) { double amplifier =
	 * 0; sendMessage("&cTrying...");
	 * if(this.core.getLocalModuleManager().getModule("inventory") != null) {
	 * sendMessage("&aInitalized"); InventoryModule module = (InventoryModule)
	 * this.core.getLocalModuleManager().getModule("inventory");
	 * Collection<InventorySlot> slots = module.getItems().values();
	 * 
	 * for(InventorySlot slot : slots) { ItemStack item =
	 * getBukkitPlayer().getInventory().getItem(slot.getInventorySlot()); amplifier
	 * += ItemUtils.getModifier(attribute, item); sendMessage("&aLoaded: " +
	 * item.getType()); } }
	 * 
	 * return amplifier; }
	 */

	private void put(UserAttribute attribute) {
		attributes.put(attribute.getAttribute(), attribute);
	}

	private void addModifier(StatAttribute attribute, String id, Consumer<ValueConsumer> consumer) {
		getAttribute(attribute).addModifier(new StatModifier(id, consumer));
	}

	private double getClassAmplifier(Types type) {
		if (!hasProfile()) {
			return 0;
		}
		RPGClass clazz = getProfile().getRPGClass();
		int level = getProfile().getLevel();
		switch (type) {
		case HEALTH:
			return clazz.getHealth(level);
		case INTELLIGENCE:
			return clazz.getIntelligence(level);
		case STRENGTH:
			return clazz.getStrength(level);
		case MELEE_DAMAGE:
			return clazz.getMeleeDamage(level);
		case RANGED_DAMAGE:
			return clazz.getRangedDamage(level);
		default:
			break;
		}
		return 0;
	}

	enum Types {
		HEALTH, INTELLIGENCE, STRENGTH, MELEE_DAMAGE, RANGED_DAMAGE;
	}


}
