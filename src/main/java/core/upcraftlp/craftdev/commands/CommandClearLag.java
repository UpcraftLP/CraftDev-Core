package core.upcraftlp.craftdev.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

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
        if(args.length > 1) throw new WrongUsageException(this.getUsage(sender));
        else if(args.length == 0) {
            this.execute(server, sender, new String[]{"2"}); //defaulting to 2
        }
        else {
            int i = parseInt(args[0], 0, 6);
            int count = 0;
            List<Entity> entityList = sender.getEntityWorld().loadedEntityList;
            if(i > 6) throw new WrongUsageException(this.getUsage(sender));
            switch(i) {
            case 1:
                for(i = 6; i > 1; i--) this.execute(server, sender, new String[]{Integer.toString(i)});
                for(Entity e : entityList) {
                    if(!e.isDead && !(e instanceof EntityPlayer)) {
                        e.setDead();
                        count++;
                    }
                }
                i = 1; //reset i for the success message
                break;
            case 2:
                for(Entity e : entityList) {
                    if(!e.isDead && (e.isCreatureType(EnumCreatureType.MONSTER, false) || e instanceof IMob || e instanceof EntityCreature)) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            case 3:
                for(Entity e : entityList) {
                    if(!e.isDead && e instanceof EntityItem) {
                        e.setDead();
                        ItemStack stack = ((EntityItem) e).getItem();
                        if(!stack.isEmpty()) count += stack.getCount();
                    }
                }
                break;
            case 4:
                for(Entity e : entityList) {
                    if(!e.isDead && e instanceof EntityTNTPrimed) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            case 5:
                for(Entity e : entityList) {
                    if(!e.isDead && (e.isCreatureType(EnumCreatureType.CREATURE, false) || e.isCreatureType(EnumCreatureType.AMBIENT, false) || e.isCreatureType(EnumCreatureType.WATER_CREATURE, false) || e instanceof IAnimals)) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            case 6:
                for (Entity e : entityList) {
                    if(!e.isDead && !e.isNonBoss()) {
                        e.setDead();
                        count++;
                    }
                }
                break;
            }
            notifyCommandListener(sender, this, "commands.clearlag.success." + i, count);
            server.updateTimeLightAndEntities();
        }
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        if(args.length < 1) return Lists.newArrayList("1", "2", "3", "4", "5", "6");
        return Collections.emptyList();
    }

}
