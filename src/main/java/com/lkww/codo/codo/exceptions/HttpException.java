package com.lkww.codo.codo.exceptions;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpException extends RuntimeException{
    private static final String CANNOT_POST_IO = "Cannot send HTTP-Post request - httpclient not found!";
    private static final String CANNOT_POST_URI = "Cannot send HTTP-Post request - URI Syntax not valid!";

    public HttpException(String message, Throwable rootCause){
        super(message,rootCause);
    }

    public static HttpException cannotPostIO(IOException ioE){
        return new HttpException(CANNOT_POST_IO, ioE);
    }
    public static HttpException cannotPostURI(URISyntaxException uE) {
        return new HttpException(CANNOT_POST_URI, uE);
    }
}
