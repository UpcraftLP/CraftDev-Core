package core.upcraftlp.craftdev.API.config;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public abstract class AbstractConfigGuiFactory implements IModGuiFactory {

    @Deprecated
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    /**
     * return a dummy implementation of {@link GuiConfig} or {@code null} if no GUI is desired
     */
    @Override
    public abstract Class<? extends GuiScreen> mainConfigGuiClass();

    @Deprecated
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Deprecated
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
    
}
