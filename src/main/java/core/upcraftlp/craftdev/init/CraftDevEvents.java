package core.upcraftlp.craftdev.init;

import core.upcraftlp.craftdev.client.MobScaleHandler;
import core.upcraftlp.craftdev.events.ChestBreakHandler;
import core.upcraftlp.craftdev.events.CreeperFireHandler;
import core.upcraftlp.craftdev.events.DeathMessageHandler;
import core.upcraftlp.craftdev.events.ZomBabyFireHandler;
import net.minecraftforge.fml.relauncher.Side;

public class CraftDevEvents {

    public static void init(Side side) {
        new ZomBabyFireHandler(side);
        new CreeperFireHandler(side);
        new DeathMessageHandler(side);
        new ChestBreakHandler(side);
        new MobScaleHandler(side);
    }
}
