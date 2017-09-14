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
                    if(insertIndex == 0) {
                        if(current instanceof MethodInsnNode) {
                            MethodInsnNode methodNode = (MethodInsnNode) current;
                            if(methodNode.getOpcode() == INVOKESTATIC && methodNode.owner.equals(DeobfuscationHelper.EnchantmentHelper.replace(".", "/")) && methodNode.name.equals(DeobfuscationHelper.getName("func_191527_a")) && methodNode.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;)F")) {
                                insertIndex = i - 2;
                            }
                        }
                    }
                    else if(current instanceof TypeInsnNode) {
                        TypeInsnNode typeNode = (TypeInsnNode) current;
                        if(typeNode.getOpcode() == INSTANCEOF && typeNode.desc.equals("net/minecraft/entity/player/EntityPlayerMP")) {
                            AbstractInsnNode abstractNode = node.instructions.get(i + 3);
                            if(abstractNode instanceof FieldInsnNode) {
                                FieldInsnNode fieldNode = (FieldInsnNode) abstractNode;
                                if(fieldNode.getOpcode() == GETFIELD && fieldNode.name.equals(DeobfuscationHelper.getName("velocityChanged"))) {
                                    System.out.println("adding SweepEvent");
                                    InsnList toAdd = new InsnList();
                                    toAdd.add(new VarInsnNode(ALOAD, 0)); //load the player on the stack
                                    toAdd.add(new VarInsnNode(ALOAD, 1)); //load the target entity (method parameter) on the stack
                                    toAdd.add(new MethodInsnNode(INVOKESTATIC, "core/upcraftlp/craftdev/asm/tweaks/TweakSweepAttack", "fireSweepEvent", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)Z", false));
                                    do {
                                        abstractNode = abstractNode.getPrevious();
                                    } while (!(abstractNode instanceof LabelNode));
                                    toAdd.add(new JumpInsnNode(IFNE, (LabelNode) abstractNode)); //skip further execution if event has been canceled
                                    node.instructions.insertBefore(node.instructions.get(insertIndex), toAdd); //FIXME doesn't work outside the dev environment :(
                                    break loop;
                                }
                            }
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
