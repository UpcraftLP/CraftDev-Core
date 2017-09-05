package core.upcraftlp.craftdev.config;

import core.upcraftlp.craftdev.api.config.AbstractConfigGuiFactory;
import core.upcraftlp.craftdev.api.config.ConfigHelper;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CoreConfigGuiFactory extends AbstractConfigGuiFactory {

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new CoreConfigGUI(parentScreen);
    }

    public static class CoreConfigGUI extends GuiConfig {

        public CoreConfigGUI(GuiScreen parent) {
            super(parent, ConfigHelper.getEntries(CoreInternalConfig.config), CraftDevReference.MODID, false, false, I18n.format("config." + CraftDevReference.MODID + ".name"));
        }
    }

}
