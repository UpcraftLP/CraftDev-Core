package core.upcraftlp.craftdev.common.main;

import java.time.Year;

import net.minecraft.util.text.TextFormatting;

public class CraftDevReference {

	public static final String MCVERSIONS = "1.11";

	public static final String VERSION = "1.0.1";
	public static final String MODNAME = "CraftDev Core";
	public static final String MODID = "craftdevcore";
	public static final String UPDATE_JSON = "https://raw.githubusercontent.com/UpcraftLP/CraftDev-Core/master/Version.json";
	public static final String UPDATE_URL = "https://minecraft.curseforge.com/projects/craftdev-core";
	public static final String CREDITS = TextFormatting.GOLD + "\u00A9" + Year.now().getValue() + " UpcraftLP";
	public static final String[] authors = new String[] {"UpcraftLP"};
	public static final String MOD_DESCRIPTION = TextFormatting.AQUA + "core mod for Upcraft's mods";
	public static final String GUI_FACTORY = "core.upcraftlp.craftdev.common.CoreConfigGuiFactory";
	
}
