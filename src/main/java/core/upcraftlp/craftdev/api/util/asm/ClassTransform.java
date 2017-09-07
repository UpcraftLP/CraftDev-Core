package core.upcraftlp.craftdev.api.util.asm;

import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * Class for doing asm stuff.
 * just extend this and instantiate it somewhere.
 */
public abstract class ClassTransform {
    
    protected ClassTransform() {
        TransformerUtils.register(this.getDeobfClassName(), this);
    }
    
    public abstract String getDeobfClassName();

    
    public abstract void transform(Iterator<MethodNode> methods);
}
