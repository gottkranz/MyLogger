package log.appender.console;

import log.appender.Appender;
import log.logger.Level;
import log.message.MessageCompiler;

public class ConsoleAppender implements Appender {

    private final String TARGET;
    private final MessageCompiler messageCompiler;

    public ConsoleAppender(String target, String pattern) throws Exception{
        messageCompiler = new MessageCompiler(pattern);

        switch (target.toUpperCase()){
            case "SYSTEM_OUT":
                TARGET = target;
                break;
            case "SYSTEM_ERR":
                TARGET = target;
                break;
                default:
                    System.out.println("Bad console target");
                    TARGET = "SYSTEM_OUT";
        }
    }

    public void append(String message, Level priorityLevel) {
        String compiledMessage = messageCompiler.compileMessage(message, priorityLevel.toString());
        switch (TARGET){
            case "SYSTEM_OUT":
                System.out.println(compiledMessage);
                break;
            case "SYSTEM_ERR":
                System.err.println(compiledMessage);
                break;
        }
    }
}
