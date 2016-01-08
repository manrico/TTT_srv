package com.ttt.srv;

import com.ttt.srv.Exception.GameMarkException;
import com.ttt.srv.Exception.GameStateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marko on 21.10.2015.
 * Holds the state of the game, has methods to change it, and check if change is allowed.
 * Decides the winner.
 */
public class Game {
    private int[] state;

    public Game() {
        this.state = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        System.out.println("New Game created. state : " + Arrays.toString(this.getState()));
    }

    public int[] turn(int mark, int index) throws GameStateException, GameMarkException {
        if (this.state[index] > 0) {
            throw new GameStateException("Invalid turn!");
        }
        if (mark != 1 || mark != 2) {
            throw new GameMarkException("Invalid game mark!");
        }
        this.state[index] = mark;
        return this.state;
    }

    /**
     * We get the state of the game and lay it on Magic Square.
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * @return int player idMark (1 or 2) or 0 if no winner yet, 3 if draw and game is over.
     */
    public int getWinner(int[] state) {
        List<int[]> positions = new ArrayList<>();
        positions.add(new int[]{0, 1, 2});
        positions.add(new int[]{3, 4, 5});
        positions.add(new int[]{6, 7, 8});
        positions.add(new int[]{0, 3, 6});
        positions.add(new int[]{1, 4, 7});
        positions.add(new int[]{2, 5, 8});
        positions.add(new int[]{0, 4, 8});
        positions.add(new int[]{2, 4, 6});

        // check if player with idMark 1 has win
        for(int[] position : positions) {
            if (state[position[0]] == 1 && state[position[1]] == 1 && state[position[2]] == 1) {
                System.out.println("1 wins.");
                return 1;
            }
            if (state[position[0]] == 2 && state[position[1]] == 2 && state[position[2]] == 2) {
                System.out.println("2 wins.");
                return 2;
            }
        }

        // if there are no more zeroes in state, it must be a draw.
        Arrays.sort(state);
        if (Arrays.binarySearch(state, 0) == -1) {
            System.out.println("draw.");
            return 3;
        }
        // no winner yet, let the game resume.
        return 0;
    }

    public int[] getState() {
        return this.state;
    }
}
