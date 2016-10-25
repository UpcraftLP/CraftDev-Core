package core.upcraftlp.craftdev.API.common;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ModHelper {

	public static ArmorMaterial registerArmor(String name, String modid, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addArmorMaterial(name, modid+ ":" + name, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}
}
