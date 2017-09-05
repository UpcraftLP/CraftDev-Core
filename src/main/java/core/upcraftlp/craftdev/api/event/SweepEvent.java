package core.upcraftlp.craftdev.api.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import java.util.List;

/**
 * (c)2017 UpcraftLP
 *
 * This Event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS} when a player tries to use a sweep-attack.
 */
@Cancelable
public class SweepEvent extends PlayerEvent {

    private ItemStack item;
    private Entity target;
    private final List<EntityLivingBase> affectedEntities;

    public SweepEvent(EntityPlayer player, Entity target) {
        super(player);
        this.item = player.getHeldItemMainhand();
        this.target = target;
        affectedEntities = player.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D));
        affectedEntities.remove(this.getEntityPlayer());
        if(target instanceof EntityLivingBase) affectedEntities.remove(target);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    /**
     * @return the {@link net.minecraft.item.ItemSword} that was used to attack
     */
    public ItemStack getHeldItem() {
        return this.item;
    }

    public Entity getTargetEntity() {
        return this.target;
    }

    /**
     * @return a List of Entities around the target entity, excluding the target entity and the player.
     *
     */
    public List<EntityLivingBase> getAffectedEntities() {
        return this.affectedEntities;
    }
}
