package core.upcraftlp.craftdev.api.structures;

import core.upcraftlp.craftdev.api.event.StructureLoadEvent;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StructureLoaderNBT {

    private static File structureDir;
    public static final DataFixer fixer = DataFixesManager.createFixer();

    public static boolean loadFromAssets(World world, BlockPos pos, ResourceLocation location, PlacementSettings settings) {
        InputStream stream = null;
        try {
            String id = location.getResourceDomain();
            String path = location.getResourcePath();
            stream = MinecraftServer.class.getResourceAsStream("/assets/" + id + "/structures/" + path + ".nbt");
            load(world, pos, stream, settings, location.toString());
            return true;
        } catch (Exception e) {
            CraftDevCore.log.error("Resource not found: " + location.toString());
            return false;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public static boolean loadFromExternalFile(World world, BlockPos pos, String name, PlacementSettings settings) {
        InputStream stream = null;
        try {
            File structureFile = new File(structureDir, name + ".nbt");
            if (!structureFile.exists()) {
                CraftDevCore.log.error("File not found: " + structureFile.getAbsolutePath());
                return false;
            } else {
                stream = new FileInputStream(structureFile);
                load(world, pos, stream, settings, name);
                return true;
            }
        } catch (Exception e) {
            CraftDevCore.log.error("Exception caught: " + e.getMessage());
            return false;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    private static void load(World world, BlockPos pos, InputStream stream, PlacementSettings settings, String name) throws IOException {
        NBTTagCompound templateCompound = CompressedStreamTools.readCompressed(stream);
        Template template = new Template();
        template.read(fixer.process(FixTypes.STRUCTURE, templateCompound));
        if (settings.getIntegrity() < 1.0f) {
            settings.setIntegrity(MathHelper.clamp(settings.getIntegrity(), 0.0f, 1.0f));
            settings.setSeed(world.getSeed());
        }
        StructureLoadEvent event = new StructureLoadEvent(world, pos, name, settings, template);
        if(MinecraftForge.TERRAIN_GEN_BUS.post(event)) {
            event.getTemplate().addBlocksToWorld(world, event.getPosition(), event.getPlacementSettings());
        }
    }

    public static File getStructureDir() {
        return structureDir;
    }

    public static void setStructureDir(File dir) {
        if (!dir.exists()) dir.mkdirs();
        structureDir = dir;
    }
}
