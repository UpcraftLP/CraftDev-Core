package core.upcraftlp.craftdev.client;

import core.upcraftlp.craftdev.api.util.UpdateChecker;
import core.upcraftlp.craftdev.common.CraftDevReference;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author UpcraftLP
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = CraftDevReference.MODID, value = {Side.CLIENT})
public class ClientUpdateHandler {

    private static boolean hasShown = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.ClientTickEvent event) {
        if(hasShown || Minecraft.getMinecraft().player == null) return;
        for (String modid : UpdateChecker.modsToCheck) {
            if (UpdateChecker.hasUpdate(modid, CoreInternalConfig.betaUpdates)) {
                String url = UpdateChecker.getResult(modid).url;
                String targetVersion = UpdateChecker.getLatestVersion(modid);
                ITextComponent link = new TextComponentString("here").setStyle(new Style().setColor(TextFormatting.BLUE).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(url).setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)))));
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Update available for " + FMLCommonHandler.instance().findContainerFor(modid).getName() + ", download v" + targetVersion + " ").appendSibling(link));
            }
        }
        hasShown = true;
    }

    @SubscribeEvent
    public static void onPlayerJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        hasShown = false;
    }
}
