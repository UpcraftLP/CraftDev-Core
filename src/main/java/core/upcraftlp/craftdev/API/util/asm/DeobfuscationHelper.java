package core.upcraftlp.craftdev.api.util.asm;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import core.upcraftlp.craftdev.api.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.asm.CraftDevLoadingPlugin;
import core.upcraftlp.craftdev.common.CraftDevCore;

public abstract class DeobfuscationHelper {

    private static final Map<String, String> deobfNames; // always deobfName : obfName
    private static final ModLogger log = CraftDevCore.getLogger();

    static {
        deobfNames = Maps.newConcurrentMap();
        addMapping("buildEnchantmentList", "func_77513_b");
        addMapping("calcItemStackEnchantability", "func_77514_a");

        addMapping("prepareScale", "func_188322_c");
        addMapping("renderEntityOnFire", "func_76977_a");
        
        addMapping("getFloatTemperature", "func_180626_a");
        addMapping("temperature", "field_185415_d");
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
        return CraftDevLoadingPlugin.isDeobfuscatedEnvironment() ? name : deobfNames.get(name);
    }

    /**
     * add missing obfuscation mappings on the fly
     */
    public static void addMapping(String deobfuscatedName, String obfuscatedName) {
        if(!deobfNames.containsKey(deobfuscatedName)) deobfNames.put(deobfuscatedName, obfuscatedName);
        else log.println("tried to add duplicate mapping: [" + deobfuscatedName + ":" + obfuscatedName + "]");
    }
}
