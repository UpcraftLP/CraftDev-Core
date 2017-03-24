package core.upcraftlp.craftdev.common;

import java.time.Year;

public class CraftDevReference {

    public static final String MCVERSIONS = "[1.11]";

    public static final String VERSION = "@VERSION@";
    public static final String MODNAME = "CraftDev Core";
    public static final String MODID = "craftdevcore";
    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/UpcraftLP/CraftDev-Core/master/Version.json";
    public static final String UPDATE_URL = "https://minecraft.curseforge.com/projects/craftdev-core";
    
    public static final String CREDITS = "\u00A9" + Year.now().getValue() + " UpcraftLP";
    public static final String[] authors = new String[] { "UpcraftLP" };
    public static final String MOD_DESCRIPTION = "core mod for Upcraft's mods";
    
    private static final String BASE_PACKAGE = "core.upcraftlp.craftdev";
    public static final String GUI_FACTORY = BASE_PACKAGE + ".config.CoreConfigGuiFactory";
    public static final String CLIENT_PROXY = BASE_PACKAGE + ".proxy.ClientProxy";
    public static final String SERVER_PROXY = BASE_PACKAGE + ".proxy.ServerProxy";

    public static final String[] TRANSFORMER_CLASSES = new String[] { BASE_PACKAGE + ".ASM.CraftDevClassTransformer" };
    public static final String TRANSFORMER_HOLDER = BASE_PACKAGE + ".ASM.CraftDevClassTransformer";
}
