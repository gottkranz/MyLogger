package log.appender.file.reader;

import log.appender.Appender;
import log.appender.AppenderSettingsReader;
import log.appender.file.FileAppender;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileAppenderXMLReader implements AppenderSettingsReader {
    @Override
    public Appender getAppender() {
        try{
            //INIT
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("logger.xml");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);

            NodeList elements = document.getElementsByTagName("appender");

            String maxFileByteSize, pattern, logFilePath;

            //READ
            for (int i = 0; i < elements.getLength(); i++) {
                NamedNodeMap attributes = elements.item(i).getAttributes();

                String name = attributes.getNamedItem("name").getNodeValue();

                if(name.equals("file")){
                    //PATTERN
                    pattern = getValueFromNode(attributes, "pattern",
                            "%d{yyyy-MM-dd HH:MM:SS}-%t--%-5p-%-10C:%m%n");

                    //MAX SIZE
                    maxFileByteSize = getValueFromNode(attributes, "maxFileSize", "10 MB");

                    //PATH
                    logFilePath = getValueFromNode(attributes, "path", "log.txt");

                    //APPEND
                    boolean append = Boolean
                            .parseBoolean(getValueFromNode(attributes, "append", "true"));

                    return new FileAppender(pattern, maxFileByteSize, logFilePath, append);
                }
            }
            return null;
        }catch (Exception e){
            System.err.println("Error: FileAppenderXMLReader:\t" + e);
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
