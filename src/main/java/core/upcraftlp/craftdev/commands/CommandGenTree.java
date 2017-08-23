package core.upcraftlp.craftdev.commands;

import com.google.common.collect.Lists;
import core.upcraftlp.craftdev.api.structures.TreeType;
import core.upcraftlp.craftdev.api.structures.trees.TreeVanillaOak;
import core.upcraftlp.craftdev.api.world.DecorationHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.Locale;

/**
 * (c)2017 UpcraftLP
 */
public class CommandGenTree extends CommandBase {

    @Override
    public String getName() {
        return "genTree";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("genTree", "gentree", "tree");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.genTree.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 4) throw new WrongUsageException(this.getUsage(sender));
        for (TreeType type : TreeType.values()) {
            if(type.name().equals(args[0].toUpperCase(Locale.ROOT))) {
                DecorationHelper.generateTree(sender.getEntityWorld(), parseBlockPos(sender, args, 1, false), new TreeVanillaOak(), type);
                return;
            }
        }
        throw new CommandException("commands.genTree.wrongArgument", (Object[]) args);
    }
}
