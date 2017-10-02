package core.upcraftlp.craftdev.api.util;

import core.upcraftlp.craftdev.asm.CraftDevLoadingPlugin;
import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ModHelper {

	private static int entityID;

	/**
	 * Registers a Sound Event
	 * <p>
	 * <b>!</b> You have to keep track of that sound by yourself <b>!</b>
	 * </p>
	 * 
	 * @return the {@link SoundEvent}
	 */
	public static SoundEvent registerSound(String name, String modid) {
		ResourceLocation location = new ResourceLocation(modid, name);
		final SoundEvent event = new SoundEvent(location).setRegistryName(location);
		GameRegistry.findRegistry(SoundEvent.class).register(event);
		return event;
	}

	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param registryName
	 *            A unique Registry name for the entity
	 * @param entityClass
	 *            The entity's class
	 * @param entityName
	 *            The entity's unique name
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 */
	public static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass,
			String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, entityID++, "craftdev-core",
				trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param registryName
	 *            A unique Registry name for the entity
	 * @param entityClass
	 *            The entity's class
	 * @param entityName
	 *            The entity's unique name
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 * @param eggPrimary
	 *            Spawn Egg primary color
	 * @param eggSecondary
	 *            Spawn Egg secondary color
	 */
	public static void registerEntity(ResourceLocation registryName, Class<? extends Entity> entityClass,
			String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary,
			int eggSecondary) {
		registerEntity(registryName, entityClass, entityName, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityRegistry.registerEgg(registryName, eggPrimary, eggSecondary);
	}

	/**
	 * Register a new armor type.
	 * 
	 * @param name
	 *            registry name of the armor
	 * @param modid
	 *            modid
	 * @param durability
	 *            durability
	 * @param reductionAmounts
	 *            damage reduction amounts for each armor piece
	 * @param enchantability
	 *            enchantability
	 * @param soundOnEquip
	 *            {@link SoundEvent} that is played when equipping the armor
	 * @param toughness
	 *            armor points
	 * @return the {@link ArmorMaterial}
	 */
	public static ArmorMaterial registerArmor(String name, String modid, int durability, int[] reductionAmounts,
			int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addArmorMaterial(name, modid + ":" + name, durability, reductionAmounts, enchantability,
				soundOnEquip, toughness);
	}

	/**
	 * get the config File for CraftDev-Mods
	 */
	public static Configuration getConfigFile(FMLPreInitializationEvent event, String modid) {
        return new Configuration(new File(getConfigDir(event), modid + ".cfg"), null);
    }
	
	/**
     * get the config File for CraftDev-Mods
     * @param version the version of this config file, usually the mod version
     */
	public static Configuration getConfigFile(FMLPreInitializationEvent event, String modid, String version) {
		return new Configuration(new File(getConfigDir(event), modid + ".cfg"), version);
	}

	/**
	 * get the shared config directory
	 * 
	 * @return the config directory for CraftDev-Mods
	 */
	public static File getConfigDir(FMLPreInitializationEvent event) {
		return new File(event.getModConfigurationDirectory(), "craftdevmods");
	}

	/**
	 * @return whether debug mode is enabled or disabled
	 */
	public static boolean isDebugMode() {
		return CoreInternalConfig.isDebugMode;
	}

	/**
	 * @return whether you are in a dev environment or not
	 */
	public static boolean isDevEnvironment() {
		return CraftDevLoadingPlugin.isDeobfuscatedEnvironment();
	}
	
	private static final Map<String, Boolean> loadedMods = new HashMap<>();

	/**
	 * checks if a specific mod id is loaded, caches the result for efficiency
	 */
    public static boolean getIsModLoaded(String id) {
        if(!loadedMods.containsKey(id)) {
            loadedMods.put(id, Loader.isModLoaded(id));
        }
        return loadedMods.get(id);
    }

}
