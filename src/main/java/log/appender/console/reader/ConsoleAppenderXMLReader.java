package log.appender.console.reader;

import log.appender.Appender;
import log.appender.AppenderSettingsReader;
import log.appender.console.ConsoleAppender;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsoleAppenderXMLReader implements AppenderSettingsReader {
    @Override
    public Appender getAppender() {
        try{
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("logger.xml");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);

            NodeList elements = document.getElementsByTagName("appender");

            String target, pattern;

            for (int i = 0; i < elements.getLength(); i++) {
                NamedNodeMap attributes = elements.item(i).getAttributes();

                String name = attributes.getNamedItem("name").getNodeValue();

                if(name.equals("console")){
                    pattern = getValueFromNode(attributes, "pattern",
                            "%d{yyyy-MM-dd HH:MM:SS}-%t--%-5p-%-10C:%m%n");

                    target = getValueFromNode(attributes, "target",
                            "SYSTEM_OUT");

                    return new ConsoleAppender(target, pattern);
                }
            }
            return null;
        }catch (Exception e){
            //System.err.println("Error: ConsoleAppenderXMLReader:\t" + e);
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
