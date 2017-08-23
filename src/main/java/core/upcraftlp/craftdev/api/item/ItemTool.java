package core.upcraftlp.craftdev.api.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;

public class ItemTool extends net.minecraft.item.ItemTool {

    static Set<Block> effectiveBlocks = Sets.newHashSet(new Block[] {});

    public ItemTool(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn) {
        super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocks);
        this.setFull3D();
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

}
