package log.appender.file.reader;

import log.appender.Appender;
import log.appender.AppenderSettingsReader;
import log.appender.file.FileAppender;

import java.io.InputStream;
import java.util.Properties;

public class FileAppenderPropertiesReader implements AppenderSettingsReader {
    @Override
    public Appender getAppender() {
        try{
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("logger.properties");

            Properties properties = new Properties();

            properties.load(inputStream);

            String pattern, maxFileByteSize, logFilePath, appendStr;
            boolean append;

            maxFileByteSize = properties
                    .getProperty("appender.file.maxFileSize");
            pattern = properties.getProperty("appender.file.pattern");
            logFilePath = properties.getProperty("appender.file.path");

            appendStr = properties.getProperty("appender.file.append");


            //DEFAULT VALUES
            if(maxFileByteSize == null) maxFileByteSize = "10 MB";
            if(pattern == null) pattern = "%d{yyyy-MM-dd}-%t--%-5p-%-10C:%m%n";
            if(logFilePath == null) logFilePath = "defaultLogFile.txt";

            append = Boolean.parseBoolean(appendStr);

            return new FileAppender(pattern, maxFileByteSize, logFilePath, append);
        }catch (Exception e){
            //System.err.println("FileAppender:\t" + e);
            return null;
        }
    }
}
