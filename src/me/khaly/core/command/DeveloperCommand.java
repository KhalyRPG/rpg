package me.khaly.core.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.khaly.core.KhalyCore;
import me.khaly.core.builder.LeatherArmorBuilder;
import me.khaly.core.builder.SkullItemBuilder;
import me.khaly.core.creator.ItemAccessoryCreator;
import me.khaly.core.creator.ItemArmorCreator;
import me.khaly.core.creator.ItemBowCreator;
import me.khaly.core.creator.ItemCreator;
import me.khaly.core.creator.ItemWeaponCreator;
import me.khaly.core.enchantment.ItemEnchantment;
import me.khaly.core.enums.AccessoryType;
import me.khaly.core.enums.ItemCooldown;
import me.khaly.core.enums.LeatherArmor;
import me.khaly.core.enums.Rarity;
import me.khaly.core.util.ItemUtils;

@Command(names = {
		"developer"
}, permission = "khaly.dev")
public class DeveloperCommand implements CommandClass {
	
	private List<ItemCreator> creators;
	private KhalyCore core;
	
	public DeveloperCommand(KhalyCore core) {
		this.core = core;
		this.creators = new ArrayList<>();
		
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.CHESTPLATE)
				.setColor(Color.SILVER)
				.build(), "Scrap Chestplate")
				.setDefense(20)
				.setStrength(20)
				.setHealth(15)
				.setRarity(Rarity.RARE)
				);
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.LEGGINGS)
				.setColor(Color.SILVER)
				.build(), "Scrap Leggings")
				.setDefense(30)
				.setStrength(15)
				.setHealth(10)
				.setRarity(Rarity.RARE));
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.BOOTS)
				.setColor(Color.SILVER)
				.build(), "Scrap Boots")
				.setDefense(10)
				.setStrength(5)
				.setHealth(10)
				.setRarity(Rarity.RARE));
		creators.add(new ItemArmorCreator(new SkullItemBuilder()
				.setTexture("http://textures.minecraft.net/texture/ef3a1265c6a0a796adc76e0734d688997feba907bdb3d708c8733ecbac1af678")
				.build(), "Diamond Steve Head")
				.setDefense(40)
				.setHealth(40)
				.setStrength(35)
				.setRarity(Rarity.FABLED)
				);
		creators.add(new ItemWeaponCreator(Material.STONE_SPADE, "Spade of Perun")
				.setCooldown(ItemCooldown.NORMAL)
				.setDamage(25)
				.setStrength(10)
				.setIntelligence(30)
				.setRarity(Rarity.UNCOMMON)
				);
		
		creators.add(new ItemWeaponCreator(Material.GOLD_HOE, "Hoz de la muerte")
				.setDamage(55)
				.setStrength(35)
				.setRarity(Rarity.FABLED)
				);
		creators.add(new ItemArmorCreator(new SkullItemBuilder()
				.setTexture("http://textures.minecraft.net/texture/dedcd92b0e210cfe98892c4334be462b3b9e725ddbd009c2783fcf88f0ffdc53")
				.build()
				, "Golden Slime Head")
				.setDefense(160)
				.setHealth(125)
				.setStrength(45)
				.setMeleeDamage(0.75)
				.setRarity(Rarity.FABLED));
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.CHESTPLATE)
				.setColor(230, 214, 60)
				.build(), "Golden Knight Chestplate")
				.setDefense(130)
				.setHealth(130)
				.setStrength(80)
				.setMeleeDamage(0.80)
				.setRarity(Rarity.FABLED));
		
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.LEGGINGS)
				.setColor(230, 154, 60)
				.build(), "Golden Knight Leggings")
				.setDefense(115)
				.setHealth(106)
				.setStrength(58)
				.setMeleeDamage(0.60)
				.setRarity(Rarity.FABLED));
		
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.BOOTS)
				.setColor(230, 94, 60)
				.build(), "Golden Knight Boots")
				.setDefense(90)
				.setHealth(70)
				.setStrength(46)
				.setMeleeDamage(0.30)
				.setRarity(Rarity.FABLED));
		
		creators.add(new ItemBowCreator("Bow of Legends")
				.setDamage(20)
				.setRangedDamage(84.3)
				.setRarity(Rarity.RARE));
		
		creators.add(new ItemWeaponCreator(Material.GOLD_SPADE, "Berserk Hammer")
				.setDamage(80)
				.setStrength(50)
				.setMeleeDamage(2.53)
				.setEnchanted(true)
				.setRarity(Rarity.FABLED)
				);
		
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.BOOTS)
				.setColor(36, 231, 156)
				.build(), "Diamond Slime Lord Boots")
				.setDefense(130)
				.setHealth(90)
				.setStrength(50)
				.setMeleeDamage(0.60)
				.setRangedDamage(1.35)
				.setRarity(Rarity.MYTHIC)
				);
		
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.LEGGINGS)
				.setColor(36, 231, 205)
				.build(), "Diamond Slime Lord Leggings")
				.setDefense(150)
				.setHealth(130)
				.setStrength(70)
				.setMeleeDamage(0.80)
				.setRangedDamage(2.15)
				.setRarity(Rarity.MYTHIC)
				);
		
		creators.add(new ItemArmorCreator(new LeatherArmorBuilder(LeatherArmor.CHESTPLATE)
				.setColor(36, 202, 231)
				.build(), "Diamond Slime Lord Chestplate")
				.setDefense(180)
				.setHealth(160)
				.setStrength(90)
				.setMeleeDamage(1.05)
				.setRangedDamage(2.40)
				.setRarity(Rarity.MYTHIC)
				);
				
		creators.add(new ItemArmorCreator(new SkullItemBuilder()
				.setTexture("http://textures.minecraft.net/texture/1cf4ebbd946c3d2a9d097a7186711a99c26f3110603c53ff729e1ba28cf89b71")
				.build(), "Diamond Slime Lord Head")
				.setDefense(160)
				.setHealth(140)
				.setStrength(80)
				.setMeleeDamage(0.90)
				.setRangedDamage(1.65)
				.setRarity(Rarity.MYTHIC));
		
		creators.add(new ItemBowCreator("Spiritual Bow")
				.setDamage(60)
				.setStrength(45)
				.setRangedDamage(2.75)
				.setIntelligence(20)
				.setRarity(Rarity.FABLED)
				);
		
		creators.add(new ItemAccessoryCreator(new SkullItemBuilder() {{
			setTexture("http://textures.minecraft.net/texture/177c9c638bf3dcda348edea44e9a3db4abc1e239558661611f80c110472ad");
		}}, "Ring of Healing", AccessoryType.RING)
				.setIntelligence(-1)
				.setHealthRegeneration(0.25)
				.setRarity(Rarity.UNCOMMON)
			);
		creators.add(new ItemAccessoryCreator(new SkullItemBuilder() {{
			setTexture("http://textures.minecraft.net/texture/1ea19079ca28158984ea29ea459173876e48453c2c8b864898935d69c80");
		}}, "Ring of Strength", AccessoryType.RING)
				.setStrength(5)
				.setRarity(Rarity.COMMON)
			);
		creators.add(new ItemAccessoryCreator(new SkullItemBuilder() {{
			setTexture("http://textures.minecraft.net/texture/8d17fdb8ff3c4a2ddd56ec2da21ec10008d36994ebab2ebac02c53a91b73d0d8");
		}}, "Wise Bracelet", AccessoryType.BRACELET)
				.setIntelligence(15)
				.setManaRegeneration(0.75)
				.setRarity(Rarity.EPIC)
			);
		creators.add(new ItemAccessoryCreator(new SkullItemBuilder() {{
			setTexture("http://textures.minecraft.net/texture/177c9c638bf3dcda348edea44e9a3db4abc1e239558661611f80c110472ad");
		}}, "Yisus Collar", AccessoryType.COLLAR)
				.setStrength(2)
				.setIntelligence(2)
				.setDefense(2)
				.setHealth(2)
				.setLuck(2)
				.setRarity(Rarity.SPECIAL)
			);
		
	}
	
	@Command(names = "items")
	public void items(@Sender Player player, @OptArg("-1") String arg) {
		if(arg.equals("-1")) {
			player.sendMessage("disponibles hasta: "+creators.size());
			return;
		}
		
		ItemCreator creator = creators.get(Integer.valueOf(arg));
		if(creator == null) {
			player.sendMessage("Nope");
			return;
		}
		
		creator.give(player);
		player.sendMessage("§aObtuviste el item §c§l" + creator.getTagString("name"));
	}
	
	@Command(names = "enchants")
	public void enchantsList(CommandSender sender) {
		sender.sendMessage("§6Encantamientos:");
		for(ItemEnchantment enchant : core.getServices().getCache().getEnchantments().values()) {
			sender.sendMessage("§b"+enchant.getDisplayName() + "("+enchant.getID()+") MAX: "+enchant.getMaxLevel());
		}
	}
	
	@Command(names = "enchant")
	public void addEnchant(@Sender Player player, @OptArg("null") String arg, @OptArg("1") String lvl) {
		if(arg.equals("null")) {
			player.sendMessage("Introduce encantamiento.");
			return;
		}
		
		ItemEnchantment enchant = core.getServices().getEnchantment(arg);
		int level = Integer.parseInt(lvl);
		if(enchant == null) {
			player.sendMessage("No encontrado.");
			return;
		}
				
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(!enchant.isAllowed(hand)) {
			player.sendMessage("§cEste encantamiento no se puede aplicar en este item.");
			return;
		}
		
		if(level > enchant.getMaxLevel()) {
			player.sendMessage("§cNo puedes introducir un numero que supera nivel máximo ("+enchant.getMaxLevel()+").");
			return;
		} else if(level < 1) {
			player.sendMessage("§cEl nivel debe ser mayor a §b0§c.");
			return;
		}
		
		ItemStack enchantedItem = ItemUtils.addEnchantment(player, hand, arg, level);
		player.getInventory().setItemInMainHand(enchantedItem);
		player.sendMessage("§eListo!");
	}
	
	@Command(names = "remove-enchant")
	public void removeEnchant(@Sender Player player, @OptArg("null") String arg) {
		player.sendMessage("§cNot yet.");
	}
	
}
