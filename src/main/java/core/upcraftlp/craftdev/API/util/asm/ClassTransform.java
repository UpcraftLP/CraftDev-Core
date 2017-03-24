package core.upcraftlp.craftdev.API.util.asm;

import java.util.Iterator;

import org.objectweb.asm.tree.MethodNode;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.common.CraftDevCore;

/**
 * Class for doing ASM stuff.
 * just extend this and instantiate it somewhere.
 */
public abstract class ClassTransform {
    
    /**
     * a {@link ModLogger} to log things
     */
    protected static final ModLogger log = CraftDevCore.getLogger();
    
    protected ClassTransform() {
        TransformerUtils.register(this.getDeobfClassName(), this);
    }
    
    public abstract String getDeobfClassName();

    
    public abstract void transform(Iterator<MethodNode> methods);
}
