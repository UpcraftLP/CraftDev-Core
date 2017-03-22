package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.common.CraftDevReference;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = CraftDevReference.MODID)
public class DeathMessageHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!CoreInternalConfig.showAllDeaths) return;
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (!world.isRemote && entity.hasCustomName() && !(entity instanceof EntityPlayer)) {
            // TODO: leave it like this or have the same formatting as players?
            Style formatting = new Style().setColor(TextFormatting.GRAY).setItalic(true);
            world.getMinecraftServer().getPlayerList().sendMessage(entity.getCombatTracker().getDeathMessage().setStyle(formatting));
        }
    }
}
