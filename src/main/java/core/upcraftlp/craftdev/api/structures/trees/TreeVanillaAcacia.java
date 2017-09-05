package core.upcraftlp.craftdev.api.structures.trees;

import core.upcraftlp.craftdev.api.structures.ITreeMapping;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TreeVanillaAcacia implements ITreeMapping {

	@Override
	public void placeLeaves(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, true));
	}

	@Override
	public void placeLog(World world, BlockPos pos, Axis axis) {
		world.setBlockState(pos, (Blocks.LOG.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(axis))));
	}

}
