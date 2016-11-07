package core.upcraftlp.craftdev.common;

import java.util.Iterator;
import java.util.Random;

import core.upcraftlp.craftdev.API.common.WorldHandler;
import core.upcraftlp.craftdev.API.world.IWorldChunkGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CoreInternalWorldGenerator implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		Iterator<IWorldChunkGenerator> i = WorldHandler.getGenerators(world.provider.getDimension()).iterator();
		while(i.hasNext()) {
			IWorldChunkGenerator worldChunkGenerator = i.next();
			worldChunkGenerator.generate(world, random, blockX, blockZ);
		}
	}
}
