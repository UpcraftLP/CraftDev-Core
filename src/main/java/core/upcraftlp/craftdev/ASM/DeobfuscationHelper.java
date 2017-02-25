package core.upcraftlp.craftdev.ASM;

import java.util.Map;

import com.google.common.collect.Maps;

import core.upcraftlp.craftdev.API.common.ModHelper;

public abstract class DeobfuscationHelper {

    // always deobfName : obfName
    private static final Map<String, String> deobfNames;

    static {
        deobfNames = Maps.newHashMap();
        deobfNames.put("buildEnchantmentList", "func_77513_b");
        deobfNames.put("calcItemStackEnchantability", "func_77514_a");
        
        deobfNames.put("prepareScale", "func_188322_c");
    }

    public static final String EnchantmentHelper = "net.minecraft.enchantment.EnchantmentHelper";
    public static final String EnchantmentHelper_Sig = "(Lnet/minecraft/item/ItemStack;I)I";
    
    public static final String RenderLiving = "net.minecraft.client.renderer.entity.RenderLiving";

    public static String getName(String name) {
        return !ModHelper.isDebugMode() ? deobfNames.get(name) : name;
    }
}
