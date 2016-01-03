package com.ttt.srv;

/**
 * Created by markonou on 15.10.2015.
 */
public class Player {
    private String name;
    private int idMark;
    private int gameId;

    public ClientHandler handler;

    public Player(String name) {
         this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIdMark() {
        return idMark;
    }

    public void setIdMark(int mark) {
        this.idMark = mark;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
