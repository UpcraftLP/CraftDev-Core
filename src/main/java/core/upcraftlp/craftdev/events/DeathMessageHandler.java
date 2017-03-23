package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.API.util.EventHandler;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class DeathMessageHandler extends EventHandler {

    public DeathMessageHandler(Side effectiveSide) {
        super(effectiveSide);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDeath(LivingDeathEvent event) {
        if (!CoreInternalConfig.showAllDeaths) return;
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (!world.isRemote && entity.hasCustomName() && !(entity instanceof EntityPlayer)) {
            Style formatting = new Style().setColor(TextFormatting.GRAY).setItalic(true);
            world.getMinecraftServer().getPlayerList().sendMessage(entity.getCombatTracker().getDeathMessage().setStyle(formatting));
        }
    }

    @Override
    public Side[] getSides() {
        return ALL;
    }
}
