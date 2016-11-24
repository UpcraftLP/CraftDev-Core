package core.upcraftlp.craftdev.common.init;

import java.util.List;

import core.upcraftlp.craftdev.API.templates.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemUniWrench extends Item {

	public ItemUniWrench() {
		super("universal_wrench");
		//TODO: wrench behaviour
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add(TextFormatting.RED + "WIP!");
	}

}
