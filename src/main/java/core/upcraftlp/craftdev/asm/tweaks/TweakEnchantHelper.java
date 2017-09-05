package core.upcraftlp.craftdev.asm.tweaks;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ISTORE;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import core.upcraftlp.craftdev.api.event.EnchantabilityEvent;
import core.upcraftlp.craftdev.api.util.asm.ClassTransform;
import core.upcraftlp.craftdev.api.util.asm.DeobfuscationHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class TweakEnchantHelper extends ClassTransform {
    
    @Override
    public void transform(Iterator<MethodNode> methods) {
        String name = DeobfuscationHelper.getName("calcItemStackEnchantability");
        while(methods.hasNext()) {
            MethodNode node = methods.next();
            if(node.name.equals(name)) {
                for(int i = 0; i < node.instructions.size(); i++) {
                    AbstractInsnNode current = node.instructions.get(i);
                    if(current instanceof VarInsnNode) {
                        VarInsnNode insNode = (VarInsnNode) current;
                        if(insNode.getOpcode() == ISTORE && insNode.var == 5) {
                            log.debug("adding EnchantabilityEvent");
                            InsnList toAdd = new InsnList();
                            toAdd.add(new VarInsnNode(ALOAD, 3)); //load the stack
                            toAdd.add(new VarInsnNode(ILOAD, 5)); //load enchantability
                            toAdd.add(new MethodInsnNode(INVOKESTATIC, "core/upcraftlp/craftdev/asm/tweaks/TweakEnchantHelper", "getItemEnchantability", "(Lnet/minecraft/item/ItemStack;I)I", false));
                            toAdd.add(new VarInsnNode(ISTORE, 5));
                            node.instructions.insert(current, toAdd);
                            break;
                        }
                    }
                            
                }
                
            }
        }
    }

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.EnchantmentHelper;
    }
    
    public static int getItemEnchantability(ItemStack stack, int enchantabilityIn) {
        EnchantabilityEvent event = new EnchantabilityEvent(stack, enchantabilityIn);
        return MinecraftForge.EVENT_BUS.post(event) ? event.getEnchantability() : 0;
    }

}
