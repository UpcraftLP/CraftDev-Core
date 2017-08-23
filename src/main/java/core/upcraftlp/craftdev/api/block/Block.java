package core.upcraftlp.craftdev.api.block;

import java.util.Random;

import core.upcraftlp.craftdev.api.item.ItemBlock;
import net.minecraft.block.material.Material;

public class Block extends net.minecraft.block.Block {

    protected Random RANDOM = new Random();
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
