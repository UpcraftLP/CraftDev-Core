package core.upcraftlp.craftdev.api.util.asm;

import core.upcraftlp.craftdev.common.CraftDevCore;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * Class for doing asm stuff.
 * just extend this and instantiate it somewhere.
 */
public abstract class ClassTransform {
    
    /**
     * a {@link Logger} to log things
     */
    protected static final Logger log = CraftDevCore.getLogger();
    
    protected ClassTransform() {
        TransformerUtils.register(this.getDeobfClassName(), this);
    }
    
    public abstract String getDeobfClassName();

    
    public abstract void transform(Iterator<MethodNode> methods);
}
