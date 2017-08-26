package core.upcraftlp.craftdev.asm;

import core.upcraftlp.craftdev.api.util.asm.TransformerUtils;
import core.upcraftlp.craftdev.asm.tweaks.TweakBiome;
import core.upcraftlp.craftdev.asm.tweaks.TweakEnchantHelper;
import core.upcraftlp.craftdev.asm.tweaks.TweakEntityFireRender;
import core.upcraftlp.craftdev.asm.tweaks.TweakSweepAttack;
import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@IFMLLoadingPlugin.MCVersion("1.11.2") //FIXME mcversion annotation
public class CraftDevClassTransformer implements IClassTransformer, IFMLCallHook {

    private static final Random rand = new Random();

    static {
        new TweakEnchantHelper();
        new TweakBiome();
        new TweakEntityFireRender();
        new TweakSweepAttack();
    }
    
    private static final Logger log = CraftDevCore.getLogger();

    @Override
    public byte[] transform(String name, String deobfClassName, byte[] basicClass) {
        return TransformerUtils.transformClass(deobfClassName, basicClass);
    }
    
    private static final String[] quotes = new String[]{ //TODO: funny Strings ;)
            "You're going to die in " + (Integer.MAX_VALUE - (int) (System.currentTimeMillis() / 1000L)), //Year 2038 is coming :P
            "What about this?",
            "Who called me?",
            "Can I be your friend?",
            "Nerf THIS!",
            "Please report this crash to the Forge authors!",
            "Can you hear me?",
            "Coded suckz",
            "get your crashes here",
            "I am Groot",
            "I don't blame you",
            UUID.randomUUID().toString(),
            "DIE! DIE! DIE!",
            "Hello, would you like to hear a TCP joke?\n" +
                    "Yes, I'd like to hear a TCP joke.\n" +
                    "OK, I'll tell you a TCP joke.\n" +
                    "OK, I'll hear a TCP joke.\n" +
                    "Are you ready to hear a TCP joke?\n" +
                    "Yes, I am ready to hear a TCP joke.\n" +
                    "OK, I'm about to send the TCP joke. It will last 10 seconds, it has two characters, it does not have a setting, it ends with a punchline.\n" +
                    "OK, I'm ready to hear the TCP joke that will last 10 seconds, has two characters, does not have a setting and will end with a punchline.\n" +
                    "I'm sorry, your connection has timed out...\n" +
                    "Hello, would you like to hear a TCP joke?",
            "I'd tell you a UDP joke, but you probably won't get it ;)",
            "Okay, here it is:",
            "UDP packet bar walks a into",
            "This is not a BUG, it's a FEATURE",
            "how many programmers does it take to change a light bulb?\n" +
                    " - none, that's a hardware problem",
            "just BASH it ;-)",
            "Go -f>@+?*<.-&'_:$#/%! yourself!",
            "Quite honestly, I wouldn't worry myself about that."
    };

    @Override
    public Void call() throws Exception {
       log.info("Hello Minecraft!");
       log.info(quotes[rand.nextInt(quotes.length)]);
       if(CraftDevLoadingPlugin.isDeobfuscatedEnvironment()) log.warn("deobfuscated environment detected!");
       return null; //return null or throw an exception
    }
    
    /**
     * {@link IFMLCallHook#injectData(Map)}
     */
    @Override
    public void injectData(Map<String, Object> data) {}

}
