package core.upcraftlp.craftdev.API.util;

import java.util.HashMap;
import java.util.Locale;
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

        
        /**
         * Factory method to create an{@link ModLogger}
         */
        public static ModLogger create(String modid) {
            return get(modid);
        }
        
        private Logger log;

        private ModLogger(String modid) {
            log = LogManager.getLogger(modid.toLowerCase(Locale.ROOT));
        }

        public void println(String output, Object... params) {
            this.log.info(output, params);
        }

        public void errFatal(String output, Object... params) {
            this.log.fatal(output, params);
        }
    }
}
