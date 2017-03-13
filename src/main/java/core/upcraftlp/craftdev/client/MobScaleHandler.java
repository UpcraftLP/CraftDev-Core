package core.upcraftlp.craftdev.client;

import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value= {Side.CLIENT}, modid = CraftDevReference.MODID)
public class MobScaleHandler {

    public static boolean isEnabled;
    public static boolean scalePlayers;
    public static float scale;

    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Pre<?> event) {
        if (!isEnabled || event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !scalePlayers) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX(), event.getY(), event.getZ());
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-event.getX(), -event.getY(), -event.getZ());
    }

    @SubscribeEvent
    public static void afterRenderLiving(RenderLivingEvent.Post<?> event) {
        if (!isEnabled || event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !scalePlayers) return;
        GlStateManager.popMatrix();
    }

}
