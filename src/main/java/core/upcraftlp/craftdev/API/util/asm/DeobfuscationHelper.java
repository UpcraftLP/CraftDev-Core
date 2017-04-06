package core.upcraftlp.craftdev.API.util.asm;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.ASM.CraftDevLoadingPlugin;
import core.upcraftlp.craftdev.common.CraftDevCore;

public abstract class DeobfuscationHelper {

    // always deobfName : obfName
    private static final Map<String, String> deobfNames;
    private static final ModLogger log = CraftDevCore.getLogger();

    static {
        deobfNames = Maps.newHashMap();
        deobfNames.put("buildEnchantmentList", "func_77513_b");
        deobfNames.put("calcItemStackEnchantability", "func_77514_a");
        
        deobfNames.put("prepareScale", "func_188322_c");
        deobfNames.put("renderEntityOnFire", "func_76977_a");
        
        deobfNames.put("getTemperature", "func_185353_n");
        deobfNames.put("temperature", "field_185415_d");
    }

    public static final String EnchantmentHelper = "net.minecraft.enchantment.EnchantmentHelper";
    public static final String Entity_Render = "net.minecraft.client.renderer.entity.Render";
    public static final String Biome = "net.minecraft.world.biome.Biome";

    @Nullable
    public static String getName(String name) {
        if(!deobfNames.containsKey(name)) {
            log.errFatal(name + " has no obfuscated name mapping! preventing rash by disabling class transformer!");
            return null; //this is safe as here because someString#equals(null) will always return false, and thus disable the class transformer.
        }
        return !CraftDevLoadingPlugin.isDeobfuscatedEnvironment() ? deobfNames.get(name) : name;
    }
}
