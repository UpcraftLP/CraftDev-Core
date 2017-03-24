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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

/**
 * not really deprecated, but unfinished!
 * <br/>
 * <b>DO NOT USE</b>
 */
@Beta
public class StructureLoaderSchematic {

    private static File structureDir;

    public static boolean loadFromExternalFile(World world, BlockPos pos, String name, PlacementSettings settings) {
        InputStream stream = null;
        try {
            File structureFile = new File(structureDir, name + ".schematic");
            if (!structureFile.exists()) {
                CraftDevCore.getLogger().errFatal("File not found: " + structureFile.getAbsolutePath());
                return false;
            }
            stream = new FileInputStream(structureFile);
            load(world, pos, stream, settings);
            return true;
        } catch (Exception e) {
            CraftDevCore.getLogger().errFatal("Exception caught: " + e.getMessage());
            return false;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public static boolean loadFromAssets(World world, BlockPos pos, ResourceLocation location, PlacementSettings settings) {
        InputStream stream = null;
        try {
            String id = location.getResourceDomain();
            String path = location.getResourcePath();
            stream = MinecraftServer.class.getResourceAsStream("/assets/" + id + "/structures/" + path + ".schematic");
            load(world, pos, stream, settings);
            return true;
        } catch (Exception e) {
            CraftDevCore.getLogger().errFatal("Exception caught: " + e.getMessage());
            return false;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    private static void load(World world, BlockPos pos, InputStream stream, PlacementSettings settings) throws IOException {

        NBTTagCompound templateCompound = SchematicConverter.convertToNBT(CompressedStreamTools.readCompressed(stream));
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
