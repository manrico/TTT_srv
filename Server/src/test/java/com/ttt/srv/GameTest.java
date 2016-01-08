package com.ttt.srv;

import junit.framework.TestCase;

/**
 * Created by marko on 08.01.2016.
 */
public class GameTest extends TestCase {

    public void testGetNoWinner() throws Exception {
        int [] state = new int[]{1,0,0,0,0,0,0,0,0};
        Game game = new Game();
        assertEquals(0,game.getWinner(state));
    }

    /**
     * X0X
     * X00
     * 00X
     * @throws Exception
     */
    public void test2wins() throws Exception {
        int [] state = new int[]{1,2,1,1,2,2,2,2,1};
        Game game = new Game();

        assertEquals(2,game.getWinner(state));
    }

    /**
     * X0X
     * XX0
     * 00X
     * @throws Exception
     */
    public void test1wins() throws Exception {
        int [] state = new int[]{1,2,1,1,1,2,2,2,1};
        Game game = new Game();

        assertEquals(1,game.getWinner(state));
    }

    /**
     * X0X
     * X00
     * 0XX
     * @throws Exception
     */
    public void testDraw() throws Exception {
        int [] state = new int[]{1,2,1,1,2,2,2,1,1};
        Game game = new Game();

        assertEquals(3,game.getWinner(state));
    }
}