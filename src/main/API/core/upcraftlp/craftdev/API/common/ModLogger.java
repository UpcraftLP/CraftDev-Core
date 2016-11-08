package core.upcraftlp.craftdev.API.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModLogger {

	private Logger log;
	
	public ModLogger(String modid) {
		log = LogManager.getLogger(modid.substring(0, 1).toUpperCase() + modid.substring(1).toLowerCase());
	}
	
	public void println(String output)
	{
		this.log.info(output);
	}
	
	public void errFatal(String output) {
		this.log.fatal(output);
	}
	
}
