package com.ttt.srv.Exception;

/**
 * Created by marko on 21.10.2015.
 */
public class GameMarkException extends Exception {
    public GameMarkException() {
        super(); }
    public GameMarkException(String message) { super(message); }
    public GameMarkException(String message, Throwable cause) { super(message, cause); }
    public GameMarkException(Throwable cause) { super(cause); }
}
