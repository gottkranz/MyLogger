package log.appender;

import log.appender.console.reader.ConsoleAppenderLoader;
import log.appender.console.reader.ConsoleAppenderPropertiesReader;
import log.appender.console.reader.ConsoleAppenderXMLReader;
import log.appender.file.reader.FileAppenderLoader;
import log.appender.file.reader.FileAppenderPropertiesReader;
import log.appender.file.reader.FileAppenderXMLReader;

import java.util.HashMap;
import java.util.Map;

public class AppenderFactory {

    private static Map<String, Appender> appenderMap = new HashMap<>();

    public static Appender getAppender(String appenderType){
        Appender ret = null;
        boolean correctType = true;

        AppenderLoader appenderLoader;

        //IF EXISTS
        if(appenderMap.get(appenderType) != null){
            return appenderMap.get(appenderType);
        }else{
            //IF DOESN'T EXIST
            switch (appenderType.toUpperCase()){
                case "CONSOLE":
                    appenderLoader = new ConsoleAppenderLoader();
                    ret = appenderLoader.loadAppender();
                    break;

                case "FILE":
                    appenderLoader = new FileAppenderLoader();
                    ret = appenderLoader.loadAppender();
                    break;
                default:
                    correctType = false;
            }

            //IF CORRECT NAME
            if(correctType){
                appenderMap.put(appenderType, ret);
            }
            return ret;
        }
    }
}
