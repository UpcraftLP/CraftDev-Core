package core.upcraftlp.craftdev.API.world.gen;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DefaultWorldGenImplementation {

	public static void create(IGeneratedOre ore, int dimension) {
		IWorldGenerator worldGen = new DefaultImplementation(ore, dimension);
		GameRegistry.registerWorldGenerator(worldGen, ore.getOreGenWeight());
	}

	public static class DefaultImplementation implements IWorldGenerator {

		private int dimension;
		private IGeneratedOre ore;
		private Predicate<IBlockState> predicate = new StonePredicate();

		private DefaultImplementation(IGeneratedOre ore, int dimension) {
			this.dimension = dimension;
			this.ore = ore;
			if (this.ore.getPredicateOverride() != null)
				this.predicate = this.ore.getPredicateOverride();
		}

		@Override
		public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
				IChunkProvider chunkProvider) {
			if (world.provider.getDimension() != this.dimension)
				return;
			int count = Math.max(0, MathHelper.getInt(random, ore.getLeastQuantity(), ore.getMostQuantity()));
			for (int i = 0; i < count; i++) {
				int x = chunkX * 16 + random.nextInt(16);
				int z = chunkZ * 16 + random.nextInt(16);
				int y = Math.max(5, MathHelper.getInt(random, ore.getMinHeight(), ore.getMaxHeight()));
				BlockPos pos = new BlockPos(x, y, z);

				// TODO: better algorithm!
				for (int j = -this.ore.getVeinDiameter() / 2; j < this.ore.getVeinDiameter() / 2; j++) {
					for (int k = -this.ore.getVeinDiameter() / 2; k < this.ore.getVeinDiameter() / 2; k++) {
						for (int l = -this.ore.getVeinDiameter() / 2; l < this.ore.getVeinDiameter() / 2; l++) {
							BlockPos blockPos = pos.add(j, k, l);
							IBlockState state = world.getBlockState(pos);
							if (state.getBlock().isReplaceableOreGen(state, world, pos, this.predicate)) {
								world.setBlockState(blockPos, this.ore.getBlockState(), 2);
							}
						}
					}
				}
			}
		}

	}

	/**
	 * derived from {@link net.minecraft.world.gen.feature.WorldGenMinable}
	 */
	static class StonePredicate implements Predicate<IBlockState> {
		public StonePredicate() {
		}

		public boolean apply(IBlockState state) {
			if (state != null && state.getBlock() == Blocks.STONE) {
				BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType) state.getValue(BlockStone.VARIANT);
				return blockstone$enumtype.isNatural();
			} else {
				return false;
			}
		}
	}

}
