package core.upcraftlp.craftdev.common.init;

import core.upcraftlp.craftdev.API.templates.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAlphaCobblestone extends Block {

	public BlockAlphaCobblestone() {
		super("alpha_cobblestone", Material.ROCK, true);
		this.setHarvestLevel("pickaxe", 0);
		this.setSoundType(SoundType.STONE);
		this.setHardness(2.0f);
		this.setResistance(30.0f);
	}
}