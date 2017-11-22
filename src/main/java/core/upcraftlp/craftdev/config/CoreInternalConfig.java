package core.upcraftlp.craftdev.config;

import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(name = "craftdevmods/core", modid = CraftDevReference.MODID)
@Config.LangKey("config." + CraftDevReference.MODID + ".title")
public class CoreInternalConfig {

    @Config.Comment("en/disable debug mode")
    public static boolean isDebugMode = false;

    @Config.Comment("announce beta updates in the update-checker")
    public static boolean betaUpdates = false;

    @Config.Comment("various small tweaks")
    public static Tweaks TWEAKS = new Tweaks();

    @Config.Comment("client-only settings")
    public static Client CLIENT = new Client();

    public static class Tweaks {

            @Config.Comment("false to prevent double chests from being destroyed as a single chest")
            public static boolean chestBreaker = true;

            @Config.Comment("show death messages of all living entities with custom name")
            public static boolean showAllDeaths = true;

            @Config.Comment("creepers explode when on fire")
            public static boolean burningCreepersExplode = true;

            @Config.Comment("fixes baby zombies not catching fire in sunlight. false to disable")
            public static boolean zomBabiesBurn = true;

            @Config.RequiresMcRestart
            @Config.Comment("en/disbale crafting recipe for cobwebs")
            public static boolean webCrafting = true;

    }

    public static class Client {

        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        @Config.Comment("custom mob scale factor applied to all living entities (formula: 1.0 - (scale * 0.2f); 0.0 to disable, anything else will also turn entity shadows off); purely visual!")
        public static double mobScaleFactor = 0.5F;

        @Config.Comment("enable/disable scaling for players. Will only take effect if mob scale factor > 0")
        public static boolean scalePlayers = false;
    }

    @EventBusSubscriber(modid = CraftDevReference.MODID)
    public static class ConfigHandler {
        @SubscribeEvent
        public static void configChanged(OnConfigChangedEvent event) {
            if (event.getModID().equals(CraftDevReference.MODID)) {
                ConfigManager.sync(CraftDevReference.MODID, Config.Type.INSTANCE);
            }
            CraftDevCore.proxy.configChanged();
        }
    }

}
