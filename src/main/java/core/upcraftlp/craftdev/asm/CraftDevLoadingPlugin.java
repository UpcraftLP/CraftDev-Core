package core.upcraftlp.craftdev.asm;

import java.util.Map;

import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(Loader.MC_VERSION)
@IFMLLoadingPlugin.TransformerExclusions({CraftDevReference.BASE_PACKAGE})
public class CraftDevLoadingPlugin implements IFMLLoadingPlugin {

    private static boolean runtimeDeobfuscation;
    
    public static boolean isDeobfuscatedEnvironment() {
        return !runtimeDeobfuscation; //yep, this makes sense
    }
    
    @Override
    public String[] getASMTransformerClass() {
        return CraftDevReference.TRANSFORMER_CLASSES;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return CraftDevReference.TRANSFORMER_HOLDER;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        runtimeDeobfuscation = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
