package log.appender.console.reader;

import log.appender.Appender;
import log.appender.AppenderLoader;
import log.appender.AppenderSettingsReader;

public class ConsoleAppenderLoader implements AppenderLoader {
    @Override
    public Appender loadAppender() {
        AppenderSettingsReader reader;
        Appender ret;

        reader = new ConsoleAppenderPropertiesReader();
        ret = reader.getAppender();

        //bad properties
        if(ret == null){
            reader = new ConsoleAppenderXMLReader();
            ret = reader.getAppender();
        }

        return ret;
    }
}
