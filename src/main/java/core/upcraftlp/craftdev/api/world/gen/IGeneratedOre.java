package core.upcraftlp.craftdev.api.world.gen;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;

public interface IGeneratedOre {

    public default int getVeinDiameter() {
        return 6;
    }

    public IBlockState getBlockState();

    public int getMinHeight();

    public int getMaxHeight();

    public default Predicate<IBlockState> getPredicateOverride() {
        return null;
    }

    public int getLeastQuantity();

    public int getMostQuantity();

    /**
     * higher number = generates later
     */
    public default int getOreGenWeight() {
        return 10;
    }
}
