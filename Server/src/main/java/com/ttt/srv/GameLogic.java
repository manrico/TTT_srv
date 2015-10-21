package com.ttt.srv;

import com.ttt.Message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game logic class, handles server messages and acts on them
 * Created by marko on 07.10.15.
 */

public class GameLogic {
    public static final int MAX_PLAYERS = 2; // which is actually 2.
    private int id;
    private Game game;
    private List<Player> players;
    private Player currentTurn;

    public  GameLogic() {
        this.players = new ArrayList<>();
        this.id = new Random().nextInt(1000);
        this.game = new Game();
    }



    public int getId() {
        return this.id;
    }

    /**
     * Add player to players pool. Sets player id as well, to the players pool index.
     * @param player Player to add
     * @return void
     * @throws Exception When max players to the game are reached.
     */
    public boolean addPlayerAndDecideMark(Player player) throws Exception {
        // check if  maximum players for this game reached.
        if (players.size() >= MAX_PLAYERS) {
            throw new Exception("Maximum players reached for this game");
        }
        players.add(player);
        // player mark (O or X) is decided by it's index in players list. 0 for O and 1 for X
        player.setMark(players.indexOf(player));
        System.out.println("Player added.");
        if(players.size()==MAX_PLAYERS) {
            System.out.printf("max players reached, starting game\n");
            // start game
            int first = (int) (Math.random() * 2);
            setPlayerTurn(players.get(first));
            return true;
        }
        return false;
    }

    public void setPlayerTurn(Player player) {
        this.currentTurn = player;
    }

    public Player getPlayerTurn() {
        return this.currentTurn;
    }

    public void startGame() {
        players.get(0).handler.sendMessage(new Message(ServerCommand.GAME_START, "Game has started"));
        players.get(1).handler.sendMessage(new Message(ServerCommand.GAME_START, "Game has started"));
        getPlayerTurn().handler.sendMessage(new Message(ServerCommand.YOUR_TURN, "You go first"));
    }

}
