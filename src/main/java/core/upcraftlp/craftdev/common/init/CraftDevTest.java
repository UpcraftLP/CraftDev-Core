package core.upcraftlp.craftdev.common.init;

import java.util.HashMap;
import java.util.Map;

import core.upcraftlp.craftdev.API.common.ModRegistry;
import core.upcraftlp.craftdev.API.templates.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftDevTest {

	public static Item WRENCH = new ItemUniWrench();
	public static Block ALPHA_COBBLESTONE = new BlockAlphaCobblestone();
	
	private static Map<Block, CreativeTabs> blockMap = new HashMap<Block, CreativeTabs>();
	private static Map<Item, CreativeTabs> itemMap = new HashMap<Item, CreativeTabs>();
	
	public static void init() {
		blockMap.put(ALPHA_COBBLESTONE, Tabs.tabCraftDev);
		ModRegistry.registerBlocks(blockMap);
		
		itemMap.put(WRENCH, Tabs.tabCraftDev);
		ModRegistry.registerItems(itemMap);
	}
	
	public static void registerRenders() {
		ModRegistry.registerBlockRenders(blockMap);
		ModRegistry.registerItemRenders(itemMap);
		Tabs.tabCraftDev.setIconStack(new ItemStack(WRENCH));
	}
	
	public static class Tabs {
		
		public static CreativeTab tabCraftDev = new CreativeTab("tabCraftDev", false);
	}
}
