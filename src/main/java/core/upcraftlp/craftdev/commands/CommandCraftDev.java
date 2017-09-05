package core.upcraftlp.craftdev.commands;

import com.google.common.collect.Lists;
import core.upcraftlp.craftdev.client.VanityFeatures;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

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
        if(args.length != 1) throw new WrongUsageException(this.getUsage(sender));
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        switch (args[0].toUpperCase(Locale.ROOT)) {
            case "UUID":
                TextComponentTranslation text = new TextComponentTranslation("commands.craftdev.uuid", player.getCachedUniqueIdString());
                        text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, player.getCachedUniqueIdString()));
                        text.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("commands.craftdev.uuidHover")));
                sender.sendMessage(text);
                break;
            case "NBT":
                ItemStack held = player.getHeldItemMainhand();
                if(held.isEmpty()) {
                    throw new CommandException("commands.craftdev.emptyHand");
                }
                else {
                    player.sendMessage(new TextComponentTranslation("commands.craftdev.nbt1", held.getItem().getRegistryName()));
                    if(held.hasTagCompound()) player.sendMessage(new TextComponentTranslation("commands.craftdev.nbt2", held.getTagCompound()));
                }
                break;
            case "DONATORS":
                sender.sendMessage(new TextComponentTranslation("commands.craftdev.donators", VanityFeatures.hasVanityFeatures(sender.getName()))); //TODO check if person is donator
                break;
        }
    }
}
