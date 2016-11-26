package core.upcraftlp.craftdev.common;

import java.util.Set;

import core.upcraftlp.craftdev.common.main.CraftDevReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class CoreConfigGuiFactory implements IModGuiFactory {
	
	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return CoreConfigGUI.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
	
	public static class CoreConfigGUI extends GuiConfig {
		
		public CoreConfigGUI(GuiScreen parent) {
		    super(parent, CoreInternalConfig.getEntries(),
		        CraftDevReference.MODID, false, false, I18n.format("config." + CraftDevReference.MODID + ".name"));
		  }
	}
	
}


