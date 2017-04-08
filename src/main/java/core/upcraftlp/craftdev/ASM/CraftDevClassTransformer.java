package core.upcraftlp.craftdev.ASM;

import java.util.Map;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.API.util.asm.ClassTransform;
import core.upcraftlp.craftdev.API.util.asm.TransformerUtils;
import core.upcraftlp.craftdev.ASM.tweaks.TweakBiome;
import core.upcraftlp.craftdev.ASM.tweaks.TweakEnchantHelper;
import core.upcraftlp.craftdev.ASM.tweaks.TweakEntityFireRender;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

public class CraftDevClassTransformer implements IClassTransformer, IFMLCallHook {

    static {
        new TweakEnchantHelper();
        new TweakBiome();
    }
    
    public static final ClassTransform RENDERLIVING_TRANSFORM = new TweakEntityFireRender(); //needs to be done this way because of the annotation
    
    private static final ModLogger log = CraftDevCore.getLogger();

    @Override
    public byte[] transform(String name, String deobfClassName, byte[] basicClass) {
        return TransformerUtils.transformClass(deobfClassName, basicClass);
    }
    
    
    
    
    @Override
    public Void call() throws Exception {
       log.println("Hello Minecraft!");
       if(CraftDevLoadingPlugin.isDeobfuscatedEnvironment()) log.println("deobfuscated environment detected!");
       //TODO: funny Strings ;)
       return null; //return null or throw an exception
    }
    
    /**
     * {@link IFMLCallHook#injectData(Map)}
     */
    @Override
    public void injectData(Map<String, Object> data) {}

}
