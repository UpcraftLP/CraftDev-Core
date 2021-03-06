package core.upcraftlp.craftdev.api.world;

import com.google.common.annotations.Beta;
import core.upcraftlp.craftdev.api.structures.ITreeMapping;
import core.upcraftlp.craftdev.api.structures.TreeType;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <b>UNFINISHED, USE AT OWN RISK!</b>
 */
@Beta
public class DecorationHelper {

	/**
	 * A helper method for generating randomized trees in different shapes
	 */
	public static void generateTree(World world, BlockPos startPos, ITreeMapping tree, TreeType treeType)
	{
		//TODO: Trees!
		Random random = new Random();
		List<BlockPos> logsX = new ArrayList<>();
		List<BlockPos> logsY = new ArrayList<>();
		List<BlockPos> logsZ = new ArrayList<>();
		List<BlockPos> leaves = new ArrayList<>();
		
		switch(treeType) {
		
		case NORMAL:
			if(random.nextInt(20) == 0) {
				generateTree(world, startPos, tree, TreeType.LARGE);
				break;
			}
			logsY.add(startPos);
			logsY.add(startPos.up());
			logsY.add(startPos.up(2));
			logsY.add(startPos.up(3));
			logsY.add(startPos.up(4));
			if(random.nextBoolean()) {
				logsY.add(startPos.up(5));
				startPos = startPos.up(5);
			}
			else
			{
				startPos = startPos.up(4);
			}
			for(int k = 0; k < 2; k++) {
				for(int i = -1; i < 2; i++)  {
					for(int j = -2; j < 3; j++) {
						leaves.add(new BlockPos(startPos.getX() + i, startPos.getY() - k, startPos.getZ() + j));
					}
					leaves.add(new BlockPos(startPos.getX() - 2, startPos.getY() - k, startPos.getZ() + 1));
					leaves.add(new BlockPos(startPos.getX() - 2, startPos.getY() - k, startPos.getZ()));
					leaves.add(new BlockPos(startPos.getX() - 2, startPos.getY() - k, startPos.getZ() - 1));
					leaves.add(new BlockPos(startPos.getX() + 2, startPos.getY() - k, startPos.getZ() + 1));
					leaves.add(new BlockPos(startPos.getX() + 2, startPos.getY() - k, startPos.getZ()));
					leaves.add(new BlockPos(startPos.getX() + 2, startPos.getY() - k, startPos.getZ() - 1));
					if(random.nextBoolean()) leaves.add(new BlockPos(startPos.getX() - 2, startPos.getY() - k, startPos.getZ() + 2));
					if(random.nextBoolean()) leaves.add(new BlockPos(startPos.getX() - 2, startPos.getY() - k, startPos.getZ() - 2));
					if(random.nextBoolean()) leaves.add(new BlockPos(startPos.getX() + 2, startPos.getY() - k, startPos.getZ() + 2));
					if(random.nextBoolean()) leaves.add(new BlockPos(startPos.getX() + 2, startPos.getY() - k, startPos.getZ() - 2));
				}
			}
			startPos = startPos.up();
			leaves.add(startPos);
			leaves.add(startPos.up());
			leaves.add(startPos.offset(EnumFacing.NORTH));
			leaves.add(startPos.offset(EnumFacing.NORTH).up());
			leaves.add(startPos.offset(EnumFacing.EAST));
			leaves.add(startPos.offset(EnumFacing.EAST).up());
			leaves.add(startPos.offset(EnumFacing.SOUTH));
			leaves.add(startPos.offset(EnumFacing.SOUTH).up());
			leaves.add(startPos.offset(EnumFacing.WEST));
			leaves.add(startPos.offset(EnumFacing.WEST).up());
			if(random.nextInt(2) == 0) leaves.add(startPos.offset(EnumFacing.NORTH).offset(EnumFacing.EAST));
			if(random.nextInt(2) == 0) leaves.add(startPos.offset(EnumFacing.NORTH).offset(EnumFacing.WEST));
			if(random.nextInt(2) == 0) leaves.add(startPos.offset(EnumFacing.SOUTH).offset(EnumFacing.EAST));
			if(random.nextInt(2) == 0) leaves.add(startPos.offset(EnumFacing.SOUTH).offset(EnumFacing.EAST));
			 
			break;

		case TWO_BY_TWO:
		case LARGE:
		case PALM:
		case BUSH:
		case BIG:
		case EXTRA_BIG:
		case HOUSE:
			CraftDevCore.log.warn("TreeType " + treeType.name() + " has not been implemented yet. Aborting Tree generation!");
			return;
		}
		
		for (BlockPos blockPos : logsX) {
			tree.placeLog(world, blockPos, Axis.X);
		}
		for (BlockPos blockPos : logsY) {
			tree.placeLog(world, blockPos, Axis.Y);
		}
		for (BlockPos blockPos : logsZ) {
			tree.placeLog(world, blockPos, Axis.Z);
		}
		for (BlockPos blockPos : leaves) {
			if(world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos)) tree.placeLeaves(world, blockPos);
		}
	}
}
