package core.upcraftlp.craftdev.common;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(name = CraftDevReference.MODNAME, version = CraftDevReference.VERSION, acceptedMinecraftVersions = CraftDevReference.MCVERSIONS, modid = CraftDevReference.MODID, acceptableRemoteVersions = "*", canBeDeactivated = false, updateJSON = CraftDevReference.UPDATE_JSON, guiFactory = CraftDevReference.GUI_FACTORY)
public class CraftDevCore {

    private static final ModLogger log = ModLogger.create(CraftDevReference.MODID);
    
    @Mod.Metadata
    public static ModMetadata metaData;

    public static ModLogger getLogger() {
        return log;
    }
    
    @SidedProxy(clientSide = CraftDevReference.CLIENT_PROXY, serverSide = CraftDevReference.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance
    private static CraftDevCore instance;

    public static CraftDevCore getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        log.println("Pre-Initialization finished.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        log.println("Initialization finished.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        log.println("Post-Initialization finished.");
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        log.println("World initialization complete.");
    }
    
}
