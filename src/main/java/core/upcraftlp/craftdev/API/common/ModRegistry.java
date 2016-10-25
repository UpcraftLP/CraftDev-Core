package core.upcraftlp.craftdev.API.common;

import java.io.File;
import java.util.Map;

import core.upcraftlp.craftdev.common.CoreInternalRegistry;
import core.upcraftlp.craftdev.common.CoreInternalUpdateChecker;
import core.upcraftlp.craftdev.common.main.CraftDevCore;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Main Registry class.
 * Also keeps track of Mod-Updates.
 * <p></p>
 * <b>Contains:</b>
 * 	<li>Items</li>
 * 	<li>Blocks</li>
 * 	<li>Entities</li>
 * 	<li>Sounds</li>
 */
public class ModRegistry {

	private static int entityID;
	
	public static Configuration getConfigFile(FMLPreInitializationEvent event, String modid) {
		return new Configuration(new File(event.getModConfigurationDirectory() + File.separator + "craftdevmods" + File.separator + modid + ".cfg"));
	}
	
	/**
	 * Registers a mod to the update-checker
	 * @param modid ID of the mod
	 * @param currentVersion mod's current version
	 * @param internalUpdateUrl URL to query the version.txt file from
	 * @param publicUpdateUrl URL to show to users to update the mod
	 */
	public static void registerMod(String modid, String currentVersion, String internalUpdateUrl, String publicUpdateUrl) {
		CoreInternalUpdateChecker.add(modid, currentVersion, internalUpdateUrl, publicUpdateUrl);
	}

	public static void registerItems(Map<Item, CreativeTabs> items) {
		CoreInternalRegistry.registerItemsInternally(items);
	}
	
	public static void registerItemRenders(Map<Item, CreativeTabs> items) {
		CoreInternalRegistry.registerItemRendersInternally(items);
	}
	
	/**
	 * Registers blocks and also registers ItemBlocks.
	 * Creates ItemBlocks if necessary, set a Block's CreativeTab to null to prevent this.
	 * <p>
	 * Always register your Items <b>BEFORE</b> your Blocks!
	 * @param blocks
	 */
	public static void registerBlocks(Map<Block, CreativeTabs> blocks) {
		CoreInternalRegistry.registerBlocksInternally(blocks);
	}
	
	public static void registerBlockRenders(Map<Block, CreativeTabs> blocks) {
		CoreInternalRegistry.registerBlockRendersInternally(blocks);
	}
	
	/**
	 * Registers a Sound Event <p>
	 * <b>!</b> You have to keep track of that sound by yourself <b>!</b>
	 * </p>
	 * @param name
	 * @param modid
	 * @return
	 */
	public static SoundEvent registerSound(String name, String modid) {
		ResourceLocation location = new ResourceLocation(modid, name);
		SoundEvent event = new SoundEvent(location);
		GameRegistry.register(event, location);
		return event;
	}
	
	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param entityClass          The entity's class
	 * @param entityName           The entity's unique name
	 * @param trackingRange        The range at which MC will send tracking updates
	 * @param updateFrequency      The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 */
	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, CraftDevCore.getInstance(), trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param entityClass          The entity's class
	 * @param entityName           The entity's unique name
	 * @param trackingRange        The range at which MC will send tracking updates
	 * @param updateFrequency      The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 * @param eggPrimary           Spawn Egg primary color
	 * @param eggSecondary         Spawn Egg secondary color		
	 */
	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		registerEntity(entityClass, entityName, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
	}

}
