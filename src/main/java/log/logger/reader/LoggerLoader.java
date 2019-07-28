package log.logger.reader;

import log.logger.Logger;
import log.logger.LoggerFactory;

public class LoggerLoader {
    public static Logger getLogger(String name){
        LoggerSettingsReader reader = new LoggerPropertiesReader();
        Logger logger = reader.getLogger(name);

        //if bad properties --- other setting files
        if(logger == null){
            reader = new LoggerXMLReader();
            logger = reader.getLogger(name);
        }

        //if no settings - copy from rootLogger
        if(logger == null && !name.equals("rootLogger")){
            Logger rootLogger = LoggerFactory.getLogger("rootLogger");

            logger = new Logger(name, rootLogger.getLevel(), rootLogger.getCoppyAppenderMap());
        }
        return logger;
    }
}
