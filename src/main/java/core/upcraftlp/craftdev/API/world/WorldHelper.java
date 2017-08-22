package core.upcraftlp.craftdev.api.world;

import net.minecraft.block.Block;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class WorldHelper {

	/**
	 * get the highest suitable block
	 * @return the y-value of the found block's position, or -1 if no block was found.
	 */
    public static int getGroundFromAbove(World world, int x, int z, Block... soil) {
        int y = 0;
        MutableBlockPos startPos = new MutableBlockPos(x, 255, z); //maximum build height
        do {
            Block blockAt = world.getBlockState(startPos.down(y)).getBlock();
            for(Block soilToTest : soil) {
            	if(blockAt == soilToTest) return y;
            }
        } while (++y < 255);

        return -1;
    }

    /**
	 * get the highest suitable block
	 * @return the y-value of the found block's position, or -1 if no block was found.
	 */
    public static int getGroundFromAbove(World world, BlockPos pos, Block... soil) {
        return getGroundFromAbove(world, pos.getX(), pos.getZ(), soil);
    }
    
    /**
	 * get the lowest suitable block
	 * @return the y-value of the found block's position, or -1 if no block was found.
	 */
    public static int getGroundFromBelow(World world, int x, int z, Block... soil) {
    	int y = 0;
        MutableBlockPos startPos = new MutableBlockPos(x, 0, z); //minimum build height
        do {
            Block blockAt = world.getBlockState(startPos.up(y)).getBlock();
            for(Block soilToTest : soil) {
            	if(blockAt == soilToTest) return y;
            }
        } while (++y < 255);

        return -1;
    }
    
    /**
   	 * get the lowest suitable block
   	 * @return the y-value of the found block's position, or -1 if no block was found.
   	 */
    public static int getGroundFromBelow(World world, BlockPos pos, Block... soil) {
    	return getGroundFromBelow(world, pos.getX(), pos.getZ(), soil);
    }
    
    /**
     * spawns particles on the respective Side (Client or Server)
     */
    public static void spawnParticles(World world, EnumParticleTypes particle, boolean ignoreRangeClient, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
    	if(!world.isRemote) {
    		world.spawnParticle(particle, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    	}
    	else {
    		world.spawnParticle(particle, ignoreRangeClient, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    	}
    }

}
