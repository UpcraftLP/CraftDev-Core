package core.upcraftlp.craftdev.API.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;

public class RegistryUtils {

    /**
     * Registers items or blocks. Creates ItemBlocks if necessary.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends IForgeRegistryEntry<T>> void createRegistryEntries(Class<T> type, RegistryEvent.Register<T> event, Class clazz, String modid, CreativeTabs tab) {
        ModLogger log = Loggers.get(modid);
        int count = 0;
        for (Field f : clazz.getDeclaredFields()) {
            if (IForgeRegistryEntry.class.isAssignableFrom(f.getType())) {
                try {
                    T entry = (T) f.get(null);
                    // event.getRegistry().register(entry); //FIXME why does this not work?
                    GameRegistry.register(entry);
                    count++;
                    if (Item.class.isAssignableFrom(type)) {
                        Item item = (Item) entry;
                        item.setCreativeTab(tab);
                        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) registerRender(item);
                    } else if (Block.class.isAssignableFrom(type)) {
                        Block block = (Block) entry;
                        block.setCreativeTab(tab);
                        if (core.upcraftlp.craftdev.API.templates.Block.class.isAssignableFrom(type)) {
                            final Item item = ((core.upcraftlp.craftdev.API.templates.Block) block).item();
                            GameRegistry.register(item);
                            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) registerRender(item);
                            count++;
                        }
                    }
                } catch (Exception ignore) {
                }
                if (!Modifier.isFinal(f.getModifiers())) CraftDevCore.getLogger().println(clazz.getName() + ":" + f.getName() + " has no final modifier! Pleaes change this!");
            }
        }
        log.println("successfully registered " + count + " Objects.");
    }

    private static void registerRender(Item item) {
        if (item.getHasSubtypes()) {
            NonNullList<ItemStack> items = NonNullList.create();
            item.getSubItems(item, null, items);
            for (ItemStack stack : items) {
                int meta = stack.getMetadata();
                ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + "_" + meta, "inventory"));
            }
        } else {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }

    }
}
