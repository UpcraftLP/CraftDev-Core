package core.upcraftlp.craftdev.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import core.upcraftlp.craftdev.API.common.ModHelper;
import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = CraftDevReference.MODID)
public class CoreInternalConfig {

    public static Configuration config;
    public static final String GENERAL = Configuration.CATEGORY_GENERAL;
    public static final String CLIENT = Configuration.CATEGORY_CLIENT;
    public static final String TWEAKS = "tweaks";
    public static boolean isDebugMode;
    public static boolean chestBreaker;
    public static boolean showAllDeaths;
    public static boolean burningCreepersExplode;
    public static boolean webCrafting;
    
    public static void init(FMLPreInitializationEvent event) {
        config = ModHelper.getConfigFile(event, "core");
        config.load();
        syncConfig();
    }

    public static void syncConfig() {
        /** Configuration Start **/
        
        /*GENERAL*/
        isDebugMode = config.getBoolean("debug mode", GENERAL, false, "en/disable debug mode");
        
        /*TWEAKS*/
        chestBreaker = config.getBoolean("chest breaker", TWEAKS, true, "false to prevent double chests from being destroyed as a single chest");
        showAllDeaths = config.getBoolean("show custom deaths", TWEAKS, true, "show death messages of all living entities with custom name");
        burningCreepersExplode = config.getBoolean("fiery creepers", TWEAKS, true, "creepers explode when on fire");
        webCrafting = config.getBoolean("web crafting", TWEAKS, true, "en/disbale crafting recipe for cobwebs");
        
        /*CLIENT*/
        float mobScaleFactor = config.getFloat("mob scale factor", CLIENT, 0.8f, 0.0f, 5.0f, "custom mob scale factor applied to all living entities (0.0 to disable, anything else will also turn entity shadows off)");
        boolean scalePlayers = config.getBoolean("scale Playes", CLIENT, false, "enable/disable scaling for players. Will only take effect if mob scale factor > 0");
        CraftDevCore.proxy.setScale(mobScaleFactor, scalePlayers);
        
        /** Configuration End **/
        if ( config.hasChanged() )
            config.save();
    }

    @SubscribeEvent
    public static void configChanged(OnConfigChangedEvent event) {
        if ( event.getModID().equals(CraftDevReference.MODID) ) {
            syncConfig();
        }
    }

    public static List<IConfigElement> getEntries() {
        List<IConfigElement> entries = new ArrayList<IConfigElement>();
        Set<String> categories = config.getCategoryNames();
        Iterator<String> i = categories.iterator();
        while (i.hasNext()) {
            String categoryName = i.next();
            ConfigCategory category = config.getCategory(categoryName);
            entries.addAll(new ConfigElement(category).getChildElements());
        }
        return entries;
    }

}
