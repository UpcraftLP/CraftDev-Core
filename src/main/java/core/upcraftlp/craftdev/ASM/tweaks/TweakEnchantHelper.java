package core.upcraftlp.craftdev.ASM.tweaks;

import java.util.Iterator;

import org.objectweb.asm.tree.MethodNode;

import core.upcraftlp.craftdev.API.util.asm.ClassTransform;
import core.upcraftlp.craftdev.ASM.DeobfuscationHelper;

public class TweakEnchantHelper extends ClassTransform {
    
    @Override
    public void transform(Iterator<MethodNode> methods) {
        log.println("UNIMPLEMENTED! RETURNING TO VANILLA BEHAVIOUR!");
    }

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.EnchantmentHelper;
    }

}
