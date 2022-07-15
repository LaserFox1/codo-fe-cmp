package com.lkww.codo.codo.exceptions;

import java.io.IOException;

public class CrawlerException extends RuntimeException{


    private static final String EXEC_INTERRUPT = "Error executing Python Script - execution interrupted!";
    private static final String EXEC_CMD_NOT_FOUND = "Error executing Python Script - Script not found!";
    private static final String EXEC_UNDETERMINED = "Error executing Python Script due to undetermined reason";

    private static final String READ_IO = "Error reading InputStream - InputStream not found";
    private static final String READ_UNDETERMINED = "Error reading InputStream due to undetermined reason";

    public CrawlerException(String message, Throwable rootCause){
        super(message,rootCause);
    }


    public static CrawlerException execInterrupted(InterruptedException iE) {
        return new CrawlerException(EXEC_INTERRUPT, iE);
    }
    public static CrawlerException execCmdNotFound(IOException ioE) {
        return new CrawlerException(EXEC_CMD_NOT_FOUND, ioE);
    }
    public static CrawlerException execUndetermined(Throwable t) {
        return new CrawlerException(EXEC_UNDETERMINED, t);
    }

    public static CrawlerException readIO(IOException ioE) {
        return new CrawlerException(READ_IO, ioE);
    }
    public static CrawlerException readUndetermined(Throwable t) {
        return new CrawlerException(READ_UNDETERMINED, t);
    }

}
