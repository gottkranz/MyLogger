package log.logger;

import java.io.Serializable;

public class Level implements Serializable {

//VARIABLES
    protected transient int levelValue;
    protected transient String levelString;

    //LEVEL VALUES
    public static final int ALL_VALUE = 0;
    public static final int TRACE_VALUE = 1;

    public static final int DEBUG_VALUE = 2;
    public static final int INFO_VALUE = 3;
    public static final int WARN_VALUE = 4;
    public static final int ERROR_VALUE = 5;
    public static final int FATAL_VALUE = 6;

    public static final int OFF_VALUE = 7;

    //LEVELS
    public static final Level ALL = new Level(ALL_VALUE, "ALL");
    public static final Level TRACE = new Level(TRACE_VALUE, "TRACE");

    public static final Level DEBUG = new Level(DEBUG_VALUE, "DEBUG");
    public static final Level INFO = new Level(INFO_VALUE, "INFO");
    public static final Level WARN = new Level(WARN_VALUE, "WARN");
    public static final Level ERROR = new Level(ERROR_VALUE, "ERROR");
    public static final Level FATAL = new Level(FATAL_VALUE, "FATAL");

    public static final Level OFF = new Level(OFF_VALUE, "OFF");


//CONSTRUCTOR
    protected Level(){
        levelValue = 2;
        levelString = "DEBUG";
    }

    protected Level(int levelValue, String levelString){
        this.levelValue = levelValue;
        this.levelString = levelString;
    }


//METHODS

    public static Level toLevel(String levelString){
        switch (levelString){
            case "ALL":
                return ALL;
            case "TRACE":
                return TRACE;
            case "DEBUG":
                return DEBUG;
            case "INFO":
                return INFO;
            case "WARN":
                return WARN;
            case "ERROR":
                return ERROR;
            case "FATAL":
                return FATAL;
            case "OFF":
                return OFF;
            default:
                System.err.println("Bad level string");
                return DEBUG;
        }
    }

    public boolean lessOrEquals(Level level){
        if(level.levelValue >= this.levelValue){
            return true;
        }else{
            return false;
        }
    }

    public String toString(){
        return levelString;
    }

    public boolean equals(Object o){
        if(o instanceof Level){
            Level level = (Level) o;
            return level.levelValue == this.levelValue;
        }
        return false;
    }

    public int hashCode(){
        return levelValue;
    }
}
