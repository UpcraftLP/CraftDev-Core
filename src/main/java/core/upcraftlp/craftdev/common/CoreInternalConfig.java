package core.upcraftlp.craftdev.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import core.upcraftlp.craftdev.API.common.ModHelper;
import core.upcraftlp.craftdev.common.main.CraftDevReference;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class CoreInternalConfig {

	public static Configuration config;
	public static boolean isDebugMode;
	
	public static void init(FMLPreInitializationEvent event) {
		config = ModHelper.getConfigFile(event, "core");
		config.load();
		syncConfig();
	}
	
	public static void syncConfig() {
		/** Configuration Start **/
		isDebugMode = config.getBoolean("debug", Configuration.CATEGORY_GENERAL, false, "en/disable debug mode");
		/** Configuration End **/
		if(config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public static void configChanged(OnConfigChangedEvent event) {
		if(event.getModID().equals(CraftDevReference.MODID)) {
			syncConfig();
		}
	}
	
	public static List<IConfigElement> getEntries() {
		List<IConfigElement> entries = new ArrayList<IConfigElement>();
		Set<String> categories = config.getCategoryNames();
		Iterator<String> i = categories.iterator();
		while(i.hasNext()) {
			String categoryName = i.next();
			ConfigCategory category = config.getCategory(categoryName);
			entries.addAll(new ConfigElement(category).getChildElements());
		}
		return entries;
	}
	
}
