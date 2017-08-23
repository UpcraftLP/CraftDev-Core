package core.upcraftlp.craftdev.asm.tweaks;

import static org.objectweb.asm.Opcodes.FMUL;
import static org.objectweb.asm.Opcodes.FSTORE;
import static org.objectweb.asm.Opcodes.GETSTATIC;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import core.upcraftlp.craftdev.api.util.asm.ClassTransform;
import core.upcraftlp.craftdev.api.util.asm.DeobfuscationHelper;

public class TweakEntityFireRender extends ClassTransform {

    @Override
    public void transform(Iterator<MethodNode> methods) {
        String name = DeobfuscationHelper.getName("renderEntityOnFire");
        while(methods.hasNext()) {
            MethodNode node = methods.next();
            if(node.name.equals(name)) {
                for(int i = 0; i < node.instructions.size(); i++) {
                    AbstractInsnNode current = node.instructions.get(i);
                    
                    if(current instanceof VarInsnNode && ((VarInsnNode) current).getOpcode() == FSTORE) {
                        VarInsnNode varNode = (VarInsnNode) current;
                        if(varNode.var == 12) {
                            log.debug("adding the scale modifier, part 1 of 2...");
                            node.instructions.insertBefore(current, new FieldInsnNode(GETSTATIC, "core/upcraftlp/craftdev/client/MobScaleHandler", "scale", "F")); //pull the scale multiplier onto the stack
                            node.instructions.insertBefore(current, new InsnNode(FMUL)); //multiply the old scale with the multiplier
                            i += 2; //skip the newly added instructions
                        }
                        else if(varNode.var == 17) { //need this for the height of the fire
                            log.debug("adding the scale modifier, part 2 of 2...");
                            node.instructions.insertBefore(current, new FieldInsnNode(GETSTATIC, "core/upcraftlp/craftdev/client/MobScaleHandler", "scale", "F")); //pull the scale multiplier onto the stack
                            node.instructions.insertBefore(current, new InsnNode(FMUL)); //multiply the old scale with the multiplier
                            break; //skip everything else.
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.Entity_Render;
    }
    
}