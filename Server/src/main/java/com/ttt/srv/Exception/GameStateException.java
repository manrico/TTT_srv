package com.ttt.srv.Exception;

/**
 * Created by marko on 21.10.2015.
 */
public class GameStateException extends Exception {
    public GameStateException() {
        super(); }
    public GameStateException(String message) { super(message); }
    public GameStateException(String message, Throwable cause) { super(message, cause); }
    public GameStateException(Throwable cause) { super(cause); }
}
