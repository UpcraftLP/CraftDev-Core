package core.upcraftlp.craftdev.events;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import core.upcraftlp.craftdev.API.util.EventHandler;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ZomBabyFireHandler extends EventHandler {

    public ZomBabyFireHandler(Side effectiveSide) {
        super(effectiveSide);
    }

    private static final List<String> incompatibleIDs = Lists.newArrayList("quark");
    
    static final Random rand = new Random();
    
    @SubscribeEvent
    public void onZomBabyUpdate(LivingUpdateEvent event) {
        if(!CoreInternalConfig.zomBabiesBurn) return;
        if(event.getEntityLiving() instanceof EntityZombie) {
            EntityZombie zombie = (EntityZombie) event.getEntityLiving();
            World world = zombie.getEntityWorld();
            if(!zombie.isChild() || zombie instanceof EntityHusk || !zombie.isServerWorld() || !world.isDaytime()) return;
            BlockPos pos = zombie.getRidingEntity() instanceof EntityBoat ? new BlockPos(zombie.posX, Math.round(zombie.posY), zombie.posZ).up() : new BlockPos(zombie.posX, Math.round(zombie.posY), zombie.posZ);
            
            float brightness = zombie.getBrightness(1.0F);

            if (brightness > 0.5F && rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && world.canSeeSky(pos))
            {
                ItemStack itemstack = zombie.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.damageItem(rand.nextInt(2), zombie);

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            zombie.renderBrokenItemStack(itemstack);
                            zombie.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                }
                else {
                    zombie.setFire(8);
                }
            }
        }
    }
    
    @Override
    public Side[] getSides() {
        return ALL;
    }

    @Override
    public List<String> getIncompatibleModIDs() {
        return incompatibleIDs;
    }
}
