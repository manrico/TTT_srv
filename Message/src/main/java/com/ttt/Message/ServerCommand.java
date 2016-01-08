package com.ttt.Message;

/**
 * Created by markonou on 15.10.2015.
 */
public enum ServerCommand implements Command{
    PONG,
    REGISTER_OK,
    ERROR,
    GAME_START,
    YOUR_TURN,
    STATE,
    DRAW
}