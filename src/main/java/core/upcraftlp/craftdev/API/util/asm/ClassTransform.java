package core.upcraftlp.craftdev.api.util.asm;

import java.util.Iterator;

import org.objectweb.asm.tree.MethodNode;

import core.upcraftlp.craftdev.api.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.common.CraftDevCore;

/**
 * Class for doing asm stuff.
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
