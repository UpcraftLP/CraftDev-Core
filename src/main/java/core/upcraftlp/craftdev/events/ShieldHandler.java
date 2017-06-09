package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.API.templates.ItemShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ShieldHandler {

    @SubscribeEvent
    public static void attackEvent(LivingAttackEvent e) {
        float damage = e.getAmount();
        if(damage > 0 && e.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntityLiving();
            if(player.world.isRemote) return;
            ItemStack activeItemStack = player.getActiveItemStack();
            if(!activeItemStack.isEmpty() && activeItemStack.getItem() instanceof ItemShield) { //! this is not net.minecraft.item.ItemShield, as that would lead to damaging vanilla or other modded shields twice.
                activeItemStack.damageItem(1 + MathHelper.floor(damage), player);
                if (activeItemStack.isEmpty()) {
                    EnumHand hand = player.getActiveHand();
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, activeItemStack, hand);
                    player.setHeldItem(hand, ItemStack.EMPTY);
                    player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 0.6F + player.getRNG().nextFloat() * 0.4F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                }
            }
        }
    }
}
