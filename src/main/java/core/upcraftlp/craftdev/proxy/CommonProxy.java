package core.upcraftlp.craftdev.proxy;

import core.upcraftlp.craftdev.api.structures.StructureLoaderNBT;
import core.upcraftlp.craftdev.api.structures.StructureLoaderSchematic;
import core.upcraftlp.craftdev.api.util.ModHelper;
import core.upcraftlp.craftdev.commands.CommandClearLag;
import core.upcraftlp.craftdev.commands.CommandGenTree;
import core.upcraftlp.craftdev.commands.CommandLoadSchematic;
import core.upcraftlp.craftdev.commands.CommandLoadStructure;
import core.upcraftlp.craftdev.config.CoreInternalConfig;
import core.upcraftlp.craftdev.init.CraftDevCrafting;
import core.upcraftlp.craftdev.init.CraftDevEvents;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;

public abstract class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        CoreInternalConfig.init(event);
        final File structureDir = new File(ModHelper.getConfigDir(event), "strcutures");
        StructureLoaderNBT.setStructureDir(structureDir);
        StructureLoaderSchematic.setStructureDir(structureDir);
        CraftDevCrafting.init();
        CraftDevEvents.init(event.getSide());
    }

    public void init(FMLInitializationEvent event) {
        //NO-OP
    }

    public void postInit(FMLPostInitializationEvent event) {
        //NO-OP
    }

    public void serverStarting(FMLServerStartingEvent event) {
        if (ModHelper.isDebugMode() || ModHelper.isDevEnvironment()) {
            event.registerServerCommand(new CommandLoadStructure());
            event.registerServerCommand(new CommandLoadSchematic());
            event.registerServerCommand(new CommandGenTree());
        }
        event.registerServerCommand(new CommandClearLag());
    }

    public void configChanged() {
        //NO-OP
    }

    public void registerRender(Item item) {
        //NO-OP
    }
}
