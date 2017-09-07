package core.upcraftlp.craftdev.api.util;

import core.upcraftlp.craftdev.api.item.ItemBlock;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class RegistryUtils {

    /**
     * Registers items or blocks. Creates ItemBlocks if necessary.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends IForgeRegistryEntry<T>> void createRegistryEntries(Class<T> type, RegistryEvent.Register<T> event, Class clazz, String modid, CreativeTabs tab) {
        Logger log = LogManager.getLogger(modid);
        int count = 0;
        for (Field f : clazz.getDeclaredFields()) {
            if (IForgeRegistryEntry.class.isAssignableFrom(f.getType())) {
                try {
                    T entry = (T) f.get(null);
                    GameRegistry.register(entry); //TODO in 1.12: change to Event#getRegistry()
                    count++;
                    if (Item.class.isAssignableFrom(type)) {
                        Item item = (Item) entry;
                        item.setCreativeTab(tab);
                        CraftDevCore.proxy.registerRender(item);
                    } else if (Block.class.isAssignableFrom(type)) {
                        Block block = (Block) entry;
                        block.setCreativeTab(tab);
                        if (core.upcraftlp.craftdev.api.block.Block.class.isInstance(type)) {
                            final Item item = ((core.upcraftlp.craftdev.api.block.Block) block).item();
                            if(item != null) {
                                GameRegistry.register(item);
                                count++;
                                CraftDevCore.proxy.registerRender(item);
                            }
                        }
                        else {
                            Item itemBlock = new ItemBlock(block);
                            GameRegistry.register(itemBlock);
                            count++;
                            CraftDevCore.proxy.registerRender(itemBlock);
                        }
                    }
                } catch (Exception ignore) {
                }
                if (!Modifier.isFinal(f.getModifiers())) CraftDevCore.log.warn(clazz.getName() + ":" + f.getName() + " has no final modifier! Pleaes change this!");
            }
        }
        log.info("successfully registered " + count + " Objects.");
    }

}
