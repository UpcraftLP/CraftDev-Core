package core.upcraftlp.craftdev.commands;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandClearLag extends CommandBase {

    private static final List<String> aliases = Lists.newArrayList("lag", "clearlag");
    
    @Override
    public String getName() {
        return "clearlag";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.clearlag.usage";
    }
    
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender.getEntityWorld().isRemote) return;
        if(args.length != 1) throw new WrongUsageException(this.getUsage(sender));
        else {
            int i = parseInt(args[0], 1, 5);
            int count = 0;
            List<Entity> entityList = sender.getEntityWorld().loadedEntityList;
            switch(i) {
            case 1:
                for(Entity e : entityList) {
                    if(!e.isDead && !e.isNonBoss()) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            case 2:
                for(Entity e : entityList) {
                    if(!e.isDead && e.isCreatureType(EnumCreatureType.MONSTER, false)) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            case 3:
                for(Entity e : entityList) {
                    if(!e.isDead && (e.isCreatureType(EnumCreatureType.CREATURE, false) || e.isCreatureType(EnumCreatureType.AMBIENT, false) || e.isCreatureType(EnumCreatureType.WATER_CREATURE, false))) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            case 4:
                for(Entity e : entityList) {
                    if(!e.isDead && e instanceof EntityItem) {
                        count += ((EntityItem) e).getEntityItem().getCount();
                        e.setDead();
                    }
                }
                break;
            case 5:
                for(i = 1; i < 5; i++) this.execute(server, sender, new String[]{Integer.toString(i)});
                for(Entity e : entityList) {
                    if(!e.isDead && !(e instanceof EntityPlayer)) {
                        e.setDead();
                        count++;
                    }
                }
            }
            notifyCommandListener(sender, this, "commands.clearlag.success." + i, new Object[]{Integer.valueOf(count)});
            server.updateTimeLightAndEntities();
        }
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        if(args.length < 1) return Lists.newArrayList("1", "2", "3", "4", "5");
        return Collections.emptyList();
    }

}
