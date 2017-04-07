package core.upcraftlp.craftdev.proxy;

import java.util.Arrays;

import core.upcraftlp.craftdev.client.MobScaleHandler;
import core.upcraftlp.craftdev.common.CraftDevReference;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ModMetadata data = event.getModMetadata();
        data.autogenerated = false;
        data.authorList = Arrays.asList(CraftDevReference.authors);
        data.credits = TextFormatting.GOLD + CraftDevReference.CREDITS;
        data.modId = CraftDevReference.MODID;
        data.name = CraftDevReference.MODNAME;
        data.description = TextFormatting.AQUA + CraftDevReference.MOD_DESCRIPTION;
        data.url = CraftDevReference.UPDATE_URL;
        data.updateJSON = CraftDevReference.UPDATE_JSON;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    @Override
    public void configChanged() {
        super.configChanged();
        MobScaleHandler.scalePlayers = CoreInternalConfig.scalePlayers;
        MobScaleHandler.scale = 1.0f - (CoreInternalConfig.mobScaleFactor * 0.2f); // hardcoded maximum of 0.8f
        Minecraft.getMinecraft().gameSettings.entityShadows = MobScaleHandler.scale == 1.0f; //disable shadows if the scale is active
    }
}