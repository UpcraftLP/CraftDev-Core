package core.upcraftlp.craftdev.common;

import core.upcraftlp.craftdev.api.util.UpdateChecker;
import core.upcraftlp.craftdev.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static core.upcraftlp.craftdev.common.CraftDevReference.*;

@Mod(
        name = MODNAME,
        version = VERSION,
        acceptedMinecraftVersions = MCVERSIONS,
        modid = MODID,
        acceptableRemoteVersions = "*",
        updateJSON = UPDATE_JSON,
        certificateFingerprint = FORGE_FINGERPRINT
)
public class CraftDevCore {

    public static Logger log = LogManager.getLogger(MODID);
    
    @Mod.Metadata(MODID)
    public static ModMetadata metaData;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance
    private static CraftDevCore instance;

    public static CraftDevCore getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        UpdateChecker.registerMod(MODID);
        log.info("Pre-Initialization finished.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        log.info("Initialization finished.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        log.info("Post-Initialization finished.");
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        UpdateChecker.notifyServer();
        log.info("World initialization complete.");
    }

    @Mod.EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
        log.warn(event.getSource() + " has a mismatching fingerprint key!");
        if(event.isDirectory()) {
            log.warn(event.getSource() + " is a directory!");
        }
        else {
            log.warn("Expected: " + event.getExpectedFingerprint());
            StringBuilder res = new StringBuilder("Got " + event.getFingerprints().size() + " known keys: ");
            for (String fingerPrint : event.getFingerprints()) res.append("\n   - ").append(fingerPrint);
            log.warn(res.toString());
        }
    }
    
}
