package core.upcraftlp.craftdev.API.util;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import core.upcraftlp.craftdev.API.util.Loggers.ModLogger;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;

public abstract class EventHandler {
    
    protected static final Side[] SERVER = new Side[]{Side.SERVER};
    protected static final Side[] CLIENT = new Side[]{Side.CLIENT};
    protected static final Side[] ALL = new Side[]{Side.SERVER, Side.CLIENT};
    protected static final ModLogger log = CraftDevCore.getLogger();
    
    /**
     * @param effectiveSide the current effective {@link Side} to allow for registration on only one side.
     */
    public EventHandler(Side effectiveSide) {
        if(this.getIncompatibleModIDs() != null) {
            for(String id : this.getIncompatibleModIDs()) {
                if(ModHelper.getIsModLoaded(id)) {
                    log.println("found mod " + id + ", skipping registration of " + this.getClass().getSimpleName());
                    return;
                }
            }
        }
        for(Side side : this.getSides()) {
            if(side == effectiveSide) this.getBus().register(this);
        }
        log.println("successfully registered EventHandler: " + this.getClass().getSimpleName());
    }
    
    /**
     * @return a list of incompatible mods to prevent feature overlapping
     */
    @Nullable
    public List<String> getIncompatibleModIDs() {
        return null;
    }
    
    /**
     * the {@link EventBus} to register this EventHandler to
     * @return
     */
    @Nonnull
    public EventBus getBus() {
        return MinecraftForge.EVENT_BUS;
    }
    
    /**
     * the {@link Side}s to register this EventHandler on<br/>
     */
    @Nonnull
    public abstract Side[] getSides();

}
