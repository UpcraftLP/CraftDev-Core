package core.upcraftlp.craftdev.API.common;

import java.io.File;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModHelper {
	
	public static ArmorMaterial registerArmor(String name, String modid, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addArmorMaterial(name, modid+ ":" + name, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}
	
	public static Configuration getConfigFile(FMLPreInitializationEvent event, String modid) {
		return new Configuration(new File(event.getModConfigurationDirectory() + File.separator + "craftdevmods" + File.separator + modid + ".cfg"));
	}
}
