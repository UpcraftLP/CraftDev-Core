package core.upcraftlp.craftdev.api.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;

/**
 * @author UpcraftLP
 */
public class BlockLog extends Block {

    public static final PropertyEnum<net.minecraft.block.BlockLog.EnumAxis> LOG_AXIS = net.minecraft.block.BlockLog.LOG_AXIS;

    public BlockLog(String name) {
        super("log_" + name, Material.WOOD, true);
        this.setDefaultState(this.getDefaultState().withProperty(LOG_AXIS, net.minecraft.block.BlockLog.EnumAxis.Y));
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, net.minecraft.block.BlockLog.EnumAxis.values()[facing.getAxis().ordinal()]);
    }

    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LOG_AXIS, net.minecraft.block.BlockLog.EnumAxis.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LOG_AXIS).ordinal();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        int b0 = 4;
        int i = b0 - 1;
        if (worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i)))
        {
            Iterator<BlockPos> iterator = BlockPos.getAllInBox(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)).iterator();

            while (iterator.hasNext())
            {
                BlockPos blockpos1 = iterator.next();
                IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

                if (iblockstate1.getBlock().isLeaves(iblockstate1, worldIn, blockpos1))
                {
                    iblockstate1.getBlock().beginLeavesDecay(iblockstate1, worldIn, blockpos1);
                }
            }
        }
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 5; //flammability of vanilla wood
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 5; //spread speed of vanilla wood
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }

}
