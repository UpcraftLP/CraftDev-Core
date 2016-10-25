package core.upcraftlp.craftdev.common;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoreInternalUpdateEventHandler {

	public static void init()
	{
		if(CoreInternalConfig.enableUpdateChecker) MinecraftForge.EVENT_BUS.register(new CoreInternalUpdateEventHandler());
	}
	
	private List<String> hasShownUpdate = new ArrayList<String>();
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void UpdateCheck(TickEvent.ClientTickEvent event)
	{
		for (String modid : CoreInternalUpdateChecker.mods) {
			if(CoreInternalUpdateChecker.isNewVersionAvailable(modid) && !hasShownUpdate.contains(modid) && Minecraft.getMinecraft().currentScreen == null)
			{
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new TextComponentString(ChatFormatting.GOLD + "Update Available for " + modid.substring(0, 1).toUpperCase() + modid.substring(1) + "!"));
				ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, CoreInternalUpdateChecker.publicUrls.get(modid));
			    Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Download version " + ChatFormatting.DARK_AQUA + CoreInternalUpdateChecker.getLatest(modid) + ChatFormatting.RESET + " from " + ChatFormatting.BLUE + "here" + ChatFormatting.RESET + ".").setStyle(new Style().setClickEvent(versionCheckChatClickEvent)));
				hasShownUpdate.add(modid);
			}
		}
	}
}
