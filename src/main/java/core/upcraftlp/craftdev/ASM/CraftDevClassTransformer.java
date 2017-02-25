package core.upcraftlp.craftdev.ASM;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.launchwrapper.IClassTransformer;

public class CraftDevClassTransformer implements IClassTransformer {

    private static final ModLogger log = CraftDevCore.getLogger();

    @Override
    public byte[] transform(String className, String deobfClassName, byte[] basicClass) {
        if ( deobfClassName.equals(DeobfuscationHelper.EnchantmentHelper) ) return transformEnchantHelper(deobfClassName, basicClass);
        if ( deobfClassName.equals(DeobfuscationHelper.RenderLiving) ) return transformRenderLiving(deobfClassName, basicClass);
        return basicClass;
    }

    protected final byte[] transformClass(byte[] basicClass, String deobfName, ClassTransform transformer) {
        ClassNode cn = new ClassNode();
        ClassReader cr = new ClassReader(basicClass);
        cr.accept(cn, 0);
        Iterator<MethodNode> methods = cn.methods.iterator();
        log.println("transforming class " + deobfName);
        transformer.transform(methods);

        // ASM specific for cleaning up and returning the final bytes for JVM
        // processing.
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(writer);
        return writer.toByteArray();
    }

    protected static abstract class ClassTransform {
        abstract void transform(Iterator<MethodNode> methods);
    }

    private byte[] transformEnchantHelper(String deobfClassName, byte[] basicClass) {
        return transformClass(basicClass, deobfClassName, new ClassTransform() {
            @Override
            void transform(Iterator<MethodNode> methods) {
                log.println("UNIMPLEMENTED! RETURNING TO VANILLA BEHAVIOUR!");
            }
        });
    }

    private byte[] transformRenderLiving(String deobfClassName, byte[] basicClass) {
        return transformClass(basicClass, deobfClassName, new ClassTransform() {
            
            @Override
            void transform(Iterator<MethodNode> methods) {
               while(methods.hasNext()) {
                   MethodNode node = methods.next();
                   if(node.name.equals(DeobfuscationHelper.getName("prepareScale"))) {
                      
                       log.println("transform render");
                       //look at RenderGiantZombie
                   }
               }
            }
        });
    }

}
