package core.upcraftlp.craftdev.ASM;

import java.util.Map;

import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.11")
public class CraftDevLoadingPlugin implements IFMLLoadingPlugin {

    private static boolean isDebofuscatedEnvironment;

    public static boolean isDeobfuscationEnabled() {
        return isDebofuscatedEnvironment;
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
        isDebofuscatedEnvironment = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
