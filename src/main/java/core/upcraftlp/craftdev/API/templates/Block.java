package core.upcraftlp.craftdev.API.templates;

import java.util.Random;

import net.minecraft.block.material.Material;

public class Block extends net.minecraft.block.Block {

	protected Random BLOCK_RANDOM = new Random();
	
	public Block(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
	
}
