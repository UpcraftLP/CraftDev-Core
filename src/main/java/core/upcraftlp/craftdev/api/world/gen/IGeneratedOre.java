package core.upcraftlp.craftdev.api.world.gen;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;

public interface IGeneratedOre {

    default int getVeinDiameter() {
        return 6;
    }

    IBlockState getBlockState();

    int getMinHeight();

    int getMaxHeight();

    default Predicate<IBlockState> getPredicateOverride() {
        return null;
    }

    int getLeastQuantity();

    int getMostQuantity();

    /**
     * higher number = generates later
     */
    default int getOreGenWeight() {
        return 10;
    }
}
