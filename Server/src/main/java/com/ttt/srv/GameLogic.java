package com.ttt.srv;

import com.ttt.Message.Message;
import com.ttt.Message.ServerCommand;
import com.ttt.srv.Exception.GameMarkException;
import com.ttt.srv.Exception.GameStateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * Change game state. We take in player mark and decision and try to change the state of the game and then decide if there's a winner.
     * @param idMark player id/ mark
     * @param decision player decision, 0-8
     * @throws Exception If game state change throws Error, which is distributed to players.
     */
    public void changeState(int idMark, int decision) throws Exception {
        try {
            this.game.turn(idMark, decision);
        } catch (GameStateException | GameMarkException ex) {
            System.out.println("Error : " + ex.getMessage());
            this.sendToBothPlayers(ServerCommand.ERROR, ex.getMessage());
            throw new GameStateException("Game state error. - " + ex.getMessage());
        }

        //pass state by reference as it gets sorted
        int[] stateForCheck = new int[9 ];
        System.arraycopy( this.game.getState(), 0, stateForCheck, 0, this.game.getState().length );
        int winner = this.game.getWinner(stateForCheck);
        if (winner > 0) {
            if (winner == 3) { // draw
                this.sendToBothPlayers(ServerCommand.STATE, Arrays.toString(this.game.getState()));
                this.sendToBothPlayers(ServerCommand.DRAW, "Draw");
            } else { // we have actual winner!
                this.sendToBothPlayers(ServerCommand.STATE, Arrays.toString(this.game.getState()));
                System.out.println("WINNER IS MARK " + winner);

                Player winnerPlayer = getPlayerByIdMark(winner);
                this.setPlayerTurn(winnerPlayer);
                winnerPlayer.handler.sendMessage(new Message(ServerCommand.WIN, "You win", winnerPlayer.getIdMark()));

                Player loserPlayer = getPlayerWhosTurnIsnt();
                loserPlayer.handler.sendMessage(new Message(ServerCommand.LOSE, "You lose", loserPlayer.getIdMark()));
            }

        } else {
            this.setPlayerTurn(getPlayerWhosTurnIsnt());
            getPlayerTurn().handler.sendMessage(new Message(ServerCommand.STATE, Arrays.toString(game.getState()), 9));
        }
    }

    public void startGame() {
        this.sendToBothPlayers(ServerCommand.GAME_START, "Game has started");
        Player currentTurnPlayer = getPlayerTurn();
        currentTurnPlayer.handler.sendMessage(new Message(ServerCommand.YOUR_TURN, "You go first", currentTurnPlayer.getIdMark()));
    }

    private void sendToBothPlayers(ServerCommand cmd, String payload) {
        for (Player player : players) {
            player.handler.sendMessage(new Message(cmd, payload, player.getIdMark()));
        }
    }

}
