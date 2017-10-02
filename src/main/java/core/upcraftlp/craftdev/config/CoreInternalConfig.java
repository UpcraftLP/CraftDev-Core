package core.upcraftlp.craftdev.config;

import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Config(modid = CraftDevReference.MODID)
@Config.LangKey("config." + CraftDevReference.MODID + ".title")
public class CoreInternalConfig {

    //need to instantiate these as additional categories
    @Config.LangKey("config." + CraftDevReference.MODID + ".client")
    public static Client client = new Client();

    @Config.LangKey("config." + CraftDevReference.MODID + ".tweaks")
    public static Tweaks tweaks = new Tweaks();

    public static class Client {

        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        @Config.Comment("custom mob scale factor applied to all living entities (formula: 1.0 - (scale * 0.2f); 0.0 to disable, anything else will also turn entity shadows off); purely visual!")
        public double mobScaleFactor = 0.0F;

        @Config.Comment("enable/disable scaling for players. Will only take effect if mob scale factor > 0")
        public boolean scalePlayers = false;
    }

    public static class Tweaks {

        @Config.Comment("false to prevent double chests from being destroyed as a single chest")
        public boolean chestBreaker = true;

        @Config.Comment("show death messages of all living entities with custom name")
        public boolean showAllDeaths = true;

        @Config.Comment("creepers explode when on fire")
        public boolean burningCreepersExplode = true;

        @Config.Comment("fixes baby zombies not catching fire in sunlight. false to disable")
        public boolean zomBabiesBurn = true;

        //FIXME currently disabled
        //@Config.Comment("en/disbale crafting recipe for cobwebs; GAME MUST BE RESTARTED FOR THIS TO TAKE EFFECT")
        //public boolean webCrafting;

    }

    @Config.Comment("en/disable debug mode")
    public static boolean isDebugMode = false;

    @Config.Comment("announce beta updates in the update-checker")
    public static boolean announceBetaUpdates = false;

    @Mod.EventBusSubscriber(modid = CraftDevReference.MODID)
    public static class EventHandler {

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(CraftDevReference.MODID)) {
                ConfigManager.sync(CraftDevReference.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
