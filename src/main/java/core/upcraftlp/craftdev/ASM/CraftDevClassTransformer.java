package core.upcraftlp.craftdev.ASM;

import java.util.Map;

import core.upcraftlp.craftdev.API.util.asm.ClassTransform;
import core.upcraftlp.craftdev.API.util.asm.TransformerUtils;
import core.upcraftlp.craftdev.ASM.tweaks.TweakEnchantHelper;
import core.upcraftlp.craftdev.ASM.tweaks.TweakRenderLiving;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

public class CraftDevClassTransformer implements IClassTransformer, IFMLCallHook {

    @Override
    public byte[] transform(String name, String deobfClassName, byte[] basicClass) {
        return TransformerUtils.transformClass(deobfClassName, basicClass);
    }
    
    public static final ClassTransform ENCHANTHELPER_TRANSFORM = new TweakEnchantHelper();
    public static final ClassTransform RENDERLIVING_TRANSFORM = new TweakRenderLiving();
    
    @Override
    public Void call() throws Exception {
       return null;
    }
    @Override
    public void injectData(Map<String, Object> data) {}

}
