package core.upcraftlp.craftdev.api.structures.trees;

import core.upcraftlp.craftdev.api.structures.ITreeMapping;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TreeVanillaSpruce implements ITreeMapping {

	@Override
	public void placeLeaves(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, true));
	}

	@Override
	public void placeLog(World world, BlockPos pos, Axis axis) {
		world.setBlockState(pos, (Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(axis))));
	}

}
