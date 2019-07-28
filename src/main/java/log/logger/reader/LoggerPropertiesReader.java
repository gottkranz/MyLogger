package log.logger.reader;

import log.appender.Appender;
import log.appender.AppenderFactory;
import log.logger.Level;
import log.logger.Logger;

import java.io.InputStream;
import java.util.*;

public class LoggerPropertiesReader implements LoggerSettingsReader {

    @Override
    public Logger getLogger(String loggerName) {
        try{
            //create properties
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("logger.properties");

            Properties properties = new Properties();

            properties.load(inputStream);

            //refactor properties
            String str = properties.getProperty(loggerName).replaceAll(" ", "");
            String[] args = str.split(",");

            //read lvl
            Level level = Level.toLevel(args[0]);
            Map<String, Appender> appenderMap = new HashMap<>();

            //read appender list
            for (int i = 1; i < args.length; i++) {
                String arg = args[i];
                appenderMap.put(arg, AppenderFactory.getAppender(arg));
            }

            Logger logger = new Logger(loggerName, level, appenderMap);

            return logger;
        }catch (Exception e){
            //System.err.println("LoggerFactory:\t" + e);
            //System.err.println("No logger properties settings:\t" + loggerName);
            return null;
        }
    }
}
