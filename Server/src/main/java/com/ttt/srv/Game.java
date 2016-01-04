package com.ttt.srv;
import java.util.Arrays;
import com.ttt.srv.Exception.GameStateException;

import java.util.List;

/**
 * Created by marko on 21.10.2015.
 */
public class Game {
    private int[] state;

    public Game() {

        this.state = new int[]{0,0,0,0,0,0,0,0,0};
        System.out.println("New Game created. state : " + Arrays.toString(this.getState()));
    }

    public int[] turn(int index, int mark) throws GameStateException
    {
        //TODO check if valid state manipulation
        if (this.state[index] > 0) {
            throw new GameStateException("Invalid turn!");
        }
        this.state[index] = mark;
        return this.state;
    }

    public int[] getState()
    {
        return this.state;
    }
}
