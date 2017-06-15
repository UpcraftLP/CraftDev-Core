package core.upcraftlp.craftdev.config;

import core.upcraftlp.craftdev.API.util.ModHelper;
import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static core.upcraftlp.craftdev.API.config.ConfigHelper.Categories.*;

@EventBusSubscriber(modid = CraftDevReference.MODID)
public class CoreInternalConfig {

    public static Configuration config;

    public static boolean isDebugMode;
    public static boolean chestBreaker;
    public static boolean showAllDeaths;
    public static boolean burningCreepersExplode;
    public static boolean zomBabiesBurn;
    public static boolean webCrafting;

    public static float mobScaleFactor;
    public static boolean scalePlayers;

    public static void init(FMLPreInitializationEvent event) {
        config = ModHelper.getConfigFile(event, "core", CraftDevReference.VERSION).setCategoryComment(CLIENT, "client-only tweaks").setCategoryComment(GENERAL, "general settings").setCategoryComment(TWEAKS, "various small tweaks");
        config.load();
        syncConfig();
    }

    public static void syncConfig() {
        /** Configuration Start **/

        /* GENERAL */
        isDebugMode = config.getBoolean("debug mode", GENERAL.toString(), false, "en/disable debug mode");

        /* TWEAKS */
        chestBreaker = config.getBoolean("chest breaker", TWEAKS, true, "false to prevent double chests from being destroyed as a single chest");
        showAllDeaths = config.getBoolean("show custom deaths", TWEAKS, true, "show death messages of all living entities with custom name");
        burningCreepersExplode = config.getBoolean("fiery creepers", TWEAKS, true, "creepers explode when on fire");
        zomBabiesBurn = config.getBoolean("ZomBabies burn", TWEAKS, true, "fixes baby zombies not catching fire in sunlight. false to disable");
        
        webCrafting = config.getBoolean("web crafting", TWEAKS, true, "en/disbale crafting recipe for cobwebs; GAME MUST BE RESTARTED FOR THIS TO TAKE EFFECT");

        /* CLIENT */
        mobScaleFactor = config.getFloat("mob scale factor", CLIENT, 0.0f, 0.0f, 1.0f, "custom mob scale factor applied to all living entities (formula: 1.0 - (scale * 0.2f); 0.0 to disable, anything else will also turn entity shadows off); purely visual!");
        scalePlayers = config.getBoolean("scale Playes", CLIENT, false, "enable/disable scaling for players. Will only take effect if mob scale factor > 0");
        CraftDevCore.proxy.configChanged();

        /** Configuration End **/
        if (config.hasChanged()) config.save();
    }

    @SubscribeEvent
    public static void configChanged(OnConfigChangedEvent event) {
        if (event.getModID().equals(CraftDevReference.MODID)) {
            syncConfig();
        }
    }

}
