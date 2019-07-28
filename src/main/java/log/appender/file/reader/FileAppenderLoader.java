package log.appender.file.reader;

import log.appender.Appender;
import log.appender.AppenderLoader;
import log.appender.AppenderSettingsReader;

public class FileAppenderLoader implements AppenderLoader {
    @Override
    public Appender loadAppender() {
        AppenderSettingsReader reader;
        Appender ret;

        reader = new FileAppenderPropertiesReader();
        ret = reader.getAppender();

        //bad properties
        if(ret == null){
            reader = new FileAppenderXMLReader();
            ret = reader.getAppender();
        }

        return ret;
    }
}
