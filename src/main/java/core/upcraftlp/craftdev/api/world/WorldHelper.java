package core.upcraftlp.craftdev.api.world;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

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
     * spawns particles.
     * if called on an {@link WorldServer} this code will send packets to the clients telling them to spawn particles.
     * Otherwise the method will do nothing at all.
     */
    public static void spawnParticles(World world, EnumParticleTypes particle, double xCoord, double yCoord, double zCoord, int amount, double xOffset, double yOffset, double zOffset, double speed, int... parameters) {
        if(world instanceof WorldServer) {
            ((WorldServer) world).spawnParticle(particle, particle.getShouldIgnoreRange(), xCoord, yCoord, zCoord, amount, world.rand.nextDouble() * 0.2D, world.rand.nextDouble() * 0.7D, world.rand.nextDouble() * 0.2D, speed, parameters);
    	}
    }

}
