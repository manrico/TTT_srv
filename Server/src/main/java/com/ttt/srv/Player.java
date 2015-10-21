package com.ttt.srv;

/**
 * Created by markonou on 15.10.2015.
 */
public class Player {
    private String name;
    private int id;
    private int mark;
    private int gameId;

    public ClientHandler handler;

    public Player(String name) {
         this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
