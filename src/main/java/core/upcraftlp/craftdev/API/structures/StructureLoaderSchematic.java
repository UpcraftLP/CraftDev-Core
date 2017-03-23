package core.upcraftlp.craftdev.API.structures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.google.common.annotations.Beta;

import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * not really deprecated, but unfinished!
 * <br/>
 * <b>DO NOT USE</b>
 */
@Beta
public class StructureLoaderSchematic {

    private static final String WIDTH = "Width";
    private static final String HEIGHT = "Height";
    private static final String LENGTH = "Length";
    private static final String BLOCKS = "Blocks";
    private static final String DATA = "Data";
    private static final String TILE_ENTITIES = "TileEntities";
    private static final String ENTITIES = "Entities";
    private static File structureDir;

    @Deprecated
    @Beta
    public static void loadFromExternalFile(World world, BlockPos pos, String name, PlacementSettings settings) {
        InputStream stream = null;
        try {
            File structureFile = new File(structureDir, name + ".schematic");
            if (!structureFile.exists()) {
                CraftDevCore.getLogger().errFatal("File not found: " + structureFile.getAbsolutePath());
                return;
            }
            stream = new FileInputStream(structureFile);
            load(world, pos, stream, settings);
        } catch (Exception e) {
            CraftDevCore.getLogger().errFatal("Exception caught: " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    @Deprecated
    @Beta
    public static void loadFromAssets(World world, BlockPos pos, ResourceLocation location, PlacementSettings settings) {
        InputStream stream = null;
        try {
            String id = location.getResourceDomain();
            String path = location.getResourcePath();
            stream = MinecraftServer.class.getResourceAsStream("/assets/" + id + "/structures/" + path + ".schematic");
            load(world, pos, stream, settings);
        } catch (Exception e) {
            CraftDevCore.getLogger().errFatal("Exception caught: " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     * NEVER EVER CALL THIS METHOD! IT'S UNFINISHED!
     */
    @Beta
    private static void load(World world, BlockPos pos, InputStream stream, PlacementSettings settings) throws IOException {

        /***************************************************************************************/

        CraftDevCore.getLogger().errFatal("I TOLD YOU NOT TO USE SCHEMATIC LOADER!");
        CraftDevCore.getLogger().errFatal("Crashing Minecraft NOW");
        CraftDevCore.getLogger().errFatal("This is your fault.");
        FMLCommonHandler.instance().exitJava(1, true); // yep, NEVER EVER
                                                       // CALL THIS METHOD!
        /***************************************************************************************/

        NBTTagCompound schematicNBT = CompressedStreamTools.readCompressed(stream);
        short width = schematicNBT.getShort(WIDTH);
        short height = schematicNBT.getShort(HEIGHT);
        short length = schematicNBT.getShort(LENGTH);
        byte[] blocks = schematicNBT.getByteArray(BLOCKS);
        byte[] data = schematicNBT.getByteArray(DATA);
        NBTTagList entities = schematicNBT.getTagList(ENTITIES, NBT.TAG_LIST);
        NBTTagList tileEntities = schematicNBT.getTagList(TILE_ENTITIES, NBT.TAG_LIST);

        // FIXME: actual schematic implementation
        NBTTagCompound templateCompound = new NBTTagCompound();

        Template template = new Template();
        template.read(StructureLoaderNBT.fixer.process(FixTypes.STRUCTURE, templateCompound));
        if (settings.getIntegrity() < 1.0f) {
            settings.setIntegrity(MathHelper.clamp(settings.getIntegrity(), 0.0f, 1.0f));
            settings.setSeed(world.getSeed());
        }
        template.addBlocksToWorld(world, pos, settings);
    }

    public static File getStructureDir() {
        return structureDir;
    }

    public static void setStructureDir(File structureDirIn) {
        structureDir = structureDirIn;
    }
}
