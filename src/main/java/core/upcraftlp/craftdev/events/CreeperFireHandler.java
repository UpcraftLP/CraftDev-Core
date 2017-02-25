package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.common.CraftDevReference;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = CraftDevReference.MODID)
public class CreeperFireHandler {

    public static final String KEY_FUSE = "Fuse";
    
    @SubscribeEvent
    public static void onBurningCreeper(LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(CoreInternalConfig.burningCreepersExplode && entity.isBurning() && entity instanceof EntityCreeper) {
            EntityCreeper creeper = (EntityCreeper) entity;
            if(!creeper.hasIgnited()) creeper.ignite();
        }
    }
}
