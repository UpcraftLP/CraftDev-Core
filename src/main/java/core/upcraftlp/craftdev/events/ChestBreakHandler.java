package core.upcraftlp.craftdev.events;

import core.upcraftlp.craftdev.API.util.EventHandler;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ChestBreakHandler extends EventHandler {

    public ChestBreakHandler(Side effectiveSide) {
        super(effectiveSide);
    }

    @SubscribeEvent
    public void onChestBroken(BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = event.getWorld();
        if (world.isRemote || player instanceof FakePlayer || player.isSneaking() || !CoreInternalConfig.chestBreaker) return;
        if (event.getState().getBlock() instanceof BlockChest) {
            BlockPos pos = event.getPos();
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                BlockPos blockpos = pos.offset(enumfacing);
                IBlockState iblockstate = world.getBlockState(blockpos);
                if (iblockstate.getBlock() == event.getState().getBlock()) {
                    iblockstate.getBlock().harvestBlock(world, player, blockpos, iblockstate, world.getTileEntity(blockpos), player.getHeldItemMainhand());
                    world.setBlockToAir(blockpos);
                    break;
                }
            }
        }
    }

    @Override
    public Side[] getSides() {
        return ALL;
    }
}
