package core.upcraftlp.craftdev.API.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.upcraftlp.craftdev.API.world.IWorldChunkGenerator;

public class WorldHandler {

	private static Map<Integer, List<IWorldChunkGenerator>> generators = new HashMap<Integer, List<IWorldChunkGenerator>>();
	
	/**
	 * Register a chunk generator for the specific dimension
	 * @param generator	The generator
	 * @param dimension	The dimension ID
	 */
	public static void registerChunkGenerator(IWorldChunkGenerator generator, int dimension) {
		if(generators.isEmpty() || !generators.containsKey(dimension)) {
			generators.put(dimension, new ArrayList<IWorldChunkGenerator>());
		}
		
		List<IWorldChunkGenerator> chunkGens = generators.get(dimension);
		chunkGens.add(generator);
		generators.remove(dimension);
		generators.put(dimension, chunkGens);
	}
	
	public static List<IWorldChunkGenerator> getGenerators(int dimension) {
		if(generators.isEmpty() || !generators.containsKey(dimension)) {
			return new ArrayList<IWorldChunkGenerator>();
		}
		return generators.get(dimension);
	}
}
