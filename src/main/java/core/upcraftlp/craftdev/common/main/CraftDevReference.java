package core.upcraftlp.craftdev.common.main;

import java.time.Year;

import net.minecraft.util.text.TextFormatting;

public class CraftDevReference {

	public static final String MCVERSIONS = "1.10.2,1.9.4";
	
	private static final int MAJOR = 0;
	private static final int MINOR = 1;
	private static final int PATCH = 0;
	private static final int BUILD = 1;
	
	public static final String VERSION = MAJOR + "." + MINOR + "." + PATCH + "-b" + BUILD;
	public static final String MODNAME = "CraftDev Core";
	public static final String MODID = "craftdev-core";
	public static final String INTERNAL_UPDATE_URL = ""; //TODO: Update-URL
	public static final String UPDATE_URL = ""; //TODO: CurseForge Page
	public static final String CREDITS = TextFormatting.GOLD + "\u00A9" + Year.now().getValue() + " UpcraftLP";
	public static final String[] authors = new String[] {"UpcraftLP"};
	public static final String MOD_DESCRIPTION = TextFormatting.AQUA + "core mod for UpcraftLP's mods";
	
}
