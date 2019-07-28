package log.appender.console.reader;

import log.appender.Appender;
import log.appender.AppenderSettingsReader;
import log.appender.console.ConsoleAppender;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConsoleAppenderPropertiesReader implements AppenderSettingsReader {
    @Override
    public Appender getAppender() {
        try{
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("logger.properties");

            Properties properties = new Properties();

            properties.load(inputStream);

            String target, pattern;

            target = properties.getProperty("appender.console.target");
            pattern = properties.getProperty("appender.console.pattern");

            //DEFAULT VALUES
            if(target == null) target = "SYSTEM_OUT";
            if(pattern == null) pattern = "%d{yyyy-MM-dd HH:MM:SS}-%t--%-5p-%-10C:%m%n";

            return new ConsoleAppender(target, pattern);
        }catch (Exception e){
            //System.err.println("ConsoleAppender:\t" + e);
            return null;
        }
    }
}
