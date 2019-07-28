package log.logger;

import log.appender.Appender;
import log.appender.AppenderFactory;

import java.util.HashMap;
import java.util.Map;

public class Logger {
    public final String name;

    private Map<String, Appender> appenderMap;

    private Level level;

    public Logger(String name, Level level, Map<String, Appender> appenderMap){
        this.name = name;
        this.level = level;
        this.appenderMap = appenderMap;
    }

    public Logger(){
        this.name = "default_logger";
        this.level = Level.DEBUG;
        appenderMap = new HashMap<>();
        appenderMap.put("console", AppenderFactory.getAppender("console"));
    }

    public Map<String, Appender> getCoppyAppenderMap(){
        return new HashMap<>(appenderMap);
    }
    public Level getLevel(){
        return level;
    }

    public void write(String message, Level level){
        if(this.level.lessOrEquals(level)){
            appenderMap.forEach((key, appender) -> {
                appender.append(message, level);
            });
        }
    }

    public void trace(String message){
        write(message, Level.TRACE);
    }

    public void debug(String message){
        write(message, Level.DEBUG);
    }

    public void info(String message){
        write(message, Level.INFO);
    }

    public void warn(String message){
        write(message, Level.WARN);
    }

    public void error(String message){
        write(message, Level.ERROR);
    }

    public void fatal(String message){
        write(message, Level.FATAL);
    }
}
