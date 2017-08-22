package core.upcraftlp.craftdev.api.structures;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITreeMapping {
	
	void placeLeaves(World world, BlockPos pos);
	
	void placeLog(World world, BlockPos pos, EnumFacing.Axis axis);
	
}
