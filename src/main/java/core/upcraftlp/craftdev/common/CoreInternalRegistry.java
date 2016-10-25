package core.upcraftlp.craftdev.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import core.upcraftlp.craftdev.common.main.CraftDevCore;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CoreInternalRegistry {

	public static void registerItemsInternally(Map<Item, CreativeTabs> itemMap) {
		Iterator<Entry<Item, CreativeTabs>> i = itemMap.entrySet().iterator();
		while(i.hasNext()) {
			Entry<Item, CreativeTabs> e = i.next();
			Item item = e.getKey();
			if(item.getRegistryName() != null) {
				GameRegistry.register(item);
			} 
			else
			{
				CraftDevCore.getLogger().println("Unable to register " + item + ": no registry name found!");
				i.remove();
			}
			
		}
	}
	
	public static void registerBlocksInternally(Map<Block, CreativeTabs> blockMap) {
		Iterator<Entry<Block, CreativeTabs>> i = blockMap.entrySet().iterator();
		while(i.hasNext()) {
			Entry<Block, CreativeTabs> e = i.next();
			Block block = e.getKey();
			CreativeTabs tab = e.getValue();
			if(block.getRegistryName() != null) {
				GameRegistry.register(block);
				if(tab != null) block.setCreativeTab(tab);
			}
			else
			{
				CraftDevCore.getLogger().println("Unable to register " + block + ": no registry name found!");
			}
			
		}
	}
	
	/**Rendering Registry**/
	
	public static void registerBlockRendersInternally(Map<Block, CreativeTabs> blockMap) {
		Iterator<Entry<Block, CreativeTabs>> i = blockMap.entrySet().iterator();
		while(i.hasNext()) {
			Entry<Block, CreativeTabs> e = i.next();
			Block block = e.getKey();
			CreativeTabs tab = e.getValue();
			
			//nullcheck to see if itemblock is wanted
			if(tab != null) {
				List<ItemStack> stacks = new ArrayList<ItemStack>();
				Item itemBlock = Item.getItemFromBlock(block);
				if(itemBlock == null) {
					itemBlock = new ItemBlock(block);
					itemBlock.setRegistryName(block.getRegistryName());
					itemBlock.setUnlocalizedName(block.getUnlocalizedName().substring(5));
					GameRegistry.register(itemBlock);
				}
				block.getSubBlocks(Item.getItemFromBlock(block), tab, stacks);
				if(stacks.size() > 1) {
					int meta = 0;
					for (ItemStack itemStack : stacks) {
						ModelLoader.setCustomModelResourceLocation(itemStack.getItem(), itemStack.getItemDamage(), new ModelResourceLocation(block.getRegistryName(), "inventory_" + meta));
						meta++;
					}
				}
				else ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
				block.setCreativeTab(tab);
				itemBlock.setCreativeTab(tab);
			}
		}
		
	}
	
	public static void registerItemRendersInternally(Map<Item, CreativeTabs> itemMap) {
		Iterator<Entry<Item, CreativeTabs>> i = itemMap.entrySet().iterator();
		while(i.hasNext()) {
			Entry<Item, CreativeTabs> e = i.next();
			Item item = e.getKey();
			CreativeTabs tab = e.getValue();
			if(item instanceof core.upcraftlp.craftdev.API.templates.Item && ((core.upcraftlp.craftdev.API.templates.Item) item).getSubItemCount() > 0) {
				
				for(int meta = 0; meta < ((core.upcraftlp.craftdev.API.templates.Item) item).getSubItemCount(); meta++) {
					ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory_" + meta));
				}
				item.setCreativeTab(tab);
			}
			else
			{
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
			item.setCreativeTab(tab);
		}
	}
}
