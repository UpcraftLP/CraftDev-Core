package core.upcraftlp.craftdev.API.templates;

import java.util.Set;
import java.util.TreeSet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ItemMultiTool extends ItemTool {

	public ItemMultiTool(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn) {
		super(name, attackDamageIn, attackSpeedIn, materialIn);
		this.setMaxDamage(((this.toolMaterial.getMaxUses() - 1) * 3) + 1);
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		Set<String> toolClasses = new TreeSet<String>();
		toolClasses.add("pickaxe");
		toolClasses.add("axe");
		toolClasses.add("shovel");
		toolClasses.add("Axe");
		toolClasses.add("Pickaxe");
		toolClasses.add("Shovel");
		return toolClasses;
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return efficiencyOnProperMaterial;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return state.getBlock().getHarvestLevel(state) <= this.toolMaterial.getHarvestLevel();
	}

}
