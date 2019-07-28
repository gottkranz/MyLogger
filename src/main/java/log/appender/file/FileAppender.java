package log.appender.file;

import log.appender.Appender;
import log.logger.Level;
import log.message.MessageCompiler;

import java.io.File;
import java.io.FileWriter;

public class FileAppender implements Appender {
//VARIABLES
    private final long maxFileByteSize; //bytes

    private String logFilePath;
    private String logFileTail;

    private int fileCounter;

    private File logFile;

    private final MessageCompiler messageCompiler;
    private FileWriter fileWriter;

    private final boolean append;


//CONSTRUCTOR
    public FileAppender(String pattern, String maxFileByteSize, String logFilePath, boolean append) throws Exception{
        messageCompiler = new MessageCompiler(pattern);
        this.maxFileByteSize = getByteSize(maxFileByteSize);

        this.append = append;

        int lastIndex = logFilePath.lastIndexOf('.');
        if(lastIndex != -1){
            this.logFilePath = logFilePath.substring(0, lastIndex);
            logFileTail = logFilePath.substring(lastIndex);
        }else{
            this.logFilePath = logFilePath;
            logFileTail = "";
        }


        fileCounter = 0;

        logFile = new File(logFilePath);
        try{
            fileWriter = new FileWriter(logFile, append);
        }catch (Exception e){
            System.err.println("Error: FileAppender append\t" + e);
        }
    }


//METHODS
    @Override
    public void append(String message, Level priorityLevel) {
        if(!isAllowedSize()){
            fileCounter++;

            logFile = new File(logFilePath + fileCounter + logFileTail);
            try{
                fileWriter = new FileWriter(logFile);
            }catch (Exception e){
                System.err.println("Error: FileAppender append\t" + e);
            }
        }

        try{
            fileWriter.write(messageCompiler.compileMessage(message, priorityLevel.toString()));
            fileWriter.flush();
        }catch (Exception e){
            System.err.println("Error: FileAppender append:\t" + e);
        }

    }

    private boolean isAllowedSize(){
        return logFile.length() < maxFileByteSize;
    }

    //INIT
    private long getByteSize(String size){
        try{
            String args[] = size.split(" ");
            long numberSize = Long.valueOf(args[0]);

            if(numberSize < 1){
                throw new Exception("Bad FileAppender maxFileSize");
            }

            long koef;

            switch (args[1].toUpperCase().charAt(0)){
                case 'B':
                    koef = 1;
                    break;

                case 'K':
                    koef = (long) Math.pow(2, 10);
                    break;

                case 'M':
                    koef = (long) Math.pow(2, 20);
                    break;

                case 'G':
                    koef = (long) Math.pow(2, 30);
                    break;
                default:
                    koef = 1;
            }

            return koef * numberSize;

        }catch (Exception e){
            System.err.println("Bad maxFileByteSize");
            return 10 * (long) Math.pow(2, 20);
        }

    }
}
