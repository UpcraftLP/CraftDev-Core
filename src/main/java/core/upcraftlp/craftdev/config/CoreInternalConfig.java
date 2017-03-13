package core.upcraftlp.craftdev.config;

import core.upcraftlp.craftdev.API.common.ConfigHelper.Categories;
import core.upcraftlp.craftdev.API.common.ModHelper;
import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = CraftDevReference.MODID)
public class CoreInternalConfig {

    public static Configuration config;
    
    public static boolean isDebugMode;
    public static boolean chestBreaker;
    public static boolean showAllDeaths;
    public static boolean burningCreepersExplode;
    public static boolean webCrafting;
    
    public static float mobScaleFactor;
    public static boolean scalePlayers;
    
    public static void init(FMLPreInitializationEvent event) {
        config = ModHelper.getConfigFile(event, "core");
        config.load();
        syncConfig();
    }
    
    public static void syncConfig() {
        /** Configuration Start **/
        
        /*GENERAL*/
        isDebugMode = config.getBoolean("debug mode", Categories.GENERAL, false, "en/disable debug mode");
        
        /*TWEAKS*/
        chestBreaker = config.getBoolean("chest breaker", Categories.TWEAKS, true, "false to prevent double chests from being destroyed as a single chest");
        showAllDeaths = config.getBoolean("show custom deaths", Categories.TWEAKS, true, "show death messages of all living entities with custom name");
        burningCreepersExplode = config.getBoolean("fiery creepers", Categories.TWEAKS, true, "creepers explode when on fire");
        webCrafting = config.getBoolean("web crafting", Categories.TWEAKS, true, "en/disbale crafting recipe for cobwebs");
        
        /*CLIENT*/
        mobScaleFactor = config.getFloat("mob scale factor", Categories.CLIENT, 0.8f, 0.0f, 5.0f, "custom mob scale factor applied to all living entities (0.0 to disable, anything else will also turn entity shadows off)");
        scalePlayers = config.getBoolean("scale Playes", Categories.CLIENT, false, "enable/disable scaling for players. Will only take effect if mob scale factor > 0");
        CraftDevCore.proxy.configChanged();
        
        /** Configuration End **/
        if(config.hasChanged()) config.save();
    }

    @SubscribeEvent
    public static void configChanged(OnConfigChangedEvent event) {
        if ( event.getModID().equals(CraftDevReference.MODID) ) {
            syncConfig();
        }
    }

}
