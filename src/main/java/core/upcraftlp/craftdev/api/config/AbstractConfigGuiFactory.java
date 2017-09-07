package core.upcraftlp.craftdev.api.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

@SideOnly(Side.CLIENT)
public abstract class AbstractConfigGuiFactory implements IModGuiFactory {

    @Deprecated
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    @Override
    public void initialize(Minecraft minecraftInstance) {
        //NO-OP
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    /**
     * @return a new instance of the config screen, usually inheriting from {@link net.minecraftforge.fml.client.config.GuiConfig}
     */
    @Override
    public abstract GuiScreen createConfigGui(GuiScreen parentScreen);

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return null;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

}
