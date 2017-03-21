package core.upcraftlp.craftdev.ASM.tweaks;

import java.util.Iterator;

import org.objectweb.asm.tree.MethodNode;

import core.upcraftlp.craftdev.API.util.asm.ClassTransform;
import core.upcraftlp.craftdev.ASM.DeobfuscationHelper;

public class TweakRenderLiving extends ClassTransform {

    @Override
    public void transform(Iterator<MethodNode> methods) {
       while(methods.hasNext()) {
           MethodNode node = methods.next();
           if(node.name.equals(DeobfuscationHelper.getName("prepareScale"))) {
              
               log.println("transform render");
               //TODO look at RenderGiantZombie
           }
       }
    }

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.RenderLiving;
    }

}
