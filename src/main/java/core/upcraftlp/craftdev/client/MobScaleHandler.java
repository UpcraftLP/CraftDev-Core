package core.upcraftlp.craftdev.client;

import core.upcraftlp.craftdev.common.CraftDevReference;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.client.Minecraft;
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

    @SubscribeEvent
    @SideOnly(CLIENT)
    public static void onRenderLiving(RenderLivingEvent.Pre event) {
        if (event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !CoreInternalConfig.client.scalePlayers) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX(), event.getY(), event.getZ());
        double scale = getScale();
        Minecraft.getMinecraft().gameSettings.entityShadows = (scale == 1.0D); //disable shadows if the scale is active
        GlStateManager.scale(scale, scale, scale); // hardcoded maximum of 0.8D
        GlStateManager.translate(-event.getX(), -event.getY(), -event.getZ());
    }

    @SubscribeEvent
    @SideOnly(CLIENT)
    public static void afterRenderLiving(RenderLivingEvent.Post<?> event) {
        if (event.getEntity().isBeingRidden()) return;
        if (event.getEntity() instanceof EntityPlayer && !CoreInternalConfig.client.scalePlayers) return;
        GlStateManager.popMatrix();
    }

    public static float getScale() {
        return (float) (1.0F - (CoreInternalConfig.client.mobScaleFactor * 0.2F));
    }
}
