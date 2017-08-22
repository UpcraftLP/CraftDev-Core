package core.upcraftlp.craftdev.commands;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import core.upcraftlp.craftdev.api.structures.StructureLoaderNBT;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public class CommandLoadStructure extends CommandBase implements ICommand {

    private static final List<String> aliases = Lists.newArrayList("loadStructure");

    @Override
    public String getName() {
        return "loadStructure";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.loadstructure.usage";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (!world.isRemote) {
            if (args.length < 4 || args.length > 7) {
                throw new WrongUsageException(this.getUsage(sender));
            }
            if (args[0].endsWith(".nbt")) {
                args[0] = args[0].replaceAll(".nbt", "").trim();
            }
            String[] locString = args[0].split(":");
            BlockPos pos = CommandBase.parseBlockPos(sender, args, 1, false);

            PlacementSettings settings = new PlacementSettings();

            if (args.length >= 5) {
                if (parseBoolean(args[4])) settings.setMirror(Mirror.LEFT_RIGHT);
            }

            if (args.length >= 6) {
                Rotation rot = Rotation.NONE;
                int arg = parseInt(args[5]);
                switch (arg) {
                    case 0:
                        rot = Rotation.NONE;
                        break;
                    case 90:
                        rot = Rotation.CLOCKWISE_90;
                        break;
                    case 180:
                        rot = Rotation.CLOCKWISE_180;
                        break;
                    case 270:
                        rot = Rotation.COUNTERCLOCKWISE_90;
                        break;
                    default:
                        throw new NumberInvalidException("commands.generic.parameter.invalid");
                }
                settings.setRotation(rot);
            }

            if (args.length == 7) {
                settings.setIgnoreEntities(parseBoolean(args[6]));
            }

            boolean success;
            if (locString.length == 2) {
                ResourceLocation location = new ResourceLocation(locString[0], locString[1]);
                success = StructureLoaderNBT.loadFromAssets(world, pos, location, settings);
            } else {
                success = StructureLoaderNBT.loadFromExternalFile(world, pos, locString[0], settings);
            }
            if (!success) throw new CommandException("commands.generic.exception");
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return server.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        if (args.length < 2) {
            File[] files = StructureLoaderNBT.getStructureDir().listFiles();
            Set<String> fileNames = Sets.newHashSet();
            for (File f : files) {
                String fileName = f.getName();
                if (!fileName.contains(".nbt")) continue;
                fileName = fileName.replace(".nbt", "");
                fileNames.add(fileName.trim());
            }
            return getListOfStringsMatchingLastWord(args, fileNames);
        }
        if (args.length > 1 && args.length <= 4) {
            return getTabCompletionCoordinate(args, 1, targetPos);
        }
        if (args.length == 5) return getListOfStringsMatchingLastWord(args, new String[] {"true", "false"});
        if (args.length == 6) return getListOfStringsMatchingLastWord(args, new String[] {"0", "90", "180", "270"});
        if (args.length == 7) return getListOfStringsMatchingLastWord(args, new String[] {"true", "false"});
        return Collections.<String>emptyList();
    }

}
