package core.upcraftlp.craftdev.client;

import core.upcraftlp.craftdev.API.util.EventHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MobScaleHandler extends EventHandler {

    public MobScaleHandler(Side effectiveSide) {
        super(effectiveSide);
    }

    public static boolean scalePlayers;
    public static float scale;

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre<?> event) {
        if (event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !scalePlayers) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX(), event.getY(), event.getZ());
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-event.getX(), -event.getY(), -event.getZ());
    }

    @SubscribeEvent
    public void afterRenderLiving(RenderLivingEvent.Post<?> event) {
        if (event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !scalePlayers) return;
        GlStateManager.popMatrix();
    }

    @Override
    public Side[] getSides() {
        return CLIENT;
    }

}
