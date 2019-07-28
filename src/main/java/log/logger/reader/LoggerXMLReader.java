package log.logger.reader;

import log.appender.Appender;
import log.appender.AppenderFactory;
import log.appender.file.FileAppender;
import log.logger.Level;
import log.logger.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoggerXMLReader implements LoggerSettingsReader {
    @Override
    public Logger getLogger(String loggerName) {
        try{
            //INIT
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("logger.xml");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);

            NodeList elements = document.getElementsByTagName("logger");

            String levelStr, appenders;

            //READ
            for (int i = 0; i < elements.getLength(); i++) {
                NamedNodeMap attributes = elements.item(i).getAttributes();

                String name = attributes.getNamedItem("name").getNodeValue();

                if(name.equals(loggerName)){
                    //LEVEL
                    levelStr = getValueFromNode(attributes, "level",
                            "DEBUG");
                    Level level = Level.toLevel(levelStr);

                    //APPENDERS
                    appenders = getValueFromNode(attributes, "appenders", "console");

                    String str = appenders.replaceAll(" ", "");
                    String[] args = str.split(",");

                    Map<String, Appender> appenderMap = new HashMap<>();

                    //read appender list
                    for (int j = 0; j < args.length; j++) {
                        String arg = args[j];
                        appenderMap.put(arg, AppenderFactory.getAppender(arg));
                    }

                    //APPEND
                    boolean append = Boolean
                            .parseBoolean(getValueFromNode(attributes, "append", "true"));

                    return new Logger(loggerName, level, appenderMap);
                }
            }
            return null;
        }catch (Exception e){
            //System.err.println("Error: LoggerXMLReader:\t" + e);
            return null;
        }
    }

    private String getValueFromNode(NamedNodeMap attributes, String itemName, String defaultValue){
        Node node = attributes.getNamedItem(itemName);
        String value;
        if(node != null){
            value = node.getNodeValue();
        }else{
            value = defaultValue;
        }
        return value;
    }
}
