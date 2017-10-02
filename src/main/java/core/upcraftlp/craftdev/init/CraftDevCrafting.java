package core.upcraftlp.craftdev.init;

import core.upcraftlp.craftdev.config.CoreInternalConfig;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftDevCrafting {

    public static void init() {
        
        /*
         * [S S]
         * [ S ]
         * [S S]
         * 3x3 crafting; s = ore:string.
         */
        //FIXME currently disabled
        //TODO move to json recipe
        //if(CoreInternalConfig.webCrafting) GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.WEB, "S S", " S ", "S S", 'S', "string"));
    }
}
