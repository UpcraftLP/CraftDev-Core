package core.upcraftlp.craftdev.API.common;

import core.upcraftlp.craftdev.common.main.CraftDevCore;
import core.upcraftlp.craftdev.common.main.CraftDevReference;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ModHelper {

	public static void validateCodeBase(int codeBaseIn, int minimumBuild, String modid) {
		int codeBase = CraftDevReference.CODEBASE;
		if(codeBaseIn > codeBase) {
			CraftDevCore.getLogger().errFatal("THE CODEBASE OF " + CraftDevReference.MODID.toUpperCase() + " IS OUTDATED, PLEASE UPDATE " + CraftDevReference.MODID.toUpperCase() + "!");
			CraftDevCore.getLogger().errFatal("EXITING NOW.");
			FMLCommonHandler.instance().exitJava(1, false);
			return;
		}
		else if(codeBaseIn < codeBase) {
			CraftDevCore.getLogger().errFatal("THE CODEBASE OF " + modid.toUpperCase() + " IS OUTDATED, PLEASE UPDATE " + modid.toUpperCase() + "!");
			CraftDevCore.getLogger().errFatal("EXITING NOW.");
			FMLCommonHandler.instance().exitJava(1, false);
			return;
		}
		if(CraftDevReference.BUILD < minimumBuild) {
			CraftDevCore.getLogger().errFatal("MOD " + modid.toUpperCase() + "Requires minimum build number of " + minimumBuild + " (current is " + CraftDevReference.BUILD + "). PLEASE UPDATE " + CraftDevReference.MODID.toUpperCase() + "!");
			CraftDevCore.getLogger().errFatal("EXITING NOW.");
		}
	}
	
	public static ArmorMaterial registerArmor(String name, String modid, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addArmorMaterial(name, modid+ ":" + name, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}
}
