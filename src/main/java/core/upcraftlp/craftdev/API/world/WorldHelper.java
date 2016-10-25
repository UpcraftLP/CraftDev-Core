package core.upcraftlp.craftdev.API.world;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {

	/** 
	 * find a grass or dirt block to place the bush on
	 * original method from sky_01, see http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/2666351
	 * 
	 * modified by UpcraftLP.
	 * 
	 * @param world
	 * @param x
	 * @param z
	 * @return the y-value at ground level
	 */
	public static int getGroundFromAbove(World world, int x, int z, List<Block> soil)
	{
		int y = 255;
		boolean foundGround = false;
			while(!foundGround && y-- >= 0)
		{
			Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = soil.contains(blockAt);
		}

		return y;
	}
	
	/** 
	 * find a grass or dirt block to place the bush on
	 * original method from sky_01, see http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/2666351
	 * 
	 * modified by UpcraftLP.
	 * 
	 * @param world
	 * @param pos
	 * @return the y-value at ground level
	 */
	public static int getGroundFromAbove(World world, BlockPos pos, List<Block> soil) {
		return getGroundFromAbove(world, pos.getX(), pos.getZ(), soil);
	}
	
}
