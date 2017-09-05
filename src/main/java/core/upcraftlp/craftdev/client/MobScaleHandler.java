package core.upcraftlp.craftdev.client;

import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod.EventBusSubscriber(modid = CraftDevReference.MODID, value = {CLIENT})
@SideOnly(CLIENT)
public class MobScaleHandler {

    public static boolean scalePlayers;
    public static float scale;

    @SideOnly(CLIENT)
    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre<?> event) {
        if (event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !scalePlayers) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX(), event.getY(), event.getZ());
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-event.getX(), -event.getY(), -event.getZ());
    }

    @SideOnly(CLIENT)
    @SubscribeEvent
    public void afterRenderLiving(RenderLivingEvent.Post<?> event) {
        if (event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !scalePlayers) return;
        GlStateManager.popMatrix();
    }
}
