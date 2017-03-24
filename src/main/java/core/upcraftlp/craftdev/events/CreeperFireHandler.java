package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.API.util.EventHandler;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CreeperFireHandler extends EventHandler {

    public CreeperFireHandler(Side effectiveSide) {
        super(effectiveSide);
    }

    public static final String KEY_FUSE = "Fuse";
    
    @SubscribeEvent
    public void onBurningCreeper(LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(CoreInternalConfig.burningCreepersExplode && entity.isBurning() && entity instanceof EntityCreeper) {
            EntityCreeper creeper = (EntityCreeper) entity;
            if(!creeper.hasIgnited()) creeper.ignite();
        }
    }

    @Override
    public Side[] getSides() {
        return ALL;
    }
}
