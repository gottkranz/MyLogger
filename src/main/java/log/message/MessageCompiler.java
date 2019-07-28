package log.message;


import java.text.SimpleDateFormat;
import java.util.*;

public class MessageCompiler {
//VARIABLES
    //FORMATS
    protected SimpleDateFormat simpleDateFormat;


    protected StringBuilder formatPattern;
    protected String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    protected List<Character> requiredSignList;

    //CHANGEABLE while messaging
    protected StringBuilder finalMessageStringBuilder;


    //REQUIRED SPECIAL PATTERN SIGNS
    protected final char CLASS_NAME = 'C';
    protected final char FILE_NAME = 'F';

    protected final char LINE = 'L';
    protected final char MESSAGE = 'm';

    protected final char DATE = 'd';
    protected final char PRIORITY = 'p';
    protected final char THREAD = 't';


    //PARAMETER SPECIAL SIGNS
    protected final char OPEN_PARAMETER_STRING = '{';
    protected final char CLOSE_PARAMETER_STRING = '}';


//CONSTRUCTOR
    public MessageCompiler(String formatPattern) throws Exception{
        requiredSignList = new ArrayList<>();

        this.formatPattern = new StringBuilder(formatPattern);

        finalMessageStringBuilder = new StringBuilder();

        refactorCommonPattern();
    }


//METHODS
    public String compileMessage(String message, String priorityLevel){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[7];

        String[] args = formArgsArray(message, stackTraceElement, priorityLevel);

        String ret = String.format(formatPattern.toString(), args);
        //

        return ret;
    }

    protected String[] formArgsArray(String message, StackTraceElement stackTraceElement, String priorityLevel){
        String[] args = new String[requiredSignList.size()];

        for (int i = 0; i < requiredSignList.size(); i++) {

            char sign = requiredSignList.get(i);

            switch (sign){
                case CLASS_NAME:
                    args[i] = stackTraceElement.getClassName();
                    break;

                case FILE_NAME:
                    args[i] = stackTraceElement.getFileName();
                    break;

                case LINE:
                    args[i] = String.valueOf(stackTraceElement.getLineNumber());
                    break;

                case MESSAGE:
                    args[i] = message;
                    break;

                case DATE:
                    Calendar calendar = new GregorianCalendar();
                    args[i] = simpleDateFormat.format(calendar.getTime());
                    break;

                case PRIORITY:
                    args[i] = priorityLevel;
                    break;

                case THREAD:
                    Thread thread = Thread.currentThread();
                    args[i] = thread.getName();
                    break;
            }
        }
        return args;
    }



    //INIT
    protected void refactorCommonPattern(){
        char[] chars = {CLASS_NAME, FILE_NAME, LINE, MESSAGE, DATE, PRIORITY, THREAD};
        /*String allSigns = CLASS_NAME + FILE_NAME + LOCATION + LINE + MESSAGE + DATE
                + PRIORITY + THREAD + "";*/
        String allSigns = String.copyValueOf(chars);

        //check all pattern chars
        for(int counter = 0; counter < formatPattern.length(); counter++){
            char ch = formatPattern.charAt(counter);

            //if special sign
            if(allSigns.indexOf(ch) != -1){

                switch (ch){
                    case DATE:
                        String datePattern = getParameter(counter);
                        if(datePattern.equals("")){
                            datePattern = DEFAULT_DATE_PATTERN;
                        }
                        simpleDateFormat = new SimpleDateFormat(datePattern);
                        break;
                }

                onSignRemarked(counter);
            }
        }
    }

    private void onSignRemarked(int index){
        char sign = formatPattern.charAt(index);
        requiredSignList.add(sign);

        char replacement = 's';
        formatPattern.setCharAt(index, replacement);
    }

    private String getParameter(int patternReadCursorPosition){
        StringBuilder datePatternBuilder = new StringBuilder();

        int readPoint;

        try {
            readPoint = patternReadCursorPosition + 1;
        }catch (Exception e){
            return  "";
        }
        //if no parameters
        if(formatPattern.charAt(readPoint) != OPEN_PARAMETER_STRING){
            return "";
    }

        //read parameter string
        while(readPoint < formatPattern.length()){
            char ch = formatPattern.charAt(readPoint);

            //clean main pattern
            formatPattern.deleteCharAt(readPoint);

            if(ch != CLOSE_PARAMETER_STRING){
                if(ch != OPEN_PARAMETER_STRING)
                    datePatternBuilder.append(ch);
            }else{
                break;
            }
        }

        return datePatternBuilder.toString();
    }
}
