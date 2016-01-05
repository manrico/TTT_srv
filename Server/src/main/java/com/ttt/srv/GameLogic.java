package com.ttt.srv;

import com.ttt.Message.*;
import com.ttt.srv.Exception.GameStateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Game logic class, handles server messages and acts on them
 * Created by marko on 07.10.15.
 */

public class GameLogic {
    public static final int MAX_PLAYERS = 2; // which is actually 2.
    private Game game;
    private List<Player> players;
    private Player currentTurn;

    public  GameLogic() {
        this.players = new ArrayList<>();
        this.game = new Game();
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
        // player mark (O or X) is decided by it's index in players list. 1 for O and 2 for X
        player.setIdMark(players.indexOf(player)+1);
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

    public Player getPlayerWhosTurnIsnt() {
        if (this.currentTurn.getIdMark() == 1) {
            return players.get(1);
        } else {
            return players.get(0);
        }
    }

    private Player getPlayerByIdMark(int idMark){
        return players.get(idMark-1);
    }

    public void changeState(int idMark, int decision) throws Exception {
        try {
            this.game.turn(idMark, decision);
        } catch (GameStateException ex) {
            System.out.println("Error : " + ex.getMessage());
            players.get(0).handler.sendMessage(new Message(ServerCommand.ERROR, ex.getMessage(), 9));
            players.get(1).handler.sendMessage(new Message(ServerCommand.ERROR, ex.getMessage(), 9));
            throw new Exception("Game state error.");
        }
        Player nextPlayer = getPlayerWhosTurnIsnt();
        nextPlayer.handler.sendMessage(new Message(ServerCommand.STATE,  Arrays.toString(game.getState()), 9));
    }

    public void startGame() {
        players.get(0).handler.sendMessage(new Message(ServerCommand.GAME_START, "Game has started", players.get(0).getIdMark()));
        players.get(1).handler.sendMessage(new Message(ServerCommand.GAME_START, "Game has started", players.get(1).getIdMark()));
        Player currentTurnPlayer = getPlayerTurn();
        currentTurnPlayer.handler.sendMessage(new Message(ServerCommand.YOUR_TURN, "You go first", currentTurnPlayer.getIdMark()));
    }


}
