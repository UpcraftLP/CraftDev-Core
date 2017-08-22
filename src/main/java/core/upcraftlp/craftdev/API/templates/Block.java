package core.upcraftlp.craftdev.api.templates;

import java.util.Random;

import net.minecraft.block.material.Material;

public class Block extends net.minecraft.block.Block {

    protected Random BLOCK_RANDOM = new Random();
    private ItemBlock item;

    public Block(String name, Material material, boolean createItemBlock) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        if ( createItemBlock )
            this.item = new ItemBlock(this);
    }

    public net.minecraft.item.Item item() {
        return this.item;
    }

}
