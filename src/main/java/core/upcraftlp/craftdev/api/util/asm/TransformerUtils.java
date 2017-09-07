package core.upcraftlp.craftdev.api.util.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransformerUtils  {
    
    private static final Map<String, ClassTransform> transformers = new HashMap<>();
    
    /**
     * register a new {@link ClassTransform}
     * internal use only!
     */
    static void register(String deobfClassName, ClassTransform transform) throws IllegalArgumentException {
        if(deobfClassName != null && !deobfClassName.isEmpty() && transform != null) {
            transformers.put(deobfClassName, transform);
        }
        else throw new IllegalArgumentException("tried to register a null value!");
    }
    
    /**
     * helper method, for internal use only!
     */
    public static byte[] transformClass(String deobfClassName, byte[] basicClass) {
        if(transformers.containsKey(deobfClassName)) return transformInternal(deobfClassName, basicClass);
        else return basicClass;
    }
    
    private static byte[] transformInternal(String deobfName, byte[] basicClass) {
        ClassTransform transformer = transformers.get(deobfName);
        ClassNode cn = new ClassNode();
        ClassReader cr = new ClassReader(basicClass);
        cr.accept(cn, 0);
        Iterator<MethodNode> methods = cn.methods.iterator();
        System.out.println("transforming class " + deobfName);
        transformer.transform(methods);
        
        // asm specific for cleaning up and returning the final bytes for JVM processing.
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(writer);

        System.out.println("successfully transformed " + deobfName + "!");
        return writer.toByteArray();
    }
    
}
