package core.upcraftlp.craftdev.common;

import core.upcraftlp.craftdev.API.common.ModHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CoreInternalConfig {

	public static void init(FMLPreInitializationEvent event) {
		Configuration config = ModHelper.getConfigFile(event, "core");
		config.load();
		/** Configuration Start **/
		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "nothing here atm");
		/** Configuration End **/
		config.save();
	}
	
}
