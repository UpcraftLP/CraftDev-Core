package core.upcraftlp.craftdev.api.templates;

import net.minecraft.block.Block;

public class ItemBlock extends net.minecraft.item.ItemBlock {

    public ItemBlock(Block block) {
        super(block);
        this.setUnlocalizedName(block.getUnlocalizedName().substring(5));
        this.setRegistryName(block.getRegistryName());
    }

}
