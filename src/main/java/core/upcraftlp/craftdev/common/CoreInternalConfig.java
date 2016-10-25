package core.upcraftlp.craftdev.common;

import core.upcraftlp.craftdev.API.common.ModRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CoreInternalConfig {

	public static boolean enableUpdateChecker;
	public static boolean enableAlphaBuilds;

	public static void init(FMLPreInitializationEvent event) {
		Configuration config = ModRegistry.getConfigFile(event, "core");
		config.load();
		/** Configuration Start **/
			enableUpdateChecker = config.getBoolean("enableUpdateChecker", Configuration.CATEGORY_GENERAL, true, "enable/disable Update Checker for these mods:");
			//TODO: alpha update-checker!
			//enableAlphaBuilds = config.getBoolean("enableAlphaBuilds", Configuration.CATEGORY_GENERAL, false, "enable nightly release versions, use this at your own peril");
			/** Configuration End **/
		config.save();
	}
	
}
