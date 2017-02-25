package core.upcraftlp.craftdev.init;

import core.upcraftlp.craftdev.API.util.RegistryUtils;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * put all registries here
 * @author UpcraftLP
 *
 */
@EventBusSubscriber(modid = CraftDevReference.MODID)
public class RegistryHandler {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        RegistryUtils.createRegistryEntries(event, CraftDevBlocks.class, CraftDevReference.MODID, CreativeTabs.DECORATIONS);
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegistryUtils.createRegistryEntries(event, CraftDevItems.class, CraftDevReference.MODID, CreativeTabs.TOOLS);
    }
}
