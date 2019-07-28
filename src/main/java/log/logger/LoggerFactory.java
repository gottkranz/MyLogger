package log.logger;

import log.appender.AppenderFactory;
import log.logger.reader.LoggerLoader;
import log.logger.reader.LoggerPropertiesReader;
import log.logger.reader.LoggerSettingsReader;

import java.util.HashMap;
import java.util.Map;

public class LoggerFactory {

    private static Map<String, Logger> loggerMap = new HashMap<>();

    public static Logger getLogger(String name){
        //IF DOESN'T EXIST YET --- CREATE
        if(!loggerMap.containsKey(name)){
            Logger logger = LoggerLoader.getLogger(name);

            loggerMap.put(name, logger);
        }

        //exists or created --- return
        return loggerMap.get(name);
    }

    public static Logger getLogger(Class clazz){
        return getLogger(clazz.getCanonicalName());
    }
}
