package core.upcraftlp.craftdev.asm.tweaks;

import core.upcraftlp.craftdev.api.util.asm.ClassTransform;
import core.upcraftlp.craftdev.api.util.asm.DeobfuscationHelper;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class TweakEntityFireRender extends ClassTransform {

    @Override
    public void transform(Iterator<MethodNode> methods) {
        String name = DeobfuscationHelper.getName("renderEntityOnFire");
        while(methods.hasNext()) {
            MethodNode node = methods.next();
            if(node.name.equals(name)) {
                for(int i = 0; i < node.instructions.size(); i++) {
                    AbstractInsnNode current = node.instructions.get(i);
                    
                    if(current instanceof VarInsnNode && current.getOpcode() == FSTORE) {
                        VarInsnNode varNode = (VarInsnNode) current;
                        if(varNode.var == 12) {
                            System.out.println("adding the scale modifier, part 1 of 2...");
                            node.instructions.insertBefore(current, new FieldInsnNode(GETSTATIC, "core/upcraftlp/craftdev/client/MobScaleHandler", "scale", "F")); //pull the scale multiplier onto the stack
                            node.instructions.insertBefore(current, new InsnNode(FMUL)); //multiply the old scale with the multiplier
                            i += 2; //skip the newly added instructions
                        }
                        else if(varNode.var == 17) { //need this for the height of the fire
                            System.out.println("adding the scale modifier, part 2 of 2...");
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
