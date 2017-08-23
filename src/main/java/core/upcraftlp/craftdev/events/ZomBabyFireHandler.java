package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.api.util.ModHelper;
import core.upcraftlp.craftdev.common.CraftDevReference;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = CraftDevReference.MODID)
public class ZomBabyFireHandler {

    private static final Random rand = new Random();
    
    @SubscribeEvent
    public static void onZomBabyUpdate(LivingUpdateEvent event) {
        if(!CoreInternalConfig.zomBabiesBurn || ModHelper.getIsModLoaded("quark")) return;
        if(event.getEntityLiving() instanceof EntityZombie) {
            EntityZombie zombie = (EntityZombie) event.getEntityLiving();
            World world = zombie.getEntityWorld();
            if(zombie.isBurning() || !zombie.isChild() || zombie instanceof EntityHusk || !zombie.isServerWorld() || !world.isDaytime()) return;
            BlockPos pos = zombie.getRidingEntity() instanceof EntityBoat ? new BlockPos(zombie.posX, Math.round(zombie.posY), zombie.posZ).up() : new BlockPos(zombie.posX, Math.round(zombie.posY), zombie.posZ);
            
            float brightness = zombie.getBrightness(1.0F);

            if (brightness > 0.5F && rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && world.canSeeSky(pos))
            {
                ItemStack itemstack = zombie.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.damageItem(rand.nextInt(3), zombie);
                        zombie.setItemStackToSlot(EntityEquipmentSlot.HEAD, itemstack);
                    }
                }
                else {
                    zombie.setFire(8);
                }
            }
        }
    }
}
