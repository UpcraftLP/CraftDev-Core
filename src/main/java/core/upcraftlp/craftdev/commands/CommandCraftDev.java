package core.upcraftlp.craftdev.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

/**
 * (c)2017 UpcraftLP
 */
public class CommandCraftDev extends CommandBase {

    @Override
    public String getName() {
        return "craftdev";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("craftdev", "cd");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.craftdev.usage";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return getListOfStringsMatchingLastWord(args, "uuid", "nbt", "donators");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1) return;
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        switch (args[0].toUpperCase(Locale.ROOT)) {
            case "UUID":
                sender.sendMessage(new TextComponentString(player.getCachedUniqueIdString()));
                break;
            case "NBT":
                ItemStack held = player.getHeldItemMainhand();
                if(held.isEmpty()) {
                    throw new CommandException("commands.craftdev.emptyHand");
                }
                else {
                    player.sendMessage(new TextComponentString("Item: " + held.getItem().getRegistryName().toString()));
                    if(held.hasTagCompound()) player.sendMessage(new TextComponentString("NBT Tag: " + held.getTagCompound().toString()));
                }
                break;
            case "DONATORS":
                sender.sendMessage(new TextComponentString(getCommandSenderAsPlayer(sender).getDisplayNameString())); //TODO check if person is donator
                break;
        }
    }
}
