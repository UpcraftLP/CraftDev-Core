package core.upcraftlp.craftdev.api.util;

import com.google.common.collect.Lists;
import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

/**
 * @author UpcraftLP
 * Check for mod updates or register them for automated update announcements
 */
public class UpdateChecker {

    public static final List<String> modsToCheck = Lists.newArrayList();

    /**
     * used to register a mod for update notifications
     * @param modid the modid String
     */
    public static void registerMod(String modid) {
        modsToCheck.add(modid);
    }

    public static boolean hasUpdate(String modid, boolean betaVersions) {
        ForgeVersion.CheckResult result = getResult(modid);
        if(result.status == ForgeVersion.Status.PENDING) {
            CraftDevCore.log.warn("Cannot check for updates, found status: PENDING!");
            return false;
        }
        return betaVersions ? result.status.isAnimated() : result.status == ForgeVersion.Status.OUTDATED;
    }

    public static ForgeVersion.CheckResult getResult(String modid) {
        return ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(modid));
    }

    public static String getLatestVersion(String modid) {
        return getResult(modid).target.toString();
    }

    public static void notifyServer() {
        for (String modid : modsToCheck) {
            if (hasUpdate(modid, CoreInternalConfig.betaUpdates)) {
                String url = getResult(modid).url;
                CraftDevCore.log.warn("There's an update available for {}" + (StringUtils.isNullOrEmpty(url) ? "": ", download version {} here: {}"), FMLCommonHandler.instance().findContainerFor(modid).getName(), getLatestVersion(modid), url);
            }
        }
    }
}
