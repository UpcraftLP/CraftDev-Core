package core.upcraftlp.craftdev.asm.tweaks;

import core.upcraftlp.craftdev.api.event.SweepEvent;
import core.upcraftlp.craftdev.api.util.asm.ClassTransform;
import core.upcraftlp.craftdev.api.util.asm.DeobfuscationHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * (c)2017 UpcraftLP
 */
public class TweakSweepAttack extends ClassTransform {

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.EntityPlayer;
    }

    @Override
    public void transform(Iterator<MethodNode> methods) {
        loop:
        while (methods.hasNext()) {
            String name = DeobfuscationHelper.getName("attackTargetEntityWithCurrentItem");
            MethodNode node = methods.next();
            if(node.name.equals(name)) {
                int insertIndex = 0;
                for(int i = 0; i < node.instructions.size(); i++) {
                    AbstractInsnNode current = node.instructions.get(i);
                    if(current instanceof LineNumberNode) {
                        LineNumberNode lineNode = (LineNumberNode) current;
                        if(lineNode.line == 1440 && insertIndex == 0) { //WARNING: using line numbers might not work out if someone else patches the same thing
                            insertIndex = i;
                        }
                        else if(lineNode.line == 1455) {
                            log.debug("adding SweepEvent");
                            InsnList toAdd = new InsnList();
                            toAdd.add(new VarInsnNode(ALOAD, 0)); //load the player on stack
                            toAdd.add(new VarInsnNode(ALOAD, 1));
                            toAdd.add(new MethodInsnNode(INVOKESTATIC, "core/upcraftlp/craftdev/asm/tweaks/TweakSweepAttack", "fireSweepEvent", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)Z", false));
                            LabelNode jumpLabelNode = (LabelNode) node.instructions.get(i - 1);
                            toAdd.add(new JumpInsnNode(IFNE, jumpLabelNode)); //if method returned 0 (false; event has been canceled) skip furhter execution
                            node.instructions.insertBefore(node.instructions.get(insertIndex), toAdd);
                            break loop;
                        }
                    }
                }
            }
        }
    }

    public static boolean fireSweepEvent(EntityPlayer player, Entity target) {
        return MinecraftForge.EVENT_BUS.post(new SweepEvent(player, target));
    }
}
