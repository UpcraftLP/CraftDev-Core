package core.upcraftlp.craftdev.API.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Loggers {

    public static ModLogger get(String modid) {
        if ( !loggers.containsKey(modid) ) {
            loggers.put(modid, new ModLogger(modid));
        }
        return loggers.get(modid);
    }

    private static final Map<String, ModLogger> loggers = new HashMap<String, ModLogger>();

    public static class ModLogger {

        private Logger log;

        public ModLogger(String modid) {
            log = LogManager.getLogger(modid.substring(0, 1).toUpperCase() + modid.substring(1).toLowerCase());
        }

        public void println(String output, Object... params) {
            this.log.info(output, params);
        }

        public void errFatal(String output, Object... params) {
            this.log.fatal(output, params);
        }
    }
}
